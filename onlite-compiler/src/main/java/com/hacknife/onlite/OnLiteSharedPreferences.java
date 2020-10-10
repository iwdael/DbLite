package com.hacknife.onlite;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import javax.annotation.processing.Messager;

public class OnLiteSharedPreferences {

    private final static String _package = "com.hacknife.onlite.converter";
    private final static String full_SharedPreferencesConverter = "com.hacknife.onlite.SharedPreferencesConverter";
    private final static String SharedPreferencesConverter = "SharedPreferencesConverter";
    private final static String OnLiteSharedPreferencesConverter = "OnLiteSharedPreferencesConverter";

    public String createLite(Messager messager) {
        CompilationUnit unit = new CompilationUnit();
        unit.setPackageDeclaration(_package);
        unit.addImport(full_SharedPreferencesConverter);
        unit.setBlockComment("Created by http://github.com/hacknife/OnLite");

        ClassOrInterfaceDeclaration clazzOrInterface = unit.addClass(OnLiteSharedPreferencesConverter);
        clazzOrInterface.addImplementedType(full_SharedPreferencesConverter);
        clazzOrInterface.setBlockComment("*\n" +
                " * author  : Hacknife\n" +
                " * e-mail  : hacknife@outlook.com\n" +
                " * github  : http://github.com/hacknife\n" +
                " * project : OnLite\n" +
                " ");
        return unit.toString();
    }
}
