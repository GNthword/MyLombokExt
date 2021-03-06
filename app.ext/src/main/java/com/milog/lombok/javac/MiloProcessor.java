package com.milog.lombok.javac;

import com.milog.lombok.app.MyApp;
import com.sun.source.tree.Tree;
import com.sun.source.util.TreePath;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.Context;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * Created by miloway on 2018/7/17.
 * true processor
 */

public class MiloProcessor extends AbstractProcessor {
    private JavacTrees trees;
    private Context context;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.trees = JavacTrees.instance(processingEnv);
        this.context = ((JavacProcessingEnvironment) processingEnv).getContext();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            return false;
        }
        Log.print("start");
        Log.print("environment " + roundEnv.getClass().getCanonicalName());

        List<JCTree.JCCompilationUnit> list = new ArrayList<JCTree.JCCompilationUnit>();
        List<Class<? extends Annotation>> classes = MyApp.getAnnotationClass();
        for (Class<? extends Annotation> class1 : classes) {
            for (Object object : roundEnv.getElementsAnnotatedWith(class1)) {
                Element element = (Element) object;
                JCTree.JCCompilationUnit unit = toUnit(element);
                if (unit == null) {
                    Log.print(" null ");
                    continue;
                }
                if (list.contains(unit)) {
                    continue;
                }
                list.add(unit);

            }
        }

        List<JavacNode> nodes = new ArrayList<JavacNode>();
        for (JCTree.JCCompilationUnit unit : list) {
            for (JCTree tree : unit.getTypeDecls()) {
                if (tree instanceof JCTree.JCClassDecl) {
                    JCTree.JCClassDecl classDecl = (JCTree.JCClassDecl) tree;
                    for (JCTree tree1 : classDecl.getMembers()) {
                        if (tree1.getKind().equals(Tree.Kind.VARIABLE)) {
                            JCTree.JCVariableDecl variableDecl = (JCTree.JCVariableDecl) tree1;
                            //create
                            List<JCTree.JCAnnotation> annotations1 = variableDecl.getModifiers().annotations;
                            for (JCTree.JCAnnotation annotation : annotations1) {
                                Log.print("annotation " + annotation.toString());

                                for (Class<? extends Annotation> class1 : classes) {
                                    if (JavacProcessor.isAnnotationEqual(annotation, class1)) {
                                        nodes.add(new JavacNode(unit ,classDecl, variableDecl, annotation));
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }

        if (nodes.size() > 0) {
            JavacProcessor.delegate(context, nodes);
        }




        return true;
    }

    private JCTree.JCCompilationUnit toUnit(Element element) {
        TreePath path = trees == null ? null : trees.getPath(element);
        if (path == null) {
            return null;
        }

        return (JCTree.JCCompilationUnit) path.getCompilationUnit();
    }


}
