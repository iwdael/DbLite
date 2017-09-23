package com.absurd.onlite.dao;

import android.database.sqlite.SQLiteDatabase;

import java.io.File;

/**
 * Author: mr-absurd
 * Github: http://github.com/mr-absurd
 * Data: 2017/9/13.
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

    public synchronized <T extends BaseLite<M>, M> T getDataHelper(Class<T> clazz, Class<M> entityClass) {
        BaseLite baseLite = null;
        try {
            baseLite = clazz.newInstance();
            baseLite.init(entityClass, sqLiteDatabase);

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return (T) baseLite;
    }
}
