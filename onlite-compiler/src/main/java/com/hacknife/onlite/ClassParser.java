package com.hacknife.onlite;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.ClassExpr;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.hacknife.onlite.annotation.Version;
import com.hacknife.onlite.util.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hacknife on 2018/10/31.
 */

public class ClassParser {
    public static void parser(String modulePath, String packageName, String className, final List<Field> fields, final List<Integer> versions) {
        String path = modulePath + "/src/main/java/" + packageName.replace(".", "/") + "/" + className + ".java";
        try {

            CompilationUnit parse = JavaParser.parse(new File(path));
            VoidVisitorAdapter<Object> adapter = new VoidVisitorAdapter<Object>() {

                @Override
                public void visit(FieldDeclaration n, Object arg) {
                    super.visit(n, arg);
                    String[] annotations = checkAnnatation(n.getAnnotations());
                    if (annotations != null)
                        fields.add(new Field(n.getVariable(0).getNameAsString(), n.getCommonType().asString(), annotations));
                }

                @Override
                public void visit(ClassOrInterfaceDeclaration n, Object arg) {
                    super.visit(n, arg);
                    NodeList<AnnotationExpr> annotationExprs = n.getAnnotations();
                    for (AnnotationExpr annotationExpr : annotationExprs) {

                        if (annotationExpr.getNameAsString().equals(Field.Version)) {
                            versions.add(getInteger(annotationExpr.toString()));
                            Logger.v(versions.toString());
                        }
                    }
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
                    annotationExpr.toString().contains(Field.NotNull) ||
                    annotationExpr.toString().contains(Field.Unique))
                annotaions.add(annotationExpr.toString());
            if (annotationExpr.toString().contains(Field.Ignore))
                return null;
        }
        return annotaions.toArray(new String[annotaions.size()]);
    }


    public static Integer getInteger(String str) {
        if (str == null || str.length() == 0) return 0;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
                builder.append(str.charAt(i));
            }
        }
        Logger.v("getInteger:"+builder.toString());
        return Integer.parseInt(builder.toString());
    }

}
