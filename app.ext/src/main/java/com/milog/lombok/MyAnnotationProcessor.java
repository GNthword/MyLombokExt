package com.milog.lombok;

import com.milog.MyGetter;
import com.sun.source.tree.Tree;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Flags;
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
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * Created by miloway on 2018/7/12.
 */

public class MyAnnotationProcessor extends AbstractProcessor {

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

        Set<? extends Element> set = roundEnv.getElementsAnnotatedWith(MyGetter.class);
        messager.printMessage(Diagnostic.Kind.NOTE, "start " + set.size());

        for(Element element : set) {
            JCTree jcTree = trees.getTree(element);
            messager.printMessage(Diagnostic.Kind.NOTE, "tree " + jcTree.getClass().getCanonicalName());
            jcTree.accept(new TreeTranslator() {
                @Override
                public void visitClassDef(JCTree.JCClassDecl jcClassDecl) {
                    messager.printMessage(Diagnostic.Kind.NOTE, "class " + jcClassDecl.toString());
                    List<JCTree.JCVariableDecl> jcVariableDeclList = List.nil();

                    for (JCTree tree : jcClassDecl.defs) {
                        if (tree.getKind().equals(Tree.Kind.VARIABLE)) {
                            JCTree.JCVariableDecl jcVariableDecl = (JCTree.JCVariableDecl) tree;
                            jcVariableDeclList = jcVariableDeclList.append(jcVariableDecl);
                        }
                    }
                    messager.printMessage(Diagnostic.Kind.OTHER, "processing " + jcVariableDeclList.size());

                    for (JCTree.JCVariableDecl jcVariableDecl : jcVariableDeclList) {
                        messager.printMessage(Diagnostic.Kind.NOTE, jcVariableDecl.getName() + " has been processed");
                        jcClassDecl.defs = jcClassDecl.defs.prepend(makeGetterMethodDecl(jcVariableDecl));
                    }
                    super.visitClassDef(jcClassDecl);
                }

                @Override
                public void visitAnnotation(JCTree.JCAnnotation jcAnnotation) {
                    super.visitAnnotation(jcAnnotation);
                    messager.printMessage(Diagnostic.Kind.NOTE, "annotation " + jcAnnotation.toString());
                    messager.printMessage(Diagnostic.Kind.NOTE, "annotation " + jcAnnotation.getKind() + " tag" + jcAnnotation.getTag());
                    messager.printMessage(Diagnostic.Kind.NOTE, "annotation " + jcAnnotation.args + " tag" + jcAnnotation.attribute.toString());
                    for (JCTree.JCExpression expression : jcAnnotation.args) {
                        messager.printMessage(Diagnostic.Kind.NOTE, "annotation " + jcAnnotation.args.get(0).getClass().getCanonicalName());
                        if (expression instanceof JCTree.JCAssign) {
                            JCTree.JCAssign assign = (JCTree.JCAssign) expression;
                            JCTree.JCIdent ident = (JCTree.JCIdent) assign.lhs;
                            ident.getName();

                            JCTree.JCLiteral literal = (JCTree.JCLiteral) assign.rhs;
                            literal.getValue();

                            messager.printMessage(Diagnostic.Kind.NOTE, "annotation " + ident.getName());
                            messager.printMessage(Diagnostic.Kind.NOTE, "annotation " + literal.getValue());
                        }
                    }
                }

                @Override
                public void visitMethodDef(JCTree.JCMethodDecl jcMethodDecl) {
                    super.visitMethodDef(jcMethodDecl);
                    messager.printMessage(Diagnostic.Kind.NOTE, "method name" + jcMethodDecl.name);
                    messager.printMessage(Diagnostic.Kind.NOTE, "method params" + jcMethodDecl.params);
                }
            });
        }

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


    private JCTree.JCMethodDecl makeGetterMethodDecl(JCTree.JCVariableDecl jcVariableDecl) {
        ListBuffer<JCTree.JCStatement> statements = new ListBuffer<>();
        statements.append(treeMaker.Return(treeMaker.Select(treeMaker.Ident(names.fromString("this")), jcVariableDecl.getName())));
        JCTree.JCBlock body = treeMaker.Block(0, statements.toList());
        return treeMaker.MethodDef(treeMaker.Modifiers(Flags.PUBLIC), getNewMethodName(jcVariableDecl.getName()), jcVariableDecl.vartype, null, null, null, body, null);
    }

    private Name getNewMethodName(Name name) {
        String s = name.toString();
        return names.fromString("get" + s.substring(0, 1).toUpperCase() + s.substring(1, name.length()));
    }
}
