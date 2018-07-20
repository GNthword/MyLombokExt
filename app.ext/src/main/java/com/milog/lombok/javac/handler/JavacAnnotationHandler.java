package com.milog.lombok.javac.handler;

import com.milog.lombok.javac.JavacNode;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Names;

import java.lang.annotation.Annotation;

import javax.annotation.processing.Messager;

/**
 * Created by miloway on 2018/7/17.
 */

public abstract class JavacAnnotationHandler <T extends Annotation> {

    protected Names names;
    protected Messager messager;

    JavacAnnotationHandler(Context context) {
        names = Names.instance(context);
    }

    public abstract void handle(TreeMaker treeMaker, JavacNode node);

    public void setMessager(Messager messager) {
        this.messager = messager;
    }
}
