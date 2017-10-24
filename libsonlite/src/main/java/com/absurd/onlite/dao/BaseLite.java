package com.absurd.onlite.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.absurd.onlite.base.OnTable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: mr-absurd
 * Github: http://github.com/mr-absurd
 * Data: 2017/9/13.
 */

public abstract class BaseLite<T> implements IBaseLite<T> {

    protected SQLiteDatabase sqLiteDatabase;
    protected boolean isInit = false;
    protected String tableName;
    protected Class<T> entityClass;
    protected Map<String, Field> cacheMap;

    public synchronized void init(Class<T> entity, SQLiteDatabase sqLiteDatabase) {
        if (!isInit) {
            this.sqLiteDatabase = sqLiteDatabase;
            if (entity.getAnnotation(OnTable.class) != null)
                this.tableName = entity.getAnnotation(OnTable.class).value();
            else
                this.tableName = entity.getSimpleName();
            entityClass = entity;
            sqLiteDatabase.execSQL(createTable(entityClass));
            initCacheMap();
        }
    }

    @Override
    public Long insert(List<T> entitys) {
        Long result = -1L;
        for (T entity : entitys) {
            result = insert(entity);
        }
        return result;
    }

    @Override
    public Long insert(T entity) {
        Map<String, String> map = getValues(entity);
        ContentValues values = getContentValues(map);
        Long result = sqLiteDatabase.insert(tableName, null, values);
        return result;
    }

    @Override
    public int updataOrInsert(T entity, T where) {
        int result = updata(entity, where);
        if (result == 0 | result == -1) {
            result = Integer.valueOf(String.valueOf(insert(entity)));
        }
        return result;
    }

    @Override
    public int updata(T entity, T where) {
        Map<String, String> mapValues = getValues(entity);
        ContentValues values = getContentValues(mapValues);
        Map<String, Object> condition = getCondition(where);
        int result = -1;
        result = sqLiteDatabase.update(this.tableName, values, (String) condition.get(CONDITION_WHERE), (String[]) condition.get(CONDITION_ARGS));
        return result;
    }

    @Override
    public List<T> select(T where, Integer limit) {
        Cursor cursor;
        List<T> result = new ArrayList<>();
        if (where == null) {
            cursor = sqLiteDatabase.query(false, tableName, null, null, null, null, null, null, String.valueOf(limit));
        } else {
            Map<String, Object> condition = getCondition(where);
            cursor = sqLiteDatabase.query(false, tableName, null, (String) condition.get(CONDITION_WHERE), (String[]) condition.get(CONDITION_ARGS), null, null, null, String.valueOf(limit));
        }
        while (cursor.moveToNext()) {
            try {
                T t = entityClass.newInstance();
                setValues(t, cursor);
                result.add(t);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        return result;
    }

    @Override
    public List<T> select(T where) {
        Cursor cursor;
        List<T> result = new ArrayList<>();
        if (where == null) {
            cursor = sqLiteDatabase.query(false, tableName, null, null, null, null, null, null, null);
        } else {
            Map<String, Object> condition = getCondition(where);
            cursor = sqLiteDatabase.query(false, tableName, null, (String) condition.get(CONDITION_WHERE), (String[]) condition.get(CONDITION_ARGS), null, null, null, null);
        }
        while (cursor.moveToNext()) {
            try {
                T t = entityClass.newInstance();
                setValues(t, cursor);
                result.add(t);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        return result;
    }

    @Override
    public int delete(T where) {
        Map<String, Object> condition = getCondition(where);
        int result = -1;
        result = sqLiteDatabase.delete(tableName, (String) condition.get(CONDITION_WHERE), (String[]) condition.get(CONDITION_ARGS));
        return result;
    }

    @Override
    public boolean deleteTable() {
        if (!tableIsExist()) return false;
        String sql = " DROP TABLE " + this.tableName;
        sqLiteDatabase.execSQL(sql);
        return true;
    }

    protected abstract boolean tableIsExist();


    protected abstract void initCacheMap();


    protected abstract String createTable(Class<T> entityClass);

    protected abstract Map<String, String> getValues(T entity);

    protected abstract ContentValues getContentValues(Map<String, String> map);

    protected abstract void setValues(T t, Cursor cursor);

    protected abstract Map<String, Object> getCondition(T where);


}
