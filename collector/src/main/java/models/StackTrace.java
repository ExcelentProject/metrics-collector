package models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

public class StackTrace {
    @JacksonXmlText
    String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
