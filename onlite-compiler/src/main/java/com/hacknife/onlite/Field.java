package com.hacknife.onlite;

import java.util.Arrays;

/**
 * Created by Hacknife on 2018/11/1.
 */

public class Field {
    public static final String AutoInc = "AutoInc";
    public static final String Column = "Column";
    public static final String NotNull = "NotNull";
    public static final String Unique = "Unique";
    public static final String Ignore = "Ignore";
    public static final String Version = "Version";

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
    
    
    private String variable;
    private String clazzType;
    private String[] annotation;

    public Field(String variable, String classType, String[] anntations) {
        this.variable = variable;
        this.clazzType = classType;
        this.annotation = anntations;
    }


    public String getVariable() {
        return variable;
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
            if (s.contains(AutoInc)) return AutoInc;
        }
        return null;
    }

    public String getUnique() {
        if (annotation == null) return null;
        for (String s : annotation) {
            if (s.contains(Unique)) return Unique;
        }
        return null;
    }

    public String getNotNull() {
        if (annotation == null) return null;
        for (String s : annotation) {
            if (s.contains(NotNull)) return NotNull;
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
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"variable\":\"")
                .append(variable).append('\"');
        sb.append(",\"classType\":\"")
                .append(clazzType).append('\"');
        sb.append(",\"anntations\":")
                .append(Arrays.toString(annotation));
        sb.append('}');
        return sb.toString();
    }
}
