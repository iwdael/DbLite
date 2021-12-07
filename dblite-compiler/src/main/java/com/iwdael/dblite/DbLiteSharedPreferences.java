package com.iwdael.dblite;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.ReceiverParameter;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.type.TypeParameter;

import java.util.Arrays;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;

public class DbLiteSharedPreferences {

    private final static String _package = "com.iwdael.dblite.converter";
    private final static String FULL_SharedPreferencesConverter = "com.iwdael.dblite.SharedPreferencesConverter";
    private final static String FULL_DbLiteSharedPreferencesConverter = "com.iwdael.dblite.converter.DbLiteSharedPreferencesConverter";

    public static final String FULL_STRING_CLASS_NAME = String.class.getName();
    public static final String FULL_OBJECT_CLASS_NAME = String.class.getName();
    public static final String FULL_CLASS_NAME = Class.class.getName();

    private final static String SharedPreferencesConverter = "SharedPreferencesConverter";
    private final static String DbLiteSharedPreferencesConverter = "DbLiteSharedPreferencesConverter";

    private Element stringConvertObject;
    private Element objectConvertString;

    public void setStringConvertObject(Element stringConvertObject) {
        this.stringConvertObject = stringConvertObject;
    }

    public void setObjectConvertString(Element objectConvertString) {
        this.objectConvertString = objectConvertString;
    }

    public Element getObjectConvertString() {
        return objectConvertString;
    }

    public Element getStringConvertObject() {
        return stringConvertObject;
    }

    public String createLite(Messager messager) {
        CompilationUnit unit = new CompilationUnit();
        unit.setPackageDeclaration(_package);
        unit.addImport(FULL_SharedPreferencesConverter);
        unit.addImport(FULL_STRING_CLASS_NAME);
        unit.addImport(FULL_OBJECT_CLASS_NAME);
        unit.addImport(FULL_CLASS_NAME);
        unit.setBlockComment("Created by http://github.com/iwdael/DbLite");

        ClassOrInterfaceDeclaration clazzOrInterface = unit.addClass(DbLiteSharedPreferencesConverter);
        clazzOrInterface.addImplementedType(SharedPreferencesConverter);
        clazzOrInterface.setBlockComment("*\n" +
                " * author  : iwdael\n" +
                " * e-mail  : iwdael@outlook.com\n" +
                " * github  : http://github.com/iwdael\n" +
                " * project : DbLite\n" +
                " ");
        clazzOrInterface
                .addMethod("objectConvertString", Modifier.PUBLIC)
                .setAnnotations(NodeList.nodeList(new MarkerAnnotationExpr("Override")))
                .setParameters(
                        new NodeList(Arrays.asList(new Parameter(new TypeParameter(String.class.getSimpleName()), "key"),
                                new Parameter(new TypeParameter(Object.class.getSimpleName()), "value"))))
                .setType(String.class)
                .setBody(createObjectConvertString());

        clazzOrInterface
                .addMethod("stringConvertObject", Modifier.PUBLIC)
                .setAnnotations(NodeList.nodeList(new MarkerAnnotationExpr("Override")))
                .setParameters(new NodeList(Arrays.asList(new Parameter(new TypeParameter(String.class.getSimpleName()), "content"))))
                .setType("T")
                .addTypeParameter("T")
                .setReceiverParameter(new ReceiverParameter(new TypeParameter("Class<T>"), "clazz"))
                .setBody(createStringConvertObject());
        return unit.toString();
    }

    private BlockStmt createObjectConvertString() {
        BlockStmt stmt = new BlockStmt();
        if (objectConvertString == null)
            stmt.addStatement(new ReturnStmt().setExpression(new NullLiteralExpr()));
        else
            stmt.addStatement(new ReturnStmt()
                    .setExpression(
                            new MethodCallExpr(String.format("%s.%s", objectConvertString.getEnclosingElement().toString(), objectConvertString.getSimpleName().toString()),
                                    new NameExpr("key"), new NameExpr("value")
                            )
                    )
            );
        return stmt;
    }

    private BlockStmt createStringConvertObject() {
        BlockStmt stmt = new BlockStmt();
        if (stringConvertObject == null)
            stmt.addStatement(new ReturnStmt().setExpression(new NullLiteralExpr()));
        else
            stmt.addStatement(new ReturnStmt()
                    .setExpression(
                            new MethodCallExpr(String.format("%s.%s", stringConvertObject.getEnclosingElement().toString(), stringConvertObject.getSimpleName().toString()),
                                    new NameExpr("clazz"), new NameExpr("content")
                            )
                    )
            );
        return stmt;
    }

    public String getDbLiteClass() {
        return FULL_DbLiteSharedPreferencesConverter;
    }
}
