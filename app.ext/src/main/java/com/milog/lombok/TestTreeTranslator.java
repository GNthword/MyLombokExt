package com.milog.lombok;

import com.sun.source.tree.Tree;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Name;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

/**
 * Created by miloway on 2018/7/16.
 */

public class TestTreeTranslator extends TreeTranslator {

    private Messager messager;
    public TestTreeTranslator(Messager messager) {
        this.messager = messager;
    }

    @Override
    public void visitClassDef(JCTree.JCClassDecl jcClassDecl) {
        messager.printMessage(Diagnostic.Kind.NOTE, "class ");
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
            //jcClassDecl.defs = jcClassDecl.defs.prepend(makeGetterMethodDecl(jcVariableDecl));
        }
        super.visitClassDef(jcClassDecl);
    }

    @Override
    public void visitAnnotation(JCTree.JCAnnotation jcAnnotation) {
        super.visitAnnotation(jcAnnotation);
//        messager.printMessage(Diagnostic.Kind.NOTE, "annotation " + jcAnnotation.toString());
//        messager.printMessage(Diagnostic.Kind.NOTE, "annotation " + jcAnnotation.getKind() + " tag" + jcAnnotation.getTag());
//        messager.printMessage(Diagnostic.Kind.NOTE, "annotation " + jcAnnotation.args + " tag" + jcAnnotation.attribute.toString());
//        for (JCTree.JCExpression expression : jcAnnotation.args) {
//            messager.printMessage(Diagnostic.Kind.NOTE, "annotation " + jcAnnotation.args.get(0).getClass().getCanonicalName());
//            if (expression instanceof JCTree.JCAssign) {
//                JCTree.JCAssign assign = (JCTree.JCAssign) expression;
//                JCTree.JCIdent ident = (JCTree.JCIdent) assign.lhs;
//                ident.getName();
//
//                JCTree.JCLiteral literal = (JCTree.JCLiteral) assign.rhs;
//                literal.getValue();
//
//                messager.printMessage(Diagnostic.Kind.NOTE, "annotation " + ident.getName());
//                messager.printMessage(Diagnostic.Kind.NOTE, "annotation " + literal.getValue());
//            }
//        }
    }

    @Override
    public void visitMethodDef(JCTree.JCMethodDecl jcMethodDecl) {
        super.visitMethodDef(jcMethodDecl);
        messager.printMessage(Diagnostic.Kind.NOTE, "method name" + jcMethodDecl.name);
        messager.printMessage(Diagnostic.Kind.NOTE, "method params" + jcMethodDecl.params);
    }


    @Override
    public void visitLetExpr(JCTree.LetExpr letExpr) {
        super.visitLetExpr(letExpr);
    }

    @Override
    public void visitAssign(JCTree.JCAssign jcAssign) {
        super.visitAssign(jcAssign);
        messager.printMessage(Diagnostic.Kind.NOTE, "assign name " + jcAssign.toString());
    }

    @Override
    public void visitVarDef(JCTree.JCVariableDecl jcVariableDecl) {
        super.visitVarDef(jcVariableDecl);
        //messager.printMessage(Diagnostic.Kind.NOTE, "var name " + jcVariableDecl.toString());
        messager.printMessage(Diagnostic.Kind.NOTE, "tree " + jcVariableDecl.init);
        messager.printMessage(Diagnostic.Kind.NOTE, "tree " + jcVariableDecl.name);
        messager.printMessage(Diagnostic.Kind.NOTE, "tree " + jcVariableDecl.nameexpr);
        messager.printMessage(Diagnostic.Kind.NOTE, "tree " + jcVariableDecl.mods);
        messager.printMessage(Diagnostic.Kind.NOTE, "tree " + jcVariableDecl.vartype);
    }


    //    private JCTree.JCMethodDecl makeGetterMethodDecl(JCTree.JCVariableDecl jcVariableDecl) {
//        ListBuffer<JCTree.JCStatement> statements = new ListBuffer<>();
//        statements.append(treeMaker.Return(treeMaker.Select(treeMaker.Ident(names.fromString("this")), jcVariableDecl.getName())));
//        JCTree.JCBlock body = treeMaker.Block(0, statements.toList());
//        return treeMaker.MethodDef(treeMaker.Modifiers(Flags.PUBLIC), getNewMethodName(jcVariableDecl.getName()), jcVariableDecl.vartype, null, null, null, body, null);
//    }
//
//    private Name getNewMethodName(Name name) {
//        String s = name.toString();
//        return names.fromString("get" + s.substring(0, 1).toUpperCase() + s.substring(1, name.length()));
//    }
}
