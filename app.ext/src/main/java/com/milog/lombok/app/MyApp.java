package com.milog.lombok.app;

import com.milog.annotation.FunctionManager;
import com.milog.annotation.MyGetter;
import com.milog.lombok.javac.Log;
import com.milog.lombok.javac.handler.FunctionManagerHandler;
import com.milog.lombok.javac.handler.GetterHandler;
import com.milog.lombok.javac.handler.JavacAnnotationHandler;
import com.sun.tools.javac.util.Context;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.Messager;

public class MyApp {

    private static BaseConfig baseConfig;

    public static void init(Messager messager) {
        baseConfig = new BaseConfig();
        Log.init(getLogState(), messager);
    }

    private static boolean getLogState() {
        return baseConfig != null && baseConfig.getLogState();
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

    public static String getApplicationName() {
        if (baseConfig == null) {
            return "";
        }
        return baseConfig.getApplicationName();
    }

    public static String getApplicationNameWithPackage() {
        if (baseConfig == null) {
            return "";
        }
        return baseConfig.getApplicationPackage() + "." + baseConfig.getApplicationName();
    }

    public static String getApplicationPackage() {
        if (baseConfig == null) {
            return "";
        }
        return baseConfig.getApplicationPackage();
    }

    public static String getApplicationFunction() {
        if (baseConfig == null) {
            return "";
        }
        String function = baseConfig.getApplicationFunction();
        //if (!function.endsWith("()")) {
            //function += "()";
        //}
        return function;
    }


    public static Set<String> getAnnotations() {
        Set<String> strings = new LinkedHashSet<String>(2);
        strings.add(MyGetter.class.getCanonicalName());
        strings.add(FunctionManager.class.getCanonicalName());
        return strings;
    }

    public static List<Class<? extends Annotation>> getAnnotationClass() {
        List<Class<? extends Annotation>> list = new ArrayList<Class<? extends Annotation>>(2);
        list.add(MyGetter.class);
        list.add(FunctionManager.class);
        return list;
    }

    public static HashMap<Class<? extends Annotation>, JavacAnnotationHandler> getAnnotationMap(Context context) {
        HashMap<Class<? extends Annotation>, JavacAnnotationHandler> map = new HashMap<>(2);
        map.put(MyGetter.class, new GetterHandler(context));
        map.put(FunctionManager.class, new FunctionManagerHandler(context));
        return map;
    }

    public static void destroy() {
        Log.destroy();
    }
}
