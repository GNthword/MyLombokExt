package com.milog.lombok.app;

public class MyApp {

    private static BaseConfig baseConfig;

    public static void init() {
        baseConfig = new BaseConfig();
    }

    private static String getApplication() {
        if (baseConfig == null) {
            return "";
        }
        return baseConfig.getApplication();
    }

    public static String getResourcesValue() {
        return "";
    }


}
