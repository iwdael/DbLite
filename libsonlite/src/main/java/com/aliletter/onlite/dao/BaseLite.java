package com.aliletter.onlite.dao;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.aliletter.onlite.annotation.OnTable;
import com.aliletter.onlite.entity.Condition;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: aliletter
 * Github: http://github.com/aliletter
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
    public List<T> select(T where, List<Condition> condition, Integer limit, Integer page, String orderColumnName, Boolean asc) {
        Cursor cursor;
        List<T> result = new ArrayList<>();
        String limitStr = null;
        if (limit != null && page != null) {
            limitStr = (limit * (page - 1)) + "," + limit;
        } else if (limit != null && page == null) {
            limitStr = String.valueOf(limit);
        }

        String key = null;
        String[] value = null;
        StringBuilder d_para = new StringBuilder();
        String[] d_value = null;
        if (condition != null) {
            List<String> list=new ArrayList<>();
            for (Condition s : condition) {
                d_para.append(" and    ");
                d_para.append(s.getCondition());
                list.add(s.getValue());
            }
            d_value = list.toArray(new String[list.size()]);
        }
        if (where != null) {
            Map<String, Object> con = getCondition(where);
            String condWhere = ((String) con.get(CONDITION_WHERE));
            String[] condArgv = (String[]) con.get(CONDITION_ARGS);
            if (condition != null) {
                List<String> condList = new ArrayList<>();
                for (String s : condArgv) {
                    condList.add(s);
                }
                for (String s : d_value) {
                    condList.add(s);
                }
                key = condWhere + d_para.toString();
                value = (String[]) condList.toArray(new String[condList.size()]);
            } else {
                key = condWhere;
                value = condArgv;
            }
        } else {
            if (condition != null) {
                key = d_para.toString().substring(6, d_para.toString().length());
                value = d_value;
            }
        }
        String order = null;
        if (orderColumnName != null) {
            order = orderColumnName + (asc ? " asc" : " desc");
        }

        cursor = sqLiteDatabase.query(false, tableName, null, key, value, null, null, order, limitStr);

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
    public List<T> select(T where, Integer limit, Integer page, String orderColumnName, Boolean asc) {
        return select(where, null, limit, page, orderColumnName, asc);
    }

    @Override
    public List<T> select(T where, Integer limit, Integer page) {
        return select(where, limit, page, null, null);
    }

    @Override
    public List<T> select(T where, Integer limit, String orderColumnName, Boolean asc) {
        return select(where, limit, null, orderColumnName, asc);
    }

    @Override
    public List<T> select(T where, Integer limit) {
        return select(where, limit, null, null, null);
    }

    @Override
    public List<T> select(T where, String orderColumnName, Boolean asc) {
        return select(where, null, null, orderColumnName, asc);
    }

    @Override
    public List<T> select(T where) {
        return select(where, null, null, null, null);
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
