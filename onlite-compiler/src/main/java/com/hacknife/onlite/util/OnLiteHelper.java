package com.hacknife.onlite.util;

import java.lang.reflect.Field;

import javax.lang.model.element.Element;

/**
 * author  : Hacknife
 * e-mail  : hacknife@outlook.com
 * github  : http://github.com/hacknife
 * project : OnLite
 */
public class OnLiteHelper {
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

    public static final int COMMON = 1;
    public static final int OTHER = 2;
    public static final int BOOLEAN = 3;

    public static int javaFieldType(String filed) {
        switch (filed) {
            case "java.lang.String":

            case "java.lang.Byte[]":
            case "byte[]":

            case "java.lang.Long":
            case "long":

            case "java.lang.Double":
            case "double":

            case "java.lang.Short":
            case "short":

            case "java.lang.Integer":
            case "int":

            case "java.lang.Byte":
            case "byte":

            case "java.lang.Float":
            case "float":
                return COMMON;

            case "java.lang.Boolean":
            case "boolean":
                return BOOLEAN;
            default:
                return OTHER;
        }
    }


    public static boolean isFieldBoolean(String field) {
        return javaFieldType(field) == BOOLEAN;
    }

    public static boolean isFieldOther(String field) {
        return javaFieldType(field) == OTHER;
    }

    public static String sql2Set(Boolean targetIsKotlin, String field, String type) {
        boolean index3FieldIsUpper = field.length() >= 3 && Character.toUpperCase(field.charAt(2)) == field.charAt(2);
        if (isFieldBoolean(type)) {
            if (field.startsWith("is") && !targetIsKotlin) {
                if (index3FieldIsUpper) {
                    String name = field.replaceFirst("is", "");
                    return String.format("set%s", Character.toUpperCase(name.charAt(0)) + name.substring(1));
                }
            } else if (field.startsWith("is") && targetIsKotlin) {
                if (index3FieldIsUpper) {
                    String name = field.replaceFirst("is", "");
                    return String.format("set%s", Character.toUpperCase(name.charAt(0)) + name.substring(1));
                }
            } else if (field.startsWith("Is") && !targetIsKotlin) {
                if (index3FieldIsUpper) {
                    String name = field.replaceFirst("Is", "");
                    return String.format("set%s", Character.toUpperCase(name.charAt(0)) + name.substring(1));
                }
            }
        } else {
            if (field.startsWith("is") && targetIsKotlin && index3FieldIsUpper) {
                String name = field.replaceFirst("is", "");
                return String.format("set%s", Character.toUpperCase(name.charAt(0)) + name.substring(1));
            }
        }

        if (field.length() >= 2 && (Character.toLowerCase(field.charAt(0)) == field.charAt(0)) && (Character.toUpperCase(field.charAt(1)) == field.charAt(1)) && !targetIsKotlin)
            return String.format("set%s", field);
        return String.format("set%s", Character.toUpperCase(field.charAt(0)) + field.substring(1));
    }


    public static String sql2Get(Boolean targetIsKotlin, String field, String type) {
        boolean index3FieldIsUpper = field.length() >= 3 && Character.toUpperCase(field.charAt(2)) == field.charAt(2);
        if (isFieldBoolean(type)) {
            if (field.startsWith("is") && !targetIsKotlin) {
                if (index3FieldIsUpper) {
                    String name = field.replaceFirst("is", "");
                    return String.format("get%s", Character.toUpperCase(name.charAt(0)) + name.substring(1));
                }
            } else if (field.startsWith("is") && targetIsKotlin) {
                if (index3FieldIsUpper)
                    return field;
            } else if (field.startsWith("Is") && !targetIsKotlin) {
                if (index3FieldIsUpper) {
                    String name = field.replaceFirst("Is", "");
                    return String.format("get%s", Character.toUpperCase(name.charAt(0)) + name.substring(1));
                }
            }
        } else {
            if (field.startsWith("is") && targetIsKotlin && index3FieldIsUpper) {
                return field;
            }
        }
        if (field.length() >= 2 && (Character.toLowerCase(field.charAt(0)) == field.charAt(0)) && (Character.toUpperCase(field.charAt(1)) == field.charAt(1)) && !targetIsKotlin)
            return String.format("get%s", field);
        return String.format("get%s", Character.toUpperCase(field.charAt(0)) + field.substring(1));
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


    public static String restype(Element element) {
        try {
            Field restype_field = element.asType().getClass().getDeclaredField("restype");
            restype_field.setAccessible(true);
            return restype_field.get(element.asType()).toString();
        } catch (Exception e) {
            return resTtype(element);
        }
    }

    private static String tvars(Element element) {
        try {
            Field restype_field = element.asType().getClass().getDeclaredField("tvars");
            restype_field.setAccessible(true);
            return restype_field.get(element.asType()).toString();
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return "";
    }

    public static String argtypes(Element element) {
        try {
            Field argtypes_field = element.asType().getClass().getDeclaredField("argtypes");
            argtypes_field.setAccessible(true);
            return argtypes_field.get(element.asType()).toString();
        } catch (Exception e) {
            return argTtype(element);
        }
    }

    private static String argTtype(Element element) {
        try {
            Field qtype_field = element.asType().getClass().getField("qtype");
            qtype_field.setAccessible(true);
            String qtype = qtype_field.get(element.asType()).toString();
            return qtype.substring(qtype.indexOf("(") + 1, qtype.indexOf(")")).replaceAll(tvars(element), "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String resTtype(Element element) {
        try {
            Field qtype_field = element.asType().getClass().getField("qtype");
            qtype_field.setAccessible(true);
            String qtype = qtype_field.get(element.asType()).toString();
            return qtype.substring(qtype.indexOf(")") + 1);
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return null;
    }
}
