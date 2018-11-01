package com.hacknife.onlite;


import com.hacknife.onlite.util.Logger;

import java.util.LinkedList;
import java.util.List;

/**
 * author  : Hacknife
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/hacknife
 * project : OnLite
 */
public class Filed {
    public static final String AUTHOR = "\n";
    public static final String AutoInc = "@AutoInc";
    public static final String Column = "@Column";
    public static final String NotNull = "@NotNull";
    public static final String Table = "@Table";
    public static final String Unique = "@Unique";
    public static final String Ignore = "@Ignore";
    public static final String empty = "";
    protected static String CREATE = "CREATE TABLE IF NOT EXISTS";
    protected static String NOT_NULL = " NOT NULL";
    protected static String NULL = " DEFAULT NULL";
    protected static String INTEGER = " INTEGER";
    protected static String PRIMARY_KEY = " PRIMARY KEY";
    protected static String UNIQUE = " UNIQUE";
    protected static String AUTOINCREMENT = " AUTOINCREMENT";
    protected static String VACHAR = " varchar(255)";
    protected static String DOUBLE = " double(64,0)";
    protected static String FLOAT = " float(32,0)";
    protected static String LONG = " int(64)";
    protected static String SHORT = " int(32)";
    protected static String INT = " int(32)";
    protected static String BLOB = " blob";
    protected static String TEXT = " TEXT";
    public String[] annotation;
    public String clazzType;
    public String varible;


    public Filed(String source) {
        if (source == null) return;
        String[] target = source.split(" ");
        List<String> list = new LinkedList<>();
        int count = 0;
        for (String s : target) {
            if (s.equalsIgnoreCase("")) continue;
            list.add(s);
            if (s.contains("@")) count++;
        }
        annotation = new String[count];
        for (int i = 0; i < count; i++) {
            annotation[i] = list.get(i);
        }
        if (list.size() >= 2) {
            clazzType = list.get(list.size() - 2);
            varible = list.get(list.size() - 1);
        }
        for (String s : annotation) {
            Logger.v(s);
        }
        Logger.v(toString());
    }

    @Override
    public String toString() {
        return "Filed{" +
                "annotation='" + (annotation==null? annotation:annotation.toString()) + '\'' +
                ", clazzType='" + clazzType + '\'' +
                ", varible='" + varible + '\'' +
                '}';
    }

    public String[] getAnnotation() {

        return annotation;
    }

    public String getClazzType() {
        return clazzType;
    }

    public String getVarible() {
        return varible;
    }

    public String getColumn() {
        if (annotation == null) return null;
        for (String s : annotation) {
            if (s.contains(Column)) {
                String tmp = s.replace(" ", "");
                int index = tmp.indexOf("(") + 2;
                return tmp.substring(index, tmp.length() - 2);
            }
        }

        return null;
    }


    public String getAutoInc() {
        if (annotation == null) return null;
        for (String s : annotation) {
            if (s.equalsIgnoreCase(AutoInc)) return AutoInc;
        }
        return null;
    }

    public String getUnique() {
        if (annotation == null) return null;
        for (String s : annotation) {
            if (s.equalsIgnoreCase(Unique)) return Unique;
        }
        return null;
    }

    public String getNotNull() {
        if (annotation == null) return null;
        for (String s : annotation) {
            if (s.equalsIgnoreCase(NotNull)) return NotNull;
        }
        return null;
    }

    public boolean isInteger() {
        return clazzType.equalsIgnoreCase("Integer") | clazzType.equalsIgnoreCase("int");
    }

    public boolean isDouble() {
        return clazzType.equalsIgnoreCase("Double") | clazzType.equalsIgnoreCase("double");
    }

    public boolean isFloat() {
        return clazzType.equalsIgnoreCase("Float") | clazzType.equalsIgnoreCase("float");
    }

    public boolean isString() {
        return clazzType.equalsIgnoreCase("String");
    }

    public boolean isLong() {
        return clazzType.equalsIgnoreCase("Long") | clazzType.equalsIgnoreCase("long");
    }

    public boolean isShort() {
        return clazzType.equalsIgnoreCase("Short") | clazzType.equalsIgnoreCase("short");
    }

    public boolean isByte() {
        return clazzType.equalsIgnoreCase("byte[]") | clazzType.equalsIgnoreCase("Byte[]");
    }

    public String type2Cursor() {

        if (isByte()) {
            return "Blob";
        }
        if (isInteger()) {
            return "Int";
        }
        if (isOther()) {
            return "String";
        }
        return clazzType;
    }

    public boolean isOther() {
        if (isInteger()) {
        } else if (isByte()) {
        } else if (isDouble()) {
        } else if (isFloat()) {
        } else if (isLong()) {
        } else if (isShort()) {
        } else if (isString()) {
        } else {
            return true;
        }
        return false;
    }
}
