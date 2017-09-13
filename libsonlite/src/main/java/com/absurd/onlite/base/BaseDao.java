package com.absurd.onlite.base;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Author: mr-absurd
 * Github: http://github.com/mr-absurd
 * Data: 2017/9/13.
 */

public abstract class BaseDao<T> implements IBaseDao<T> {

    private SQLiteDatabase sqLiteDatabase;
    private boolean isInit = false;
    private String tableName;
    private Class<T> entityClass;
    private Map<String, Field> cacheMap;

    public synchronized void init(Class<T> entity, SQLiteDatabase sqLiteDatabase) {
        if (!isInit) {
            this.sqLiteDatabase = sqLiteDatabase;
            this.tableName = entity.getAnnotation(OnTable.class).value();
            entityClass = entity;
            sqLiteDatabase.execSQL(createDataBase());
            initCacheMap();
        }
    }

    protected void initCacheMap() {
        String sqlite = "select * from " + this.tableName + " limit 1,0";
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.rawQuery(sqlite, null);
            String[] columnNames = cursor.getColumnNames();
            Field[] fields = entityClass.getFields();
            for (Field field : fields) {
                field.setAccessible(true);

            }
            for (String columnName : columnNames) {
                for (Field field : fields) {
                    String fieldname = null;
                    if (field.getAnnotation(OnField.class) != null) {
                        fieldname = field.getAnnotation(OnField.class).value();
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
        cacheMap = new HashMap<>();
    }

    public abstract String createDataBase();

    @Override
    public Long insert(T entity) {
        Map<String, String> map = getValues(entity);
        ContentValues values = getContentValues(map);
        Long result=sqLiteDatabase.insert(tableName,null,values);
        return result;
    }

    private Map<String, String> getValues(T entity) {
        HashMap<String, String> map = new HashMap<>();
        Iterator fieldIterator = cacheMap.values().iterator();
        while (fieldIterator.hasNext()) {
            Field colmunfield = (Field) fieldIterator.next();
            String cacheKey = null;
            String cacheValue = null;
            if (colmunfield.getAnnotation(OnField.class) != null) {
                cacheKey = colmunfield.getAnnotation(OnField.class).value();
            } else {
                cacheKey = colmunfield.getName();
            }
            try {
                if (null == colmunfield.get(entity)) {
                    continue;
                }
                cacheValue = colmunfield.get(entity).toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            map.put(cacheKey, cacheValue);
        }
        return map;
    }

    @Override
    public int updata(T entity, T where) {
        return -1;
    }

    public ContentValues getContentValues(Map<String, String> map) {
        ContentValues contentValues = new ContentValues();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue() != null)
                contentValues.put(entry.getKey(), entry.getValue());
        }
        return contentValues;
    }
}
