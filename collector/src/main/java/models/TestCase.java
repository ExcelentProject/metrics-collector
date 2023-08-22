package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.ArrayList;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestCase {
    String name;
    String classname;

    @JacksonXmlElementWrapper(useWrapping = false)
    List<Error> error = new ArrayList<>();

    @JacksonXmlElementWrapper(useWrapping = false)
    List<FlakyError> flakyError = new ArrayList<>();

    public String getName() {
        return name;
    }

    public String getClassname() {
        return classname;
    }

    public List<Error> getError() {
        return error;
    }

    public List<FlakyError> getFlakyError() {
        return flakyError;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public void setError(List<Error> error) {
        this.error = error;
    }

    public void setFlakyError(List<FlakyError> flakyError) {
        this.flakyError = flakyError;
    }

    public String getNameWithoutParams() {
        return getName().replaceFirst("\\(.*\\)", "");
    }
}
