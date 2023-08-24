package collector;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import models.TestSuite;

import java.io.File;
import java.io.IOException;

public class XUnitParser {

    public static TestSuite parseTestSuite(File file) {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            return xmlMapper.readValue(file, TestSuite.class);
        } catch (IOException ioException) {
            throw new RuntimeException(String.format("Unable to parse file %s due to: %s", file.getAbsolutePath(), ioException.getCause()));
        }
    }
}
