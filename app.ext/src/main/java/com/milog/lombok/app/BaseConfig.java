package com.milog.lombok.app;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by miloway on 2018/7/17.
 */

class BaseConfig {

    public static Properties properties;

    private final String APPLICATION_NAME = "application_name";
    private final String GET_APPLICATION_FUNCTION_NAME = "get_application_function_name";
    private final String path = "app\\src\\main\\assets\\annotation.properties";;//"app.ext\\src\\main\\resources\\properties\\annotation.properties";

    public BaseConfig () {
        init();
    }

    private void init() {
        properties = new Properties();
        try {
            InputStream is = new FileInputStream(path);
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getApplication() {
        String name = properties.getProperty(APPLICATION_NAME, "");
        String functionName = properties.getProperty(GET_APPLICATION_FUNCTION_NAME, "");
        if (name.equals("") && functionName.equals("")) {
            return "";
        }

        return name + "." + functionName;
    }



}
