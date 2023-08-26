package metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MetricsRegistry {

    private static final String METRICS_PREFIX = "tealc_tests_";

    private static MetricsRegistry instance = null;
    private final PrometheusMeterRegistry prometheusMeterRegistry;

    private final Map<String, Counter> numOfPassedTests = new ConcurrentHashMap<>(1);
    private final Map<String, Counter> numOfFailedTests = new ConcurrentHashMap<>(1);
    private final Map<String, Counter> numOfFlakyTests = new ConcurrentHashMap<>(1);
    private final Map<String, Counter> numOfRerunsForFlakyTest = new ConcurrentHashMap<>(1);

    public MetricsRegistry(PrometheusMeterRegistry prometheusMeterRegistry) {
        this.prometheusMeterRegistry = prometheusMeterRegistry;
    }

    public static MetricsRegistry getInstance() {
        if (instance == null) {
            instance = new MetricsRegistry(new PrometheusMeterRegistry(PrometheusConfig.DEFAULT));
        }
        return instance;
    }

    public PrometheusMeterRegistry getPrometheusMeterRegistry() {
        return prometheusMeterRegistry;
    }

    public Counter getNumOfPassedTests(String runId) {
        String metricName = METRICS_PREFIX + "passed_tests_total";
        Tags tags = Tags.of(Tag.of("runId", runId));
        String description = "The total number of passed tests for run with ID: " + runId;
        String key = metricName;

        return numOfPassedTests.computeIfAbsent(key, func -> counter(metricName, description, tags));
    }

    public Counter getNumOfFailedTests(String runId) {
        String metricName = METRICS_PREFIX + "failed_tests_total";
        Tags tags = Tags.of(Tag.of("runId", runId));
        String description = "The total number of failed tests for run with ID: " + runId;
        String key = metricName;

        return numOfFailedTests.computeIfAbsent(key, func -> counter(metricName, description, tags));
    }

    public Counter getNumOfFlakyTests(String runId) {
        String metricName = METRICS_PREFIX + "flaky_tests_total";
        Tags tags = Tags.of(Tag.of("runId", runId));
        String description = "The total number of flaky tests for run with ID: " + runId;
        String key = metricName;

        return numOfFlakyTests.computeIfAbsent(key, func -> counter(metricName, description, tags));
    }

    public Counter getNumOfRerunsForFlakyTest(String testCaseName, String runId) {
        String metricName = METRICS_PREFIX + "num_of_test_rerun";
        Tags tags = Tags.of(Tag.of("runId", runId), Tag.of("testCaseName", testCaseName));
        String description = "Number of reruns for a test and run with ID: " + runId;
        String key = metricName;

        return numOfRerunsForFlakyTest.computeIfAbsent(key, func -> counter(metricName, description, tags));
    }

    private Counter counter(String metricName, String metricDescription, Tags tags) {
        return Counter
            .builder(metricName)
            .description(metricDescription)
            .tags(tags)
            .register(prometheusMeterRegistry);
    }
}
