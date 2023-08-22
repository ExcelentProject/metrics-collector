package collector;

import common.Constants;

import java.util.Map;

import static common.Environment.parseIntOrDefault;
import static common.Environment.parseStringOrDefault;

public class CollectorConfiguration {

    private final String xunitFilePath;
    private final int sleepMs;
    private final String runId;

    CollectorConfiguration(String xunitFilePath, int sleepMs, String runId) {
        this.xunitFilePath = xunitFilePath;
        this.sleepMs = sleepMs;
        this.runId = runId;
    }

    public static CollectorConfiguration fromMap(Map<String, String> config) {
        String xunitFilePath = parseStringOrDefault(config.get(Constants.XUNIT_FILE_PATH_ENV), Constants.XUNIT_FILE_PATH_DEFAULT);
        int sleepMs = parseIntOrDefault(config.get(Constants.SLEEP_MS_ENV), Constants.SLEEP_MS_DEFAULT);
        String runId = parseStringOrDefault(config.get(Constants.RUN_ID_ENV), Constants.RUN_ID_DEFAULT);

        return new CollectorConfiguration(xunitFilePath, sleepMs, runId);
    }

    public String getXunitFilePath() {
        return xunitFilePath;
    }

    public int getSleepMs() {
        return sleepMs;
    }

    public String getRunId() {
        return runId;
    }

    @Override
    public String toString() {
        return "CollectorConfiguration{" +
            "xunitFilePath='" + getXunitFilePath() + '\'' +
            ", sleepMs=" + getSleepMs() +
            ", runId=" + getRunId() +
            '}';
    }
}
