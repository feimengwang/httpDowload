package cn.true123.files;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFileImpl implements PropertiesFile {

    private Properties properties = new Properties();

    protected PropertiesFileImpl(InputStream inputStream) {
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String getProperty(String k) {
        return properties.getProperty(k);
    }

    @Override
    public void setProperty(String key, String value) {
        properties.setProperty(key,value);
    }

}
