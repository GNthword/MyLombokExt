package com.milog.lombok;

import com.milog.MyGetter;
import com.milog.lombok.app.MyApp;
import com.milog.lombok.javac.MiloProcessor;
import com.sun.source.tree.Tree;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Names;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * Created by miloway on 2018/7/12.
 * Main
 */

public class MyAnnotationProcessor extends AbstractProcessor {
    private MiloProcessor processor;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        MyApp.init();
        processor = new MiloProcessor();
        processor.init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        processor.process(annotations, roundEnv);

//        Set<? extends Element> set = roundEnv.getElementsAnnotatedWith(MyGetter.class);
//        messager.printMessage(Diagnostic.Kind.NOTE, "start " + set.size());
//
//
//        messager.printMessage(Diagnostic.Kind.NOTE, "state " + roundEnv.processingOver());
//
//        for(Element element : set) {
//            JCTree jcTree = trees.getTree(element);
//            messager.printMessage(Diagnostic.Kind.NOTE, "tree " + jcTree.getClass().getCanonicalName());
////            messager.printMessage(Diagnostic.Kind.NOTE, "tree " + jcTree.toString());
//            //jcTree.accept(new TestTreeTranslator(messager));
//            JCTree.JCVariableDecl variableDecl = (JCTree.JCVariableDecl) jcTree;
//            //messager.printMessage(Diagnostic.Kind.NOTE, "tree" + variableDecl.toString());
//            MyGetter myGetter = element.getAnnotation(MyGetter.class);
//            myGetter.value();
//            messager.printMessage(Diagnostic.Kind.NOTE, "mods " + variableDecl.mods);
//            messager.printMessage(Diagnostic.Kind.NOTE, "mods type " + variableDecl.vartype);
//            for (Modifier modifier :variableDecl.mods.getFlags() ) {
//                modifier.equals(Modifier.PUBLIC);
//            }
//            messager.printMessage(Diagnostic.Kind.NOTE, "var type " + variableDecl.vartype.type.hasTag(TypeTag.BOOLEAN));



//            messager.printMessage(Diagnostic.Kind.NOTE, "tree " + variableDecl.init);
//            messager.printMessage(Diagnostic.Kind.NOTE, "tree " + variableDecl.name);
//            messager.printMessage(Diagnostic.Kind.NOTE, "tree " + variableDecl.nameexpr);
//            messager.printMessage(Diagnostic.Kind.NOTE, "tree " + variableDecl.mods);
//            messager.printMessage(Diagnostic.Kind.NOTE, "tree " + variableDecl.vartype);


//        }

        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> strings = new LinkedHashSet<String>(1);
        strings.add(MyGetter.class.getCanonicalName());
        return strings;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


}
