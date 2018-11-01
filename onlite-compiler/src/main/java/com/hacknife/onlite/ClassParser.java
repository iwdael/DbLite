package com.hacknife.onlite;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.hacknife.onlite.util.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hacknife on 2018/10/31.
 */

public class ClassParser {


    public static void parser(String path, List<Filed> briefness) {
        try {
            CompilationUnit parse = JavaParser.parse(new File(path));
            VoidVisitorAdapter<Object> adapter = new VoidVisitorAdapter<Object>() {

                @Override
                public void visit(FieldDeclaration n, Object arg) {
                    super.visit(n, arg);
                    String[] ids = checkAnnatation(n.getAnnotations());
                    if (ids != null)
                        briefness.addField(new Field(n.getCommonType().toString(), n.getVariable(0).toString(), ids));
                }


            };
            adapter.visit(parse, null);
        } catch (Exception e) {
            Logger.v(e.getMessage());
        }
    }

    private static String[] checkAnnatation(NodeList<AnnotationExpr> annotationExprs) {
        List<String> annotaions = new ArrayList<>();
        for (AnnotationExpr annotationExpr : annotationExprs) {
            if (annotationExpr.toString().contains(Field.AutoInc) ||
                    annotationExpr.toString().contains(Field.Column) ||
                    annotationExpr.toString().contains(Field.Ignore) ||
                    annotationExpr.toString().contains(Field.NotNull) ||
                    annotationExpr.toString().contains(Field.Unique))
                annotaions.add(annotationExpr.getNameAsString());
        }
        return (String[]) annotaions.toArray();
    }

    private static String subString(String anntation) {
        if (anntation.contains("{")) {
            int start = anntation.indexOf("{");
            int end = anntation.lastIndexOf("}");
            return anntation.substring(start + 1, end).replaceAll(" ", "");
        } else {
            int start = anntation.indexOf("(");
            int end = anntation.lastIndexOf(")");
            return anntation.substring(start + 1, end).replaceAll(" ", "");
        }

    }


//    public static void main(String[] argv) {
//        Briefness briefness = new Briefness("");
//        parser("C:\\Users\\Hacknife\\Desktop\\briefness\\example\\src\\main\\java\\com\\hacknife\\demo\\MainActivity.java", briefness);
//        System.out.print(briefness.toString());
//    }
}
