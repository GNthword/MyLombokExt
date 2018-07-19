package com.milog.lombok.javac;

import com.sun.tools.javac.tree.JCTree;

/**
 * Created by miloway on 2018/7/19.
 */

public class JavacNode {

    public JCTree.JCClassDecl classDecl;
    public JCTree.JCVariableDecl variableDecl;
    public JCTree.JCAnnotation annotation;
    private boolean isChanged;

    public JavacNode(JCTree.JCClassDecl classDecl, JCTree.JCVariableDecl variableDecl, JCTree.JCAnnotation annotation) {
        this.classDecl = classDecl;
        this.variableDecl = variableDecl;
        this.annotation = annotation;
    }


}
