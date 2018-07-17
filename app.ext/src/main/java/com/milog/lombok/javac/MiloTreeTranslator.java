package com.milog.lombok.javac;

import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeTranslator;

/**
 * Created by miloway on 2018/7/17.
 */

public class MiloTreeTranslator extends TreeTranslator {


    @Override
    public void visitClassDef(JCTree.JCClassDecl tree) {
        super.visitClassDef(tree);
    }
}
