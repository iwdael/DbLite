package com.absurd.onlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.absurd.onlite.base.OnAutoIncreament;
import com.absurd.onlite.base.OnColumn;
import com.absurd.onlite.base.OnNotNull;
import com.absurd.onlite.base.OnUnique;
import com.absurd.onlite.dao.BaseLite;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: mr-absurd
 * Github: http://github.com/mr-absurd
 * Data: 2017/9/13.
 */

public abstract class OnLite<T> extends BaseLite<T> {
    protected static String CREATE = " CREATE TABLE IF NOT EXISTS ";
    protected static String NOT_NULL = "NOT NULL";
    protected static String NULL = " DEFAULT NULL ";
    protected static String INTEGER = " INTEGER ";
    protected static String PRIMARY_KEY = " PRIMARY KEY ";
    protected static String UNIQUE = " UNIQUE ";
    protected static String AUTOINCREMENT = " AUTOINCREMENT ";
    protected static String VACHAR = " varchar(255) ";
    protected static String DOUBLE = " double(64,0) ";
    protected static String FLOAT = " float(32,0) ";
    protected static String LONG = " int(64) ";
    protected static String SHORT = " int(32) ";
    protected static String INT = " int(32) ";
    protected static String BLOB = " blob ";

    @Override
    protected String createTable(Class<T> entityClass) {
        StringBuilder builder = new StringBuilder();
        builder.append(CREATE);
        builder.append(entityClass.getSimpleName()).append(" (\n");
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getName().equals("$change") | field.getName().equals("serialVersionUID"))
                continue;
            if (field.getAnnotation(OnColumn.class) != null)
                builder.append(" ").append(field.getAnnotation(OnColumn.class).value()).append(" ");
            else
                builder.append(" ").append(field.getName()).append(" ");
            if (field.getAnnotation(OnAutoIncreament.class) != null) {
                builder.append(" ").append(INTEGER).append(" ");
            } else if (field.getType() == Integer.class) {
                builder.append(" ").append(INTEGER).append(" ");
            } else if (field.getType() == Double.class) {
                builder.append(" ").append(DOUBLE).append(" ");
            } else if (field.getType() == Float.class) {
                builder.append(" ").append(FLOAT).append(" ");
            } else if (field.getType() == String.class) {
                builder.append(" ").append(VACHAR).append(" ");
            } else if (field.getType() == Long.class) {
                builder.append(" ").append(LONG).append(" ");
            } else if (field.getType() == Short.class) {
                builder.append(" ").append(SHORT).append(" ");
            } else if (field.getType() == int.class) {
                builder.append(" ").append(INT).append(" ");
            } else if (field.getType() == double.class) {
                builder.append(" ").append(DOUBLE).append(" ");
            } else if (field.getType() == float.class) {
                builder.append(" ").append(FLOAT).append(" ");
            } else if (field.getType() == long.class) {
                builder.append(" ").append(LONG).append(" ");
            } else if (field.getType() == short.class) {
                builder.append(" ").append(SHORT).append(" ");
            } else if (field.getType() == byte[].class) {
                builder.append(" ").append(BLOB).append(" ");
            } else if (field.getType() == Byte[].class) {
                builder.append(" ").append(BLOB).append(" ");
            }

            if (field.getAnnotation(OnAutoIncreament.class) != null) {
                builder.append(" ").append(PRIMARY_KEY).append(AUTOINCREMENT).append(",\n");
            } else if (field.getAnnotation(OnUnique.class) != null) {
                builder.append(" ").append(UNIQUE).append(NOT_NULL).append(",\n");
            } else if (field.getAnnotation(OnNotNull.class) != null) {
                builder.append(" ").append(NOT_NULL).append(",\n");
            } else {
                builder.append(" ").append(NULL).append(",\n");
            }
        }
        String temp = builder.toString();
        temp = temp.substring(0, temp.length() - 2) + ")";
        return temp;
    }

    protected void initCacheMap() {
        cacheMap = new HashMap<>();
        String sqlite = "select * from " + this.tableName;
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.rawQuery(sqlite, null);
            String[] columnNames = cursor.getColumnNames();
            Field[] fields = entityClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
            }
            for (String columnName : columnNames) {
                for (Field field : fields) {
                    String fieldname = null;
                    if (field.getAnnotation(OnColumn.class) != null) {
                        fieldname = field.getAnnotation(OnColumn.class).value();
                    } else {
                        fieldname = field.getName();
                    }
                    if (columnName.equals(fieldname)) {
                        cacheMap.put(columnName, field);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

    }

    @Override
    protected void setValues(T t, Cursor cursor) {
        try {
            for (Map.Entry<String, Field> entry : cacheMap.entrySet()) {
                Field field = entry.getValue();
                int index = cursor.getColumnIndex(entry.getKey());
                if (field.getType() == Integer.class) {
                    field.set(t, cursor.getInt(index));
                } else if (field.getType() == Double.class) {
                    field.set(t, cursor.getDouble(index));
                } else if (field.getType() == Float.class) {
                    field.set(t, cursor.getFloat(index));
                } else if (field.getType() == String.class) {
                    field.set(t, cursor.getString(index));
                } else if (field.getType() == Long.class) {
                    field.set(t, cursor.getLong(index));
                } else if (field.getType() == Short.class) {
                    field.set(t, cursor.getShort(index));
                } else if (field.getType() == int.class) {
                    field.set(t, cursor.getInt(index));
                } else if (field.getType() == double.class) {
                    field.set(t, cursor.getDouble(index));
                } else if (field.getType() == float.class) {
                    field.set(t, cursor.getFloat(index));
                } else if (field.getType() == long.class) {
                    field.set(t, cursor.getLong(index));
                } else if (field.getType() == short.class) {
                    field.set(t, cursor.getShort(index));
                } else if (field.getType() == byte[].class) {
                    field.set(t, cursor.getBlob(index));
                } else if (field.getType() == Byte[].class) {
                    field.set(t, cursor.getBlob(index));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected Map<String, String> getValues(T entity) {
        HashMap<String, String> map = new HashMap<>();
        for (Map.Entry<String, Field> entry : cacheMap.entrySet()) {
            String cacheKey = null;
            String cacheValue = null;
            if (entry.getValue().getAnnotation(OnColumn.class) != null) {
                cacheKey = entry.getValue().getAnnotation(OnColumn.class).value();
            } else {
                cacheKey = entry.getValue().getName();
            }
            try {
                if (null != entry.getValue().get(entity)) {

                    cacheValue = entry.getValue().get(entity).toString();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            map.put(cacheKey, cacheValue);
        }
        return map;
    }

    @Override
    protected ContentValues getContentValues(Map<String, String> map) {
        ContentValues contentValues = new ContentValues();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue() != null)
                contentValues.put(entry.getKey(), entry.getValue());
        }
        return contentValues;
    }

    @Override
    protected Map<String, Object> getCondition(T where) {
        Map<String, Object> result = new HashMap<>();
        Map<String, String> mapValues = getValues(where);
        StringBuilder builder = new StringBuilder();
        List<String> args = new ArrayList<>();
        builder.append(" 1 = 1");
        for (Map.Entry<String, String> entry : mapValues.entrySet()) {
            if (entry.getValue() != null) {
                builder.append(" and ").append(entry.getKey()).append(" = ? ");
                args.add(entry.getValue());
            }
        }
        result.put(CONDITION_WHERE, builder.toString());
        result.put(CONDITION_ARGS, args.toArray(new String[args.size()]));
        return result;

    }
}
