package com.milog.mylombokext;

import com.milog.mylombokext.app.MyApplication;

/**
 * Created by miloway on 2018/7/12.
 */

public class FunctionManager {

    public static String getProperty(String key) {
        int id = MyApplication.getApplication().getResources().getIdentifier(key, "string", MyApplication.getApplication().getPackageName());
        if (id == 0) {
            return "error";
        }
        return MyApplication.getApplication().getResources().getString(id);
    }
}
