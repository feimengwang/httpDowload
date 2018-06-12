package cn.true123;

import cn.true123.files.PropertiesFile;
import cn.true123.files.PropertiesFileLoader;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.Properties;

public class TestCase1 {


    @Test
    public void test(){
        PropertiesFile file = PropertiesFileLoader.getInstance();
        System.out.println(file.getProperty("thread"));

    }
}