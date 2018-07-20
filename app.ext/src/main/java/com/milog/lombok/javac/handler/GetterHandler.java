package com.milog.lombok.javac.handler;

import com.milog.annotation.MyGetter;
import com.milog.lombok.javac.JavacNode;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Name;

import javax.annotation.processing.Messager;

/**
 * Created by miloway on 2018/7/17.
 */

public class GetterHandler extends JavacAnnotationHandler<MyGetter>{

    public GetterHandler(Context context) {
        super(context);
    }

    @Override
    public void handle(TreeMaker treeMaker, JavacNode node) {
        JCTree.JCMethodDecl methodDecl = createGetterMethod(treeMaker, node.variableDecl);
        node.classDecl.defs = node.classDecl.defs.prepend(methodDecl);
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
