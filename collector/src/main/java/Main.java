import collector.Collector;
import collector.CollectorConfiguration;
import metrics.MetricsRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.HttpServer;


public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        CollectorConfiguration configuration = CollectorConfiguration.fromMap(System.getenv());

        LOGGER.info("Starting MetricsCollector with following configuration: {}", configuration.toString());
        MetricsRegistry.getInstance().setRunId(configuration.getRunId());

        LOGGER.info("Starting HTTP server for exposing Prometheus metrics");
        HttpServer httpServer = new HttpServer();
        httpServer.start();

        LOGGER.info("Starting collecting metrics");
        Collector.collectMetricsFromFilesInPath(configuration.getXunitFilePath());

        LOGGER.info("Metrics were successfully collected, transitioning to sleep mode");
        Thread.sleep(configuration.getSleepMs());

        LOGGER.info("Shutting down HTTP server");
        httpServer.stop();

        LOGGER.info("Closing collector application");
    }
}
