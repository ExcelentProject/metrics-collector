package collector;

import metrics.MetricsRegistry;
import models.TestSuite;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.HttpServer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;

public class Collector {

    private static final Logger LOGGER = LogManager.getLogger(HttpServer.class);

    public static void collectMetricsForInputStream(InputStream inputStream, String runId) throws IOException {
        File xunitFile = createTempFileFromInputStream(inputStream);

        registerMetrics(parseTestSuiteFromXUnit(xunitFile), runId);
    }

    private static File createTempFileFromInputStream(InputStream inputStream) throws IOException {
        File tempFile = File.createTempFile("result",".xml");

        FileUtils.copyToFile(inputStream, tempFile);

        LOGGER.info("Created temp file for current results: {}", tempFile.getAbsoluteFile());

        return tempFile;
    }

    private static TestSuite parseTestSuiteFromXUnit(File xunitFile) {
        return XUnitParser.parseTestSuite(xunitFile);
    }

    private static void registerMetrics(TestSuite testSuite, String runId) {
        LOGGER.info("Registering metrics for TestSuite: {} and RunID: {}", testSuite.getName(), runId);

        int passedTests = testSuite.getTests() - (testSuite.getErrors() + testSuite.getSkipped() + testSuite.getFailures());
        MetricsRegistry.getInstance().getNumOfPassedTests(runId).set(passedTests);
        MetricsRegistry.getInstance().getNumOfFailedTests(runId).set(testSuite.getFailures() + testSuite.getErrors());

        AtomicInteger flakyTests = new AtomicInteger();

        testSuite.getTestcase().forEach( testCase -> {
            if (testCase.getFlakyError() != null && !testCase.getFlakyError().isEmpty()) {
                flakyTests.getAndIncrement();
                MetricsRegistry.getInstance().getNumOfRerunsForFlakyTest(testCase.getNameWithoutParams(), runId).set(testCase.getFlakyError().size());
            }
        });
        MetricsRegistry.getInstance().getNumOfFlakyTests(runId).set(flakyTests.intValue());


        LOGGER.info("All metrics successfully registered for TestSuite: {} and RunID: {}", testSuite.getName(), runId);
    }
}
