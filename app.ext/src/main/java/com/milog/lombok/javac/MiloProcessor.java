package com.milog.lombok.javac;

import com.milog.MyGetter;
import com.milog.lombok.TestTreeTranslator;
import com.sun.source.tree.Tree;
import com.sun.source.util.TreePath;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Names;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * Created by miloway on 2018/7/17.
 */

public class MiloProcessor extends AbstractProcessor {
    private Messager messager;
    private JavacTrees trees;
    private TreeMaker treeMaker;
    private Names names;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.messager = processingEnv.getMessager();
        this.trees = JavacTrees.instance(processingEnv);
        Context context = ((JavacProcessingEnvironment) processingEnv).getContext();
        this.treeMaker = TreeMaker.instance(context);
        this.names = Names.instance(context);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            return false;
        }
        messager.printMessage(Diagnostic.Kind.NOTE, "start");

        for (Element element : roundEnv.getRootElements()) {
            JCTree jcTree = trees.getTree(element);
            messager.printMessage(Diagnostic.Kind.NOTE, jcTree.getClass().getCanonicalName());
            JCTree.JCClassDecl classDecl = (JCTree.JCClassDecl) jcTree;
            messager.printMessage(Diagnostic.Kind.NOTE, classDecl.name);
            for (JCTree tree : classDecl.getMembers()) {
                if (tree instanceof JCTree.JCVariableDecl) {
                    messager.printMessage(Diagnostic.Kind.NOTE, ((JCTree.JCVariableDecl)tree).toString());
                }
            }
        }

        messager.printMessage(Diagnostic.Kind.NOTE, "\npart2");

        List<JCTree.JCCompilationUnit> list = new ArrayList<JCTree.JCCompilationUnit>();
        for (Element element : roundEnv.getElementsAnnotatedWith(MyGetter.class)) {
            JCTree.JCCompilationUnit unit = toUnit(element);
            if (unit == null) {
                messager.printMessage(Diagnostic.Kind.NOTE, " null ");
                continue;
            }
            if (list.contains(unit)) {
                continue;
            }
            list.add(unit);
        }

        for (JCTree.JCCompilationUnit unit : list) {
            messager.printMessage(Diagnostic.Kind.NOTE, unit.packge.toString());
            messager.printMessage(Diagnostic.Kind.NOTE, unit.getTypeDecls().getClass().getCanonicalName());
            for (JCTree tree : unit.getTypeDecls()) {
                if (tree instanceof JCTree.JCClassDecl) {
                    JCTree.JCClassDecl classDecl = (JCTree.JCClassDecl) tree;
                    messager.printMessage(Diagnostic.Kind.NOTE, classDecl.name);

                    for (JCTree tree1 : classDecl.getMembers()) {
                        if (tree1.getKind().equals(Tree.Kind.VARIABLE)) {
                            JCTree.JCVariableDecl variableDecl = (JCTree.JCVariableDecl) tree1;
                            //create
                        }
                    }
                }
            }
        }




        return true;
    }

    private JCTree.JCCompilationUnit toUnit(Element element) {
        TreePath path = trees == null ? null : trees.getPath(element);
        if (path == null) return null;

        return (JCTree.JCCompilationUnit) path.getCompilationUnit();
    }


}
