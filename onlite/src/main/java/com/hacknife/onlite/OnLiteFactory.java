package com.hacknife.onlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;

/**
 * author  : Hacknife
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/hacknife
 * project : OnLite
 */
public class OnLiteFactory {
    private static final String DATABASENAME = "onlite.db";
    private static volatile OnLiteFactory instence = null;
    private String path;
    private SQLiteDatabase sqLiteDatabase;

    private OnLiteFactory(String path) {
        this.path = path;
        openDatabase();
    }

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

    public static <T extends OnLite<M>, M> T create(Class<T> lite) {
        OnLiteFactory factory = getInstance();
        OnLite baseLite = null;
        try {
            baseLite = lite.newInstance();
            baseLite.init(factory.sqLiteDatabase);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) baseLite;
    }

    private void openDatabase() {
        new File(this.path).mkdirs();
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(path + DATABASENAME, null);
    }

    public Cursor rawQuery(String sql, String[] value) {
        return sqLiteDatabase.rawQuery(sql, value);
    }

    public void execSQL(String sql) {
        sqLiteDatabase.execSQL(sql);
    }
}
