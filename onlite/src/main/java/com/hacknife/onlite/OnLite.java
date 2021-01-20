package com.hacknife.onlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * author  : Hacknife
 * e-mail  : hacknife@outlook.com
 * github  : http://github.com/hacknife
 * project : OnLite
 */
public abstract class OnLite<T> implements ILite<T> {

    protected SQLiteDatabase sqLiteDatabase;
    protected boolean isInit = false;
    protected String tableName;
    protected Integer version;

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
    public long insert(T entity) {
        long result = sqLiteDatabase.insert(tableName, null, createContentValues(entity));
        return result;
    }

    @Override
    public long insert(List<T> entitys) {
        long result = -1L;
        for (T entity : entitys) {
            result = insert(entity);
        }
        return result;
    }

    @Override
    public int update(T entity, T where) {
        int result = -1;
        result = sqLiteDatabase.update(this.tableName, createContentValues(entity), createSelection(where), createSelectionArgv(where));
        return result;
    }

    @Override
    public int update(T entity, Condition condition) {
        return update(entity, condition.whereClause(), condition.whereArgs());
    }

    @Override
    public int update(T entity, String where, String[] value) {
        int result;
        result = sqLiteDatabase.update(this.tableName, createContentValues(entity), where, value);
        return result;
    }

    @Override
    public int updateOrInsert(T entity, T where) {
        int result = update(entity, where);
        if (result == 0 | result == -1) {
            result = Integer.valueOf(String.valueOf(insert(entity)));
        }
        return result;
    }

    @Override
    public int updateOrInsert(T entity, String where, String[] value) {
        int result = update(entity, where, value);
        if (result == 0 | result == -1) {
            result = Integer.valueOf(String.valueOf(insert(entity)));
        }
        return result;
    }

    @Override
    public int updateOrInsert(T entity, Condition condition) {
        return updateOrInsert(entity, condition.whereClause(), condition.whereArgs());
    }

    @Override
    public List<T> select() {
        return select((T) null);
    }

    @Override
    public List<T> select(T where) {
        return select(where, null, null, null, null);
    }

    @Override
    public List<T> select(Condition where) {
        return select(where.whereClause(), where.whereArgs());
    }

    @Override
    public List<T> select(T where, Integer limit) {
        return select(where, limit, null, null, null);
    }

    @Override
    public List<T> select(Condition where, Integer limit) {
        return select(where.whereClause(), where.whereArgs(), limit);
    }

    @Override
    public List<T> select(T where, Integer limit, Integer page) {
        return select(where, limit, page, null, null);
    }

    @Override
    public List<T> select(Condition where, Integer limit, Integer page) {
        return select(where.whereClause(), where.whereArgs(), limit, page);
    }

    @Override
    public List<T> select(T where, String orderColumnName, Boolean asc) {
        return select(where, null, null, orderColumnName, asc);
    }

    @Override
    public List<T> select(Condition where, String orderColumnName, Boolean asc) {
        return select(where.whereClause(), where.whereArgs(), orderColumnName, asc);
    }

    @Override
    public List<T> select(T where, Integer limit, String orderColumnName, Boolean asc) {
        return select(where, limit, null, orderColumnName, asc);
    }

    @Override
    public List<T> select(Condition where, Integer limit, String orderColumnName, Boolean asc) {
        return select(where.whereClause(), where.whereArgs(), limit, orderColumnName, asc);
    }


    @Override
    public List<T> select(Condition where, Integer limit, Integer page, String orderColumnName, Boolean asc) {
        return select(where.whereClause(), where.whereArgs(), limit, page, orderColumnName, asc);
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
    public int delete(Condition where) {
        return delete(where.whereClause(), where.whereArgs());
    }

    @Override
    public int delete(String where, String[] value) {
        int result;
        result = sqLiteDatabase.delete(tableName, where, value);
        return result;
    }

    @Override
    public boolean delete() {
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
        if (cursor.getCount() > 0) {
            result = true;
        }
        cursor.close();
        return result;
    }


    @Override
    public int count() {
        int count;
        String sql = "SELECT COUNT(*) FROM " + tableName;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        count = cursor.getCount();
        cursor.close();
        return count;
    }

    private void change() {
        String sql = "SELECT sql FROM sqlite_master WHERE type='table' AND name = '" + tableName + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        String createSql = cursor.getString(0);
        cursor.close();
    }
}
