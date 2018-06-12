package cn.true123.files;

import java.io.InputStream;


public class PropertiesFileLoader implements PropertiesFile {
    private PropertiesFile innerProperties;
    private PropertiesFile outProperties;

    private static class PropertiesHolder {
        public static PropertiesFileLoader propertiesFileLoader = new PropertiesFileLoader();
    }

    private PropertiesFileLoader() {
        InputStream innerInputStream = this.getClass().getClassLoader().getResourceAsStream("dl.properties");
        if (innerInputStream != null) {
            innerProperties = new PropertiesFileImpl(innerInputStream);
        }
        InputStream outInputStream = this.getClass().getClassLoader().getResourceAsStream("./dl.properties");
        if (outInputStream != null) {
            outProperties = new PropertiesFileImpl(outInputStream);
        }

    }

    public static PropertiesFileLoader getInstance() {
        return PropertiesHolder.propertiesFileLoader;
    }

    @Override
    public String getProperty(String k) {
        if (outProperties != null) {
            String v = outProperties.getProperty(k);
            if (v == null && innerProperties != null) {
                return innerProperties.getProperty(k);
            }
            return v;
        }
        return innerProperties != null ? innerProperties.getProperty(k) : null;
    }

    @Override
    public void setProperty(String key, String value) {

    }

}
