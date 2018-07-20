package com.milog.lombok.javac.handler;

import com.milog.annotation.FunctionManager;
import com.milog.lombok.app.MyApp;
import com.milog.lombok.javac.JavacNode;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;

import javax.tools.Diagnostic;

/**
 * Created by miloway on 2018/7/20.
 * FunctionManagerHandler
 */

public class FunctionManagerHandler extends JavacAnnotationHandler<FunctionManager> {

    public FunctionManagerHandler(Context context) {
        super(context);

    }

    @Override
    public void handle(TreeMaker treeMaker, JavacNode node) {
        String value = getValue(node.annotation);
        if (value == null || value.length() < 1) {
            return;
        }
        /*createInit(treeMaker, node.variableDecl, value);
        List<JCTree.JCImport> importList = node.unit.getImports();
        for (JCTree.JCImport jcImport : importList) {
            messager.printMessage(Diagnostic.Kind.NOTE, "fm import " + jcImport.qualid);
            messager.printMessage(Diagnostic.Kind.NOTE, "fm import " + jcImport.type);
            messager.printMessage(Diagnostic.Kind.NOTE, "fm import " + jcImport.qualid.getClass().getCanonicalName());
        }*/
        messager.printMessage(Diagnostic.Kind.NOTE, "fm " + node.variableDecl.init);
        messager.printMessage(Diagnostic.Kind.NOTE, "fm " + node.variableDecl.init.getClass().getCanonicalName());
        if (node.variableDecl.init == null) {
            return;
        }
        JCTree.JCMethodInvocation jcMethodInvocation = (JCTree.JCMethodInvocation) node.variableDecl.init;
        messager.printMessage(Diagnostic.Kind.NOTE, "fm "  + jcMethodInvocation.typeargs);
        messager.printMessage(Diagnostic.Kind.NOTE, "fm "  + jcMethodInvocation.meth);
        messager.printMessage(Diagnostic.Kind.NOTE, "fm "  + jcMethodInvocation.args);
        messager.printMessage(Diagnostic.Kind.NOTE, "fm "  + jcMethodInvocation.varargsElement);

    }

    private String getValue(JCTree.JCAnnotation jcAnnotation) {
        for (JCTree.JCExpression jcExpression : jcAnnotation.args) {
            if (jcExpression instanceof JCTree.JCAssign) {
                JCTree.JCAssign assign = (JCTree.JCAssign) jcExpression;
                JCTree.JCIdent ident = (JCTree.JCIdent) assign.lhs;
                if (ident.getName().toString().equals("value")) {
                    JCTree.JCLiteral literal = (JCTree.JCLiteral) assign.rhs;
                    return (String) literal.getValue();
                }
            }
        }
        return null;
    }

    private JCTree.JCAssign createInit(TreeMaker treeMaker, JCTree.JCVariableDecl variableDecl, String value) {
        messager.printMessage(Diagnostic.Kind.NOTE, "fm " + getResource(treeMaker, value));

        variableDecl.init = getResource(treeMaker, value);

        return null;
    }

    /**
     * @return MyApplication.getApplication().getString(R.string.app_name);
     */
    private JCTree.JCExpression getResource(TreeMaker treeMaker, String value) {
        String applicationPackage = MyApp.getApplicationPackage();
        String application = MyApp.getApplicationName();
        String applicationFunction = MyApp.getApplicationFunction();
        JCTree.JCExpression app = treeMaker.Select(treeMaker.Ident(names.fromString(applicationPackage)), names.fromString(application));
        JCTree.JCExpression getApp = treeMaker.Select(app, names.fromString(applicationFunction));

        JCTree.JCExpression getString = treeMaker.Select(getApp, names.fromString("getString(R.string"));

        return treeMaker.Select(getString, names.fromString(value + ")"));
    }
}
