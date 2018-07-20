package com.milog.lombok.javac;

import com.milog.lombok.app.MyApp;
import com.milog.lombok.javac.handler.JavacAnnotationHandler;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

/**
 * Created by miloway on 2018/7/19.
 * 处理和分发到对应的Handler
 */

public class JavacProcessor {

    private List<JavacNode> nodes;

    public static void delegate(Context context, Messager messager, List<JavacNode> nodes) {
        messager.printMessage(Diagnostic.Kind.NOTE, "delegate in");
        TreeMaker treeMaker = TreeMaker.instance(context);

        HashMap<Class<? extends Annotation>, JavacAnnotationHandler> maps = MyApp.getAnnotationMap(context);
        for (JavacNode node : nodes) {
            for (Map.Entry<Class<? extends Annotation>, JavacAnnotationHandler> map : maps.entrySet()) {
                if (isAnnotationEqual(node.annotation, map.getKey())) {
                    messager.printMessage(Diagnostic.Kind.NOTE, "delegate handle");
                    //node.classDecl.accept(new MiloTreeTranslator(context, messager, node));
                    ((JavacAnnotationHandler)map.getValue()).setMessager(messager);
                    ((JavacAnnotationHandler)map.getValue()).handle(treeMaker, node);

                }
            }
        }

    }

    public static boolean isAnnotationEqual(JCTree.JCAnnotation jcAnnotation, Class<? extends Annotation> annotation) {
        return jcAnnotation.type.tsym.toString().equals(annotation.getCanonicalName());
    }

}
