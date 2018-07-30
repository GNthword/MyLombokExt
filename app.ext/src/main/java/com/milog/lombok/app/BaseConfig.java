package com.milog.lombok.app;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by miloway on 2018/7/17.
 * 基础配置
 */

class BaseConfig {

    private static Properties properties;

    private final String OPEN = "open";
    private final String CLOSE = "close";


    private final String LOG_STATE = "log_state";


    private final String APPLICATION_NAME = "application_name";
    private final String GET_APPLICATION_FUNCTION_NAME = "get_application_function_name";
    private final String APPLICATION_PACKAGE = "application_package";
    private final String path = "app\\src\\main\\assets\\annotation.properties";;//"app.ext\\src\\main\\resources\\properties\\annotation.properties";

    BaseConfig () {
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

    boolean getLogState() {
        String state = properties.getProperty(LOG_STATE, CLOSE);
        return state.equals(OPEN);
    }

    String getApplication() {
        String name = getApplicationName();
        String functionName = getApplicationFunction();
        if (name == null || functionName == null) {
            return null;
        }

        return name + "." + functionName;
    }

    String getApplicationName() {
        return properties.getProperty(APPLICATION_NAME);
    }

    String getApplicationPackage() {
        return properties.getProperty(APPLICATION_PACKAGE);
    }

    String getApplicationFunction() {
        return properties.getProperty(GET_APPLICATION_FUNCTION_NAME);
    }



}
