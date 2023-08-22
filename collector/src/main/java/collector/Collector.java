package collector;

import metrics.MetricsRegistry;
import models.TestSuite;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Collector {

    public static void collectMetricsFromFilesInPath(String resultsPath) {
        List<File> xunitFiles = listFilesInFolder(resultsPath);

        registerMetrics(parseTestSuitesFromXUnits(xunitFiles));
    }

    private static List<File> listFilesInFolder(String resultsPath) {
        List<File> xunitFiles = new ArrayList<>();

        File folder = new File(resultsPath);

        File[] filesInFolder = folder.listFiles();

        for (File file : filesInFolder) {
            if (file.getName().contains(".xml")) {
                xunitFiles.add(file);
            }
        }

        return xunitFiles;
    }

    private static List<TestSuite> parseTestSuitesFromXUnits(List<File> xunitFiles) {
        List<TestSuite> testSuites = new ArrayList<>();

        xunitFiles.forEach(file -> testSuites.add(XUnitParser.parseTestSuite(file)));

        return testSuites;
    }

    private static void registerMetrics(List<TestSuite> testSuites) {
        testSuites.forEach(testSuite -> {
            double passedTests = testSuite.getTests() - (testSuite.getErrors() + testSuite.getSkipped() + testSuite.getFailures());
            MetricsRegistry.getInstance().getNumOfPassedTests().increment(passedTests);
            MetricsRegistry.getInstance().getNumOfFailedTests().increment(testSuite.getFailures() + testSuite.getErrors());

            testSuite.getTestcase().forEach( testCase -> {
                if (testCase.getFlakyError() != null && testCase.getFlakyError().size() != 0) {
                    MetricsRegistry.getInstance().getNumOfFlakyTests().increment();
                    MetricsRegistry.getInstance().getNumOfRerunsForFlakyTest(testCase.getNameWithoutParams()).increment(testCase.getFlakyError().size());
                }

            });
        });
    }
}
