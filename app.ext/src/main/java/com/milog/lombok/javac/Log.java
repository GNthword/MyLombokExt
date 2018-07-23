package com.milog.lombok.javac;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

/**
 * Created by miloway on 2018/7/23.
 */

public class Log {

    private static boolean isOpen;
    private static Messager messager;

    public static void init(boolean isOpen, Messager messager) {
        Log.isOpen = isOpen;
        Log.messager = messager;
    }

    public static void setIsOpen(boolean isOpen) {
        Log.isOpen = isOpen;
    }

    public static void print(String message) {
        if (isOpen) {
            print(Diagnostic.Kind.NOTE, message);
        }
    }

    public static void print(Diagnostic.Kind kind, String message) {
        if (isOpen) {
            messager.printMessage(kind, message);
        }
    }

    public static void destroy() {
        messager = null;
    }

}
