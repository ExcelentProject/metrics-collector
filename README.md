# TEALC - metrics collector

Metrics collector developed for scraping information from xUnit files, mainly from the Thor testsuite.</br>
For collecting metrics from xUnit files, you will need to cURL the data to the `8080` port and `/data` endpoint.

For specifying the run ID, you need to use `X-Run-ID` header and exec cURL command as follows:

```shell
curl -X POST -H "X-Run-ID: MY_ID" -d @/path/to/file HOSTNAME:8080/data
```

The binary data are then converted to temp file, from which is everything gathered.

Collected metrics are exposed under `8080` port and `/metrics` endpoint.

## Exposed metrics

The collector collects information about passed, failed, and flaky tests. </br>
Also it counts number of re-runs for the flaky tests.


| Metric name                                         | Type    | Description                                                |
|-----------------------------------------------------|---------|------------------------------------------------------------|
| `tealc_tests_passed_tests_total(runId)`             | Counter | Number of passed tests for specific run                    |
| `tealc_tests_failed_tests_total(runId)`             | Counter | Number of failed tests for specific run                    |
| `tealc_tests_flaky_tests_total(runId)`              | Counter | Number of flaky tests for specific run                     |
| `tealc_tests_num_of_test_rerun(runId,testCaseName)` | Counter | Number of re-rerun for the flaky test for the specific run |