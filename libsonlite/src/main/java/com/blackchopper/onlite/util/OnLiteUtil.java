package com.blackchopper.onlite.util;

import android.database.Cursor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

import java.util.List;


/**
 * Author：alilettter
 * Github: http://github.com/aliletter
 * Email: 4884280@qq.com
 * data: 2017/12/18
 */

public class OnLiteUtil {

    public static Object fromJsonString(String content, Class<?> clazz) {
        Object obj = null;
        if (content == null) return null;
        try {
            obj = clazz.newInstance();
            JSONObject json = new JSONObject(content);
            Field[] fields = obj.getClass().getDeclaredFields();
            Field[] var5 = fields;
            int var6 = fields.length;

            for (int var7 = 0; var7 < var6; ++var7) {
                Field field = var5[var7];
                if (!(field.getName().contains("serialVersionUID") | field.getName().contains("$change"))) {
                    field.setAccessible(true);
                    if (!json.isNull(field.getName())) {
                        setField(obj, field, json);
                    }
                }
            }
        } catch (InstantiationException var9) {
            var9.printStackTrace();
        } catch (IllegalAccessException var10) {
            var10.printStackTrace();
        } catch (JSONException var11) {
            var11.printStackTrace();
        }

        return obj;
    }

    public static Object newObject(Class<?> clazz, JSONObject json) {
        Object obj = null;

        try {
            obj = clazz.newInstance();
            Field[] fields = obj.getClass().getDeclaredFields();
            Field[] var4 = fields;
            int var5 = fields.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                Field field = var4[var6];
                if (!(field.getName().contains("serialVersionUID") | field.getName().contains("$change"))) {
                    field.setAccessible(true);
                    if (!json.isNull(field.getName())) {
                        setField(obj, field, json);
                    }
                }
            }
        } catch (InstantiationException var8) {
            var8.printStackTrace();
        } catch (IllegalAccessException var9) {
            var9.printStackTrace();
        } catch (JSONException var10) {
            var10.printStackTrace();
        }

        return obj;
    }

    private static void setField(Object obj, Field field, JSONObject jsonObject) throws JSONException, IllegalAccessException {
        if (jsonObject.optJSONArray(field.getName()) != null) {
            Class<?> clazz = getTypeFromList(field);
            if (clazz != null) {
                JSONArray array = jsonObject.getJSONArray(field.getName());
                ArrayList list = new ArrayList();

                for (int i = 0; i < array.length(); ++i) {
                    JSONObject object = array.getJSONObject(i);
                    list.add(newObject(clazz, object));
                }

                field.set(obj, list);
            }
        } else if (jsonObject.optJSONObject(field.getName()) != null) {
            field.set(obj, newObject(field.getType(), jsonObject.optJSONObject(field.getName())));
        } else if (Integer.class == field.getType()) {
            field.set(obj, Integer.valueOf(jsonObject.getInt(field.getName())));
        } else if (String.class == field.getType()) {
            field.set(obj, jsonObject.getString(field.getName()));
        } else if (Long.class == field.getType()) {
            field.set(obj, Long.valueOf(jsonObject.getLong(field.getName())));
        } else if (Float.class != field.getType()) {
            if (Double.class == field.getType()) {
                field.set(obj, Double.valueOf(jsonObject.getDouble(field.getName())));
            } else if (Boolean.class == field.getType()) {
                field.set(obj, Boolean.valueOf(jsonObject.getBoolean(field.getName())));
            } else if (Short.class != field.getType() && Byte.class != field.getType()) {
                if (Integer.TYPE == field.getType()) {
                    field.set(obj, Integer.valueOf(jsonObject.getInt(field.getName())));
                } else if (Long.TYPE == field.getType()) {
                    field.set(obj, Long.valueOf(jsonObject.getLong(field.getName())));
                } else if (Double.TYPE == field.getType()) {
                    field.set(obj, Double.valueOf(jsonObject.getDouble(field.getName())));
                } else if (Boolean.TYPE == field.getType()) {
                    field.set(obj, Boolean.valueOf(jsonObject.getBoolean(field.getName())));
                } else if (Short.TYPE == field.getType()) {
                    ;
                }
            }
        }

    }

    private static Class<?> getTypeFromList(Field field) {
        if (field.getType().isAssignableFrom(List.class)) {
            Type type = field.getGenericType();
            if (type == null) {
                return null;
            }

            if (type instanceof ParameterizedType) {
                ParameterizedType parameterType = (ParameterizedType) type;
                return (Class) parameterType.getActualTypeArguments()[0];
            }
        }

        return null;
    }

}
