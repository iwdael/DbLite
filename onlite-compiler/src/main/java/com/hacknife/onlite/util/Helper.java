package com.hacknife.onlite.util;

/**
 * author  : Hacknife
 * e-mail  : hacknife@outlook.com
 * github  : http://github.com/hacknife
 * project : OnLite
 */
public class Helper {
    protected static String VARCHAR = "varchar(255)";
    protected static String DOUBLE = "double(64,0)";
    protected static String FLOAT = "float(32,0)";
    protected static String LONG = "int(64)";
    protected static String SHORT = "int(32)";
    protected static String INT = "int(32)";
    protected static String BYTE = "int(8)";
    protected static String BLOB = "blob";
    protected static String TEXT = "TEXT";


    public static String java2Sql(String filed) {
        switch (filed) {
            case "java.lang.String":
                return VARCHAR;

            case "java.lang.Byte":
            case "byte":
                return BYTE;

            case "java.lang.Integer":
            case "int":
                return INT;

            case "java.lang.Short":
            case "short":
                return SHORT;

            case "java.lang.Double":
            case "double":
                return DOUBLE;

            case "java.lang.Long":
            case "long":
                return LONG;

            case "java.lang.Float":
            case "float":
                return FLOAT;

            case "java.lang.Byte[]":
            case "byte[]":
                return BLOB;

            default:
                return TEXT;
        }
    }


    public static String sql2Java(String filed) {
        switch (filed) {

            case "java.lang.Byte":
            case "byte":
            case "java.lang.Integer":
            case "int":
                return "Int";

            case "java.lang.Short":
            case "short":
                return "Short";

            case "java.lang.Double":
            case "double":
                return "Double";

            case "java.lang.Long":
            case "long":
                return "Long";

            case "java.lang.Float":
            case "float":
                return "Float";

            case "java.lang.Byte[]":
            case "byte[]":
                return "Blob";

            default:
                return "String";
        }
    }
}
