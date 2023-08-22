package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TestSuite {
    String name;
    int tests;
    int errors;
    int skipped;
    int failures;
    @JacksonXmlElementWrapper(useWrapping = false)
    List<TestCase> testcase = new ArrayList<>();

    public String getName() {
        return name;
    }

    public int getTests() {
        return tests;
    }

    public int getErrors() {
        return errors;
    }

    public int getSkipped() {
        return skipped;
    }

    public int getFailures() {
        return failures;
    }

    public List<TestCase> getTestcase() {
        return testcase;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTests(int tests) {
        this.tests = tests;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }

    public void setSkipped(int skipped) {
        this.skipped = skipped;
    }

    public void setFailures(int failures) {
        this.failures = failures;
    }

    public void setTestcase(List<TestCase> testcase) {
        this.testcase = testcase;
    }
}
