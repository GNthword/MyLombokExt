package com.milog.lombok.javac.handler;

import com.milog.annotation.FunctionManager;
import com.milog.lombok.app.MyApp;
import com.milog.lombok.javac.JavacNode;
import com.milog.lombok.javac.Log;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;

import javax.tools.Diagnostic;

/**
 * Created by miloway on 2018/7/20.
 * FunctionManagerHandler
 */

public class FunctionManagerHandler extends JavacAnnotationHandler<FunctionManager> {

    private final String TAG = "fm ";

    public FunctionManagerHandler(Context context) {
        super(context);

    }

    @Override
    public void handle(TreeMaker treeMaker, JavacNode node) {
        String value = getValue(node.annotation);
        if (value == null || value.length() < 1) {
            return;
        }
        createInit(treeMaker, node.variableDecl, value);
        /*List<JCTree.JCImport> importList = node.unit.getImports();
        for (JCTree.JCImport jcImport : importList) {
            messager.printMessage(Diagnostic.Kind.NOTE, "fm import " + jcImport.qualid);
            messager.printMessage(Diagnostic.Kind.NOTE, "fm import " + jcImport.type);
            messager.printMessage(Diagnostic.Kind.NOTE, "fm import " + jcImport.qualid.getClass().getCanonicalName());
        }*/
        if (node.variableDecl.init == null) {
            return;
        }
        Log.print(Diagnostic.Kind.NOTE, TAG + node.variableDecl.init);
        Log.print(Diagnostic.Kind.NOTE, TAG + node.variableDecl.init.getClass().getCanonicalName());

        JCTree.JCMethodInvocation jcMethodInvocation = (JCTree.JCMethodInvocation) node.variableDecl.init;
        Log.print(Diagnostic.Kind.NOTE, TAG + jcMethodInvocation.typeargs);
        Log.print(Diagnostic.Kind.NOTE, TAG + jcMethodInvocation.meth);
        Log.print(Diagnostic.Kind.NOTE, TAG + jcMethodInvocation.args);
        Log.print(Diagnostic.Kind.NOTE, TAG + jcMethodInvocation.varargsElement);

        Log.print(Diagnostic.Kind.NOTE, TAG + jcMethodInvocation.meth.getClass().getCanonicalName());
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

    /**
     * var a = b;
     */
    private void createInit(TreeMaker treeMaker, JCTree.JCVariableDecl variableDecl, String value) {
        String applicationPackage = MyApp.getApplicationPackage();
        String application = MyApp.getApplicationName();
        String applicationFunction = MyApp.getApplicationFunction();
        JCTree.JCExpression app = treeMaker.Select(treeMaker.Ident(names.fromString(applicationPackage)), names.fromString(application));
        JCTree.JCExpression selectApp = treeMaker.Select(app, names.fromString(applicationFunction));

        JCTree.JCMethodInvocation getApp = treeMaker.Apply(List.<JCTree.JCExpression>nil(), selectApp, List.<JCTree.JCExpression>nil());
        JCTree.JCExpressionStatement expressionStatement = treeMaker.Exec(getApp);


        Log.print(variableDecl.vartype.getClass().getCanonicalName());
        Log.print(variableDecl.vartype.type.getClass().getCanonicalName());
        //second part
        JCTree.JCExpression selectRes = treeMaker.Select(expressionStatement.expr, names.fromString("getString"));
        ListBuffer<JCTree.JCExpression> buffer = new ListBuffer<>();
        buffer.add(treeMaker.Select(treeMaker.Select(treeMaker.Ident(names.fromString("R")), names.fromString("string")), names.fromString(value)));

        JCTree.JCMethodInvocation getRes = treeMaker.Apply(List.<JCTree.JCExpression>nil(), selectRes, buffer.toList());

        variableDecl.init = getRes;
    }

    private String getResFunctionName(Type type) {
        if (type.hasTag(TypeTag.CLASS)) {
            Log.print("type " + type.toString());
        }else if (type.hasTag(TypeTag.BOOLEAN)){
            return "getBoolean";
        }else if (type.hasTag(TypeTag.INT)) {
            return "getInteger";
        }else if (type.hasTag(TypeTag.ARRAY)) {

        }

        return "getString";
    }

    private String getResType(Type type) {
        if (type.hasTag(TypeTag.CLASS)) {
            type.toString();
        }else if (type.hasTag(TypeTag.BOOLEAN)){
            return "bool";
        }else if (type.hasTag(TypeTag.INT)) {
            return "integer";
        }else if (type.hasTag(TypeTag.ARRAY)) {

        }
        return "string";
    }

}
