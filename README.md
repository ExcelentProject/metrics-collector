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

* `XUNIT_FILE_PATH` - path to the folder with results in `.xml` format - String
* `SLEEP_MS` - desired time to sleep after metrics collection, in milliseconds - int
* `RUN_ID` - ID of the test execution - for separation of test runs in dashboards