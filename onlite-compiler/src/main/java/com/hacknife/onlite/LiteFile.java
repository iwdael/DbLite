package com.hacknife.onlite;


import com.hacknife.onlite.util.ClassValidator;
import com.hacknife.onlite.util.FileUtil;

import com.hacknife.onlite.util.StringUtil;

import java.util.List;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * author  : Hacknife
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/hacknife
 * project : OnLite
 */
public class LiteFile {
    public static final String PROXY = "Lite";
    private final TypeElement typeElement;
    private final String className;
    private final String packageName;
    private final String proxyClassName;
    private final String table;
    private final Lite lite;
    StringBuilder importBuilder = new StringBuilder();
    StringBuilder clazzBuilder = new StringBuilder();
    private List<Filed> fileds;

    public LiteFile(Elements elementUtils, TypeElement classElement, String table) {
        this.table = table;
        this.typeElement = classElement;
        PackageElement packageElement = elementUtils.getPackageOf(classElement);
        String packageName = packageElement.getQualifiedName().toString();
        className = ClassValidator.getClassName(classElement, packageName);
        this.packageName = packageName;
        this.proxyClassName = className + PROXY;
        String content = FileUtil.readFile(packageName, className, "java");
        int index = content.indexOf("@Table");
        content = content.substring(index);
        index = content.indexOf("{") + 1;
        lite = new Lite(content.substring(index, content.length()));
        fileds = lite.getFileds();

    }

    public CharSequence getProxyClassFullName() {
        return packageName + "." + proxyClassName;
    }

    public TypeElement getTypeElement() {
        return typeElement;
    }

    public String generateJavaCode() {
        importBuilder.append("// Generated by LiteProcessor (https://github.com/hacknife/OnLite).\n" +
                "// If you have any questions in use, please use email to contact me (e-mail:4884280@qq.com).\n");
        importBuilder.append("package ").append(packageName).append(";\n");
        importBuilder.append("import java.util.ArrayList;\n");
        importBuilder.append("import java.util.List;\n");
        importBuilder.append("import android.database.Cursor;\n");
        importBuilder.append("import android.content.ContentValues;\n");
        importBuilder.append("import com.hacknife.onlite.OnLite;\n");
        importBuilder.append(Filed.AUTHOR);
        clazzBuilder.append("public class " + proxyClassName + " extends OnLite<" + className + "> {\n");
        clazzBuilder.append(generateConstructor());
        clazzBuilder.append(generateCreateTableCode());
        clazzBuilder.append(generateCreateObject());
        clazzBuilder.append(generateContentValues());
        clazzBuilder.append(generateCreateSelection());
        clazzBuilder.append(generateCreateSelectionArgv());
        clazzBuilder.append("\n}");
        return importBuilder.append(clazzBuilder.toString()).toString();
    }

