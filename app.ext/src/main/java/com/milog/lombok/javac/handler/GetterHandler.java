/*
 * Copyright (C) 2009-2012 The Project Lombok Authors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.milog.lombok.javac.handler;

import com.milog.MyGetter;
import com.milog.lombok.javac.JavacNode;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Names;

import javax.lang.model.element.Modifier;

/**
 * Created by miloway on 2018/7/17.
 */

public class GetterHandler  extends JavacAnnotationHandler<MyGetter>{
    private Names names;

    public GetterHandler(Context context) {
        names = Names.instance(context);
    }

    @Override
    public void handle(TreeMaker treeMaker, JavacNode node) {
        JCTree.JCMethodDecl methodDecl = createGetterMethod(treeMaker, node.variableDecl);
        node.classDecl.defs.prepend(methodDecl);
    }

    public JCTree.JCMethodDecl createGetterMethod(TreeMaker treeMaker, JCTree.JCVariableDecl variableDecl) {

        Name methodName = getMethodName(variableDecl.name);
        JCTree.JCReturn jcReturn = treeMaker.Return(treeMaker.Select(treeMaker.Ident(names.fromString("this")), variableDecl.getName()));
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC);
        ListBuffer<JCTree.JCStatement> statements = new ListBuffer<>();
        statements.append(jcReturn);
        JCTree.JCBlock block = treeMaker.Block(Flags.BLOCK, statements.toList());

        statements.clear();

        return treeMaker.MethodDef(modifiers, methodName, variableDecl.vartype, null, null, null, block, null);
    }


    private Name getMethodName(Name name) {
        String s = name.toString();
        return names.fromString("get" + s.substring(0, 1).toUpperCase() + s.substring(1, name.length()));
    }
}
