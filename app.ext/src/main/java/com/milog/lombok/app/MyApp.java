package com.milog.lombok.app;

import com.milog.MyGetter;
import com.milog.lombok.javac.handler.GetterHandler;
import com.milog.lombok.javac.handler.JavacAnnotationHandler;
import com.sun.tools.javac.util.Context;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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

    public static Set<String> getAnnotations() {
        Set<String> strings = new LinkedHashSet<String>(1);
        strings.add(MyGetter.class.getCanonicalName());
        return strings;
    }

    public static List<Class<? extends Annotation>> getAnnotationClass() {
        List<Class<? extends Annotation>> list = new ArrayList<Class<? extends Annotation>>(1);
        list.add(MyGetter.class);
        return list;
    }

    public static HashMap<Class<? extends Annotation>, JavacAnnotationHandler> getAnnotationMap(Context context) {
        HashMap<Class<? extends Annotation>, JavacAnnotationHandler> map = new HashMap<>(1);
        map.put(MyGetter.class, new GetterHandler(context));
        return map;
    }
}
