import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.HttpServer;


public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        LOGGER.info("Starting HTTP server for exposing Prometheus metrics");
        HttpServer httpServer = new HttpServer();
        httpServer.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.info("Shutting down HTTP server");
            httpServer.stop();

            LOGGER.info("Closing collector application");
        }));
    }
}
