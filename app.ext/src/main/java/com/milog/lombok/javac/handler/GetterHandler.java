package com.milog.lombok.javac.handler;

import com.milog.annotation.MyGetter;
import com.milog.lombok.javac.JavacNode;
import com.milog.lombok.javac.Log;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Name;

/**
 * Created by miloway on 2018/7/17.
 */

public class GetterHandler extends JavacAnnotationHandler<MyGetter>{

    private final String TAG = "GetterHandler";
    public GetterHandler(Context context) {
        super(context);
    }

    @Override
    public void handle(TreeMaker treeMaker, JavacNode node) {
        JCTree.JCMethodDecl methodDecl = createGetterMethod(treeMaker, node.variableDecl);
        Log.print(TAG + " " +methodDecl.getName().toString());
        node.classDecl.defs = node.classDecl.defs.prepend(methodDecl);
    }

    public JCTree.JCMethodDecl createGetterMethod(TreeMaker treeMaker, JCTree.JCVariableDecl variableDecl) {

        Name methodName = getMethodName(variableDecl.name, variableDecl.vartype.type);
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


    private Name getMethodName(Name name, Type type) {
        String s = name.toString();
        String get = "";
        if (type.hasTag(TypeTag.BOOLEAN)) {
            if (s.startsWith("is")) {
                get = s.substring(0, 1).toUpperCase() + s.substring(1, name.length());
            }
        }else {
            get = "get" + s.substring(0, 1).toUpperCase() + s.substring(1, name.length());
        }

        return names.fromString(get);
    }
}
