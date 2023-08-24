package server;

import collector.Collector;
import common.Constants;
import metrics.MetricsRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HttpServer {
    private static final Logger LOGGER = LogManager.getLogger(HttpServer.class);
    private static final int HTTP_PORT = 8080;
    private final Server server;

    public HttpServer() {
        this.server = new Server(HTTP_PORT);

        ContextHandler metricsContext = new ContextHandler();
        metricsContext.setContextPath("/metrics");
        metricsContext.setHandler(new MetricsHandler());
        metricsContext.setAllowNullPathInfo(true);

        ContextHandler dataContext = new ContextHandler();
        dataContext.setContextPath("/data");
        dataContext.setHandler(new DataHandler());
        dataContext.setAllowNullPathInfo(true);

        ContextHandlerCollection contexts = new ContextHandlerCollection(metricsContext, dataContext);
        server.setHandler(contexts);
    }

    public void start() {
        LOGGER.info("Starting HTTP server");
        try {
            getServer().start();
        } catch (Exception e)   {
            LOGGER.error("Failed to start the webserver", e);
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        LOGGER.info("Stopping HTTP server");
        try {
            getServer().stop();
        } catch (Exception e)   {
            LOGGER.error("Failed to stop the webserver", e);
            throw new RuntimeException(e);
        }
    }

    private Server getServer() {
        return server;
    }

    public static class MetricsHandler extends AbstractHandler {

        @Override
        public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
            response.setContentType("text/plain");

            response.setStatus(HttpServletResponse.SC_OK);
            MetricsRegistry.getInstance().getPrometheusMeterRegistry().scrape(response.getWriter());

            baseRequest.setHandled(true);
        }
    }

    public static class DataHandler extends AbstractHandler {

        @Override
        public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
            response.setContentType("text/plain");

            response.setStatus(HttpServletResponse.SC_OK);

            String runId = baseRequest.getHeader(Constants.RUN_ID_HEADER);
            LOGGER.info("Received data for following run ID: {}", runId);

            Collector.collectMetricsForInputStream(request.getInputStream(), runId);

            response.getWriter().println("ok");

            baseRequest.setHandled(true);
        }
    }
}
