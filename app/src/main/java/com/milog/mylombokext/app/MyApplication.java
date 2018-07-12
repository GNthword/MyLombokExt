package com.milog.mylombokext.app;

import android.app.Application;

/**
 * Created by miloway on 2018/7/12.
 */

public class MyApplication extends Application {
    private static MyApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static MyApplication getApplication() {
        return application;
    }


}
