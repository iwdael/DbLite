package com.blackchopper.onlite.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;

/**
 * author  : Black Chopper
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/BlackChopper
 * project : OnLite
 */
public class OnLiteFactory {
    private static volatile OnLiteFactory instence = null;
    private static final String DATABASENAME = "onlite.db";
    private String path;
    private SQLiteDatabase sqLiteDatabase;

    public static OnLiteFactory getInstance() {
        if (instence == null) {
            synchronized (OnLiteFactory.class) {
                if (instence == null)
                    throw new RuntimeException("You must first implement a parameter constructor before you use OnLite !");
            }
        }
        return instence;
    }

    public static OnLiteFactory getInstance(String path) {
        if (instence == null) {
            synchronized (OnLiteFactory.class) {
                if (instence == null)
                    instence = new OnLiteFactory(path);
            }
        }
        return instence;
    }

    private OnLiteFactory(String path) {
        this.path = path;
        openDatabase();
    }

    private void openDatabase() {
        new File(this.path).mkdirs();
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(path + DATABASENAME, null);
    }

    public static <T extends BaseLite<M>, M> T getLiteHelper(Class<T> liteClass, Class<M> entityClass) {
        OnLiteFactory factory = getInstance();
        BaseLite baseLite = null;
        try {
            baseLite = liteClass.newInstance();
            baseLite.init(entityClass, factory.sqLiteDatabase);

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return (T) baseLite;
    }


    public Cursor rawQuery(String sql, String[] value) {
        return sqLiteDatabase.rawQuery(sql, value);
    }

    public void execSQL(String sql) {
        sqLiteDatabase.execSQL(sql);
    }
}