    public String generateCreateTableCode() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n\n    @Override\n" +
                "    protected String createTable() {\n");
        builder.append("        return \"" + Filed.CREATE);
        if (table.equalsIgnoreCase("")) {
            builder.append(" ").append(StringUtil.toLowerCaseFirstOne(className)).append(" (\"\n");
        } else {
            builder.append(" ").append(table).append(" (\"\n");
        }
        for (Filed filed : fileds) {
            if (filed.getColumn() != null) {
                builder.append("                + \"").append(filed.getColumn());
            } else {
                builder.append("                + \"").append(filed.getVarible());
            }
            if (filed.getAutoInc() != null) {
                builder.append(Filed.INTEGER);
            } else if (filed.isInteger()) {
                builder.append(Filed.INTEGER);
            } else if (filed.isDouble()) {
                builder.append(Filed.DOUBLE);
            } else if (filed.isFloat()) {
                builder.append(Filed.FLOAT);
            } else if (filed.isString()) {
                builder.append(Filed.VACHAR);
            } else if (filed.isLong()) {
                builder.append(Filed.LONG);
            } else if (filed.isShort()) {
                builder.append(Filed.SHORT);
            } else if (filed.isByte()) {
                builder.append(Filed.BLOB);
            } else {
                builder.append(Filed.TEXT);
            }
            if (filed.getAutoInc() != null) {
                builder.append(Filed.PRIMARY_KEY).append(Filed.AUTOINCREMENT).append(",\"\n");
            } else if (filed.getUnique() != null) {
                builder.append(Filed.UNIQUE).append(Filed.NOT_NULL).append(",\"\n");
            } else if (filed.getNotNull() != null) {
                builder.append(Filed.NOT_NULL).append(",\"\n");
            } else {
                builder.append(Filed.NULL).append(",\"\n");
            }

        }
        String temp = builder.toString();
        temp = temp.substring(0, temp.length() - 3);
        return temp + ")\";\n    }\n";
    }

    public String generateCreateObject() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n    @Override\n" +
                "    protected " + className + " createObject(Cursor cursor) {\n" +
                "        " + className + " " + StringUtil.toLowerCaseFirstOne(className) + " = new " + className + "();\n");

        for (Filed filed : fileds) {
            if (filed.getColumn() == null)
                builder.append("        " + StringUtil.toLowerCaseFirstOne(className) + ".set" + StringUtil.toUpperCaseFirstOne(filed.getVarible()) + "(cursor.get" + StringUtil.toUpperCaseFirstOne(filed.type2Cursor()) + "(cursor.getColumnIndex(\"" + filed.getVarible() + "\")));\n");
            else
                builder.append("        " + StringUtil.toLowerCaseFirstOne(className) + ".set" + StringUtil.toUpperCaseFirstOne(filed.getVarible()) + "(cursor.get" + StringUtil.toUpperCaseFirstOne(filed.type2Cursor()) + "(cursor.getColumnIndex(\"" + filed.getColumn() + "\")));\n");
        }
        builder.append("        return " + StringUtil.toLowerCaseFirstOne(className) + ";\n" +
                "    }");
        return builder.toString();
    }

    public String generateContentValues() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n\n    @Override\n" +
                "    protected ContentValues createContentValues(" + className + " " + StringUtil.toLowerCaseFirstOne(className) + ") {\n" +
                "        if (" + StringUtil.toLowerCaseFirstOne(className) + " == null) return null;\n" +
                "        ContentValues values = new ContentValues();\n");
        for (Filed filed : fileds) {
            builder.append("        if (" + StringUtil.toLowerCaseFirstOne(className) + ".get" + StringUtil.toUpperCaseFirstOne(filed.getVarible()) + "() != null)\n");
            if (filed.getColumn() == null)
                builder.append("            values.put(\"" + filed.getVarible() + "\", " + StringUtil.toLowerCaseFirstOne(className) + ".get" + StringUtil.toUpperCaseFirstOne(filed.getVarible()) + "()");
            else
                builder.append("            values.put(\"" + filed.getColumn() + "\", " + StringUtil.toLowerCaseFirstOne(className) + ".get" + StringUtil.toUpperCaseFirstOne(filed.getVarible()) + "()");
            if (filed.isOther())
                builder.append(".toString());\n");
            else
                builder.append(");\n");
        }
        builder.append("        return values;\n" +
                "    }");

        return builder.toString();
    }

    public String generateCreateSelection() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n\n    @Override\n" +
                "    protected String createSelection(" + className + " where) {\n" +
                "        if (where == null) return null;\n" +
                "        StringBuilder builder = new StringBuilder();\n" +
                "        builder.append(\"1 = 1 \");\n");
        for (Filed filed : fileds) {
            builder.append("        if (where.get" + StringUtil.toUpperCaseFirstOne(filed.getVarible()) + "() != null)\n");
            if (filed.getColumn() != null)
                builder.append("            builder.append(\"and \" + \"" + filed.getColumn() + " = ? \");\n");
            else
                builder.append("            builder.append(\"and \" + \"" + filed.getVarible() + " = ? \");\n");

        }
        builder.append("        return builder.toString();\n" +
                "    }");

        return builder.toString();
    }

    public String generateCreateSelectionArgv() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n\n    @Override\n" +
                "    protected String[] createSelectionArgv(" + className + " where) {\n" +
                "        if (where == null) return null;\n" +
                "        List<String> list = new ArrayList<>();\n");
        for (Filed filed : fileds) {
            builder.append("        if (where.get" + StringUtil.toUpperCaseFirstOne(filed.getVarible()) + "() !=null )\n");
            builder.append("            list.add(String.valueOf(where.get" + StringUtil.toUpperCaseFirstOne(filed.getVarible()) + "()));\n");
        }
        builder.append("        return list.toArray(new String[list.size()]);\n" +
                "    }");
        return builder.toString();
    }

    public String generateConstructor() {
        StringBuilder builder = new StringBuilder();
        builder.append("    public " + proxyClassName + "() {\n");
        if (table.equalsIgnoreCase(""))
            builder.append("        tableName = \"" + StringUtil.toLowerCaseFirstOne(className) + "\";\n");
        else
            builder.append("        tableName = \"" + table + "\";\n");
        builder.append("    }");
        return builder.toString();
    }
}