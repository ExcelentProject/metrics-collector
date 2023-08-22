# TEALC - metrics collector

Metrics collector developer for scraping information from xUnit files, mainly from the Thor testsuite.</br>
The collector does following:

1. List all files with `.xml` suffix on specified path (via environmental variable)
2. Parses all the objects from each file to - `TestSuite`, `TestCase`
3. Goes through each `TestSuite` -> `TestCase`
4. Collects all the information to the Prometheus exporter
5. Exposes the metrics via `/metrics` endpoint
6. Sleeps for the specified time (via environmental variable)

## Environmental variables

Here is the list of environmental variables you can specify:

| Environment variable | Type   | Description                                                          |
|----------------------|--------|----------------------------------------------------------------------|
| `XUNIT_FILE_PATH`    | String | path to the folder with results in `.xml` format                     |
| `SLEEP_MS`           | int    | desired time to sleep after metrics collection, in milliseconds      |
| `RUN_ID`             | String | ID of the test execution - for separation of test runs in dashboards |

## Exposed metrics

The collector collects information about passed, failed, and flaky tests. </br>
Also it counts number of re-runs for the flaky tests.


| Metric name                                         | Type    | Description                                                |
|-----------------------------------------------------|---------|------------------------------------------------------------|
| `tealc_tests_passed_tests_total(runId)`             | Counter | Number of passed tests for specific run                    |
| `tealc_tests_failed_tests_total(runId)`             | Counter | Number of failed tests for specific run                    |
| `tealc_tests_flaky_tests_total(runId)`              | Counter | Number of flaky tests for specific run                     |
| `tealc_tests_num_of_test_rerun(runId,testCaseName)` | Counter | Number of re-rerun for the flaky test for the specific run |