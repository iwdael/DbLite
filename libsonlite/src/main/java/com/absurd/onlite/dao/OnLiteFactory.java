package com.absurd.onlite.dao;

import android.database.sqlite.SQLiteDatabase;

/**
 * Author: mr-absurd
 * Github: http://github.com/mr-absurd
 * Data: 2017/9/13.
 */

public class OnLiteFactory {
    private static OnLiteFactory instence;
    private static final String DATABASENAME = "onlite.db";
    private String path = "/sdcard/Music/" + DATABASENAME;
    private SQLiteDatabase sqLiteDatabase;

    public static OnLiteFactory getInstance() {
        if (instence == null) {
            synchronized (OnLiteFactory.class) {
                if (instence == null)
                    instence = new OnLiteFactory();
            }
        }
        return instence;
    }

    public static OnLiteFactory getInstance(String path) {
        if (instence == null) {
            synchronized (OnLiteFactory.class) {
                if (instence == null)
                    instence = new OnLiteFactory();
            }
        }
        return instence;
    }

    private OnLiteFactory(String path) {
        this.path = path;
        openDatabase();
    }

    private OnLiteFactory() {
        openDatabase();
    }

    private void openDatabase() {
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(path, null);
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
