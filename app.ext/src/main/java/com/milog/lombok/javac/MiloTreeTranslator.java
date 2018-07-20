package com.milog.lombok.javac;

import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Names;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

/**
 * Created by miloway on 2018/7/17.
 */

public class MiloTreeTranslator extends TreeTranslator {

    private Names names;
    private TreeMaker treeMaker;
    private Messager messager;
    private JavacNode node;

    public MiloTreeTranslator(Context context, Messager messager, JavacNode node) {
        names = Names.instance(context);
        treeMaker = TreeMaker.instance(context);
        this.messager = messager;
        this.node = node;
    }

    @Override
    public void visitClassDef(JCTree.JCClassDecl tree) {
        if (tree.equals(node.classDecl)) {
            messager.printMessage(Diagnostic.Kind.NOTE, "class equal");
        }else {
            super.visitClassDef(tree);
        }

        JCTree.JCMethodDecl methodDecl = createGetterMethod(treeMaker, node.variableDecl);
        messager.printMessage(Diagnostic.Kind.NOTE, "class visit method " + methodDecl.getName());
        messager.printMessage(Diagnostic.Kind.NOTE, "class visit method " + methodDecl.toString());
        tree.defs = tree.defs.prepend(methodDecl);
        messager.printMessage(Diagnostic.Kind.NOTE, "class visit end");

        super.visitClassDef(tree);
    }


    public JCTree.JCMethodDecl createGetterMethod(TreeMaker treeMaker, JCTree.JCVariableDecl variableDecl) {

        Name methodName = getMethodName(variableDecl.name);
        JCTree.JCReturn jcReturn = treeMaker.Return(treeMaker.Select(treeMaker.Ident(names.fromString("this")), variableDecl.getName()));
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC);
        ListBuffer<JCTree.JCStatement> statements = new ListBuffer<>();
        statements.append(jcReturn);
        JCTree.JCBlock block = treeMaker.Block(Flags.BLOCK, statements.toList());

        statements.clear();

        List<JCTree.JCTypeParameter> typeParameters = new ListBuffer<JCTree.JCTypeParameter>().toList();
        List<JCTree.JCVariableDecl> variableDecls = new ListBuffer<JCTree.JCVariableDecl>().toList();
        List<JCTree.JCExpression> throws1 = new ListBuffer<JCTree.JCExpression>().toList();

        return treeMaker.MethodDef(modifiers, methodName, variableDecl.vartype, typeParameters, variableDecls, throws1, block, null);
    }


    private Name getMethodName(Name name) {
        String s = name.toString();
        return names.fromString("get" + s.substring(0, 1).toUpperCase() + s.substring(1, name.length()));
    }
}
