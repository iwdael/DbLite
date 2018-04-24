package com.blackchopper.onlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * author  : Black Chopper
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/BlackChopper
 * project : OnLite
 */
public abstract class OnLite<T> implements ILite<T> {

    protected SQLiteDatabase sqLiteDatabase;
    protected boolean isInit = false;
    protected String tableName;

      synchronized void init(SQLiteDatabase sqLiteDatabase) {
        if (!isInit) {
            this.sqLiteDatabase = sqLiteDatabase;
            sqLiteDatabase.execSQL(createTable());
        }
    }

    protected abstract String createTable();

    protected abstract String createSelection(T where);

    protected abstract ContentValues createContentValues(T entity);

    protected abstract String[] createSelectionArgv(T where);

    protected abstract T createObject(Cursor cursor);

    @Override
    public Long insert(T entity) {
        Long result = sqLiteDatabase.insert(tableName, null, createContentValues(entity));
        return result;
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
    public int updata(T entity, T where) {
        int result = -1;
        result = sqLiteDatabase.update(this.tableName, createContentValues(entity), createSelection(where), createSelectionArgv(where));
        return result;
    }

    @Override
    public int updata(T entity, String where, String[] value) {
        int result;
        result = sqLiteDatabase.update(this.tableName, createContentValues(entity), where, value);
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
    public int updataOrInsert(T entity, String where, String[] value) {
        int result = updata(entity, where, value);
        if (result == 0 | result == -1) {
            result = Integer.valueOf(String.valueOf(insert(entity)));
        }
        return result;
    }

    @Override
    public List<T> select(T where) {
        return select(where, null, null, null, null);
    }

    @Override
    public List<T> select(T where, Integer limit) {
        return select(where, limit, null, null, null);
    }

    @Override
    public List<T> select(T where, Integer limit, Integer page) {
        return select(where, limit, page, null, null);
    }

    @Override
    public List<T> select(T where, String orderColumnName, Boolean asc) {
        return select(where, null, null, orderColumnName, asc);
    }

    @Override
    public List<T> select(T where, Integer limit, String orderColumnName, Boolean asc) {
        return select(where, limit, null, orderColumnName, asc);
    }

    @Override
    public List<T> select(T where, Integer limit, Integer page, String orderColumnName, Boolean asc) {

        List<T> result = new ArrayList<>();
        String conditionWhere = null;
        String[] conditionValue = null;
        //升序或者降序
        String order = null;
        if (orderColumnName != null) {
            order = orderColumnName + (asc ? " asc" : " desc");
        }
        //分页查询
        String limitStr = null;
        if (limit != null && page != null) {
            limitStr = (limit * (page - 1)) + "," + limit;
        } else if (limit != null && page == null) {
            limitStr = String.valueOf(limit);
        }
        if (where != null) {
            conditionWhere = createSelection(where);
            conditionValue = createSelectionArgv(where);
        }
        Cursor cursor = sqLiteDatabase.query(false, tableName, null, conditionWhere, conditionValue, null, null, order, limitStr);
        while (cursor.moveToNext()) {
            try {

                result.add(createObject(cursor));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        return result;


    }

    @Override
    public List<T> select(String where, String[] value, Integer limit, Integer page, String orderColumnName, Boolean asc) {
        List<T> result = new ArrayList<>();
        String order = null;
        if (orderColumnName != null) {
            order = orderColumnName + (asc ? " asc" : " desc");
        }
        String limitStr = null;
        if (limit != null && page != null) {
            limitStr = (limit * (page - 1)) + "," + limit;
        } else if (limit != null && page == null) {
            limitStr = String.valueOf(limit);
        }
        Cursor cursor = sqLiteDatabase.query(false, tableName, null, where, value, null, null, order, limitStr);
        while (cursor.moveToNext()) {
            try {
                result.add(createObject(cursor));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        return result;
    }

    @Override
    public List<T> select(String where, String[] value) {
        return select(where, value, null, null, null);
    }

    @Override
    public List<T> select(String where, String[] value, String orderColumnName, Boolean asc) {
        return select(where, value, null, null, orderColumnName, asc);
    }

    @Override
    public List<T> select(String where, String[] value, Integer limit) {
        return select(where, value, limit, null, null, null);
    }

    @Override
    public List<T> select(String where, String[] value, Integer limit, String orderColumnName, Boolean asc) {
        return select(where, value, limit, null, orderColumnName, asc);
    }

    @Override
    public List<T> select(String where, String[] value, Integer limit, Integer page) {
        return select(where, value, limit, page, null, null);
    }

    @Override
    public int delete(T where) {
        int result = -1;
        result = sqLiteDatabase.delete(tableName, createSelection(where), createSelectionArgv(where));
        return result;
    }

    @Override
    public int delete(String where, String[] value) {
        int result;
        result = sqLiteDatabase.delete(tableName, where, value);
        return result;
    }

    @Override
    public boolean delete () {
        if (!exists()) return false;
        String sql = " DROP TABLE " + this.tableName;
        sqLiteDatabase.execSQL(sql);
        return true;
    }

    @Override
    public boolean exists() {
        boolean result = false;
        String sql = "SELECT COUNT(*) FROM sqlite_master where type='table' and name='" + tableName + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            int count = cursor.getInt(0);
            if (count > 0) {
                result = true;
            }
        }
        return result;
    }




}
