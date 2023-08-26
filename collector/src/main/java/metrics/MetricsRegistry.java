package metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MetricsRegistry {

    private static final String METRICS_PREFIX = "tealc_tests_";

    private static MetricsRegistry instance = null;
    private final PrometheusMeterRegistry prometheusMeterRegistry;

    private final Map<String, AtomicInteger> numOfPassedTests = new ConcurrentHashMap<>(1);
    private final Map<String, AtomicInteger> numOfFailedTests = new ConcurrentHashMap<>(1);
    private final Map<String, AtomicInteger> numOfFlakyTests = new ConcurrentHashMap<>(1);
    private final Map<String, AtomicInteger> numOfRerunsForFlakyTest = new ConcurrentHashMap<>(1);

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

    public AtomicInteger getNumOfPassedTests(String runId) {
        String metricName = METRICS_PREFIX + "passed_tests_total";
        Tags tags = Tags.of(Tag.of("runId", runId));
        String description = "The total number of passed tests for run with ID: " + runId;
        String key = metricName;

        return numOfPassedTests.computeIfAbsent(key, func -> counter(metricName, description, tags));
    }

    public AtomicInteger getNumOfFailedTests(String runId) {
        String metricName = METRICS_PREFIX + "failed_tests_total";
        Tags tags = Tags.of(Tag.of("runId", runId));
        String description = "The total number of failed tests for run with ID: " + runId;
        String key = metricName;

        return numOfFailedTests.computeIfAbsent(key, func -> counter(metricName, description, tags));
    }

    public AtomicInteger getNumOfFlakyTests(String runId) {
        String metricName = METRICS_PREFIX + "flaky_tests_total";
        Tags tags = Tags.of(Tag.of("runId", runId));
        String description = "The total number of flaky tests for run with ID: " + runId;
        String key = metricName;

        return numOfFlakyTests.computeIfAbsent(key, func -> counter(metricName, description, tags));
    }

    public AtomicInteger getNumOfRerunsForFlakyTest(String testCaseName, String runId) {
        String metricName = METRICS_PREFIX + "num_of_test_rerun";
        Tags tags = Tags.of(Tag.of("runId", runId), Tag.of("testCaseName", testCaseName));
        String description = "Number of reruns for a test and run with ID: " + runId;
        String key = metricName;

        return numOfRerunsForFlakyTest.computeIfAbsent(key, func -> counter(metricName, description, tags));
    }

    private AtomicInteger counter(String metricName, String metricDescription, Tags tags) {
        AtomicInteger gauge = new AtomicInteger(0);
        Gauge.builder(metricName, () -> gauge)
                .description(metricDescription)
                .tags(tags)
                .register(prometheusMeterRegistry);

        return gauge;
    }
}
