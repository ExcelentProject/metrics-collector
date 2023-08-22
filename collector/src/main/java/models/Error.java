package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Error {
    String message;
    String type;
    @JacksonXmlText
    String value;

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
