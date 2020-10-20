package com.hacknife.onlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.List;

/**
 * author  : Hacknife
 * e-mail  : hacknife@outlook.com
 * github  : http://github.com/hacknife
 * project : OnLite
 */
public class OnLiteFactory {
    private static final String DATABASENAME = "onlite.db";
    private static volatile OnLiteFactory instence = null;
    private String path;
    private SQLiteDatabase sqLiteDatabase;
    private TableDbLite tableDbLite;

    private OnLiteFactory(String path) {
        this.path = path;
        openDatabase();
    }

    public static OnLiteFactory init(String absPath) {
        return OnLiteFactory.getInstance(absPath);
    }

    public static OnLiteFactory init(Context context, String directoryName) {
        return OnLiteFactory.getInstance(context.getDir(directoryName, Context.MODE_PRIVATE).getAbsolutePath() + "/");
    }

    private static OnLiteFactory getInstance() {
        if (instence == null) {
            synchronized (OnLiteFactory.class) {
                if (instence == null)
                    throw new RuntimeException("You must first implement a parameter constructor before you use OnLite !");
            }
        }
        return instence;
    }

    private static OnLiteFactory getInstance(String path) {
        if (instence == null) {
            synchronized (OnLiteFactory.class) {
                if (instence == null)
                    instence = new OnLiteFactory(path);
            }
        }
        getInstance().tableDbLite = OnLiteFactory.create(TableDbLite.class);
        return instence;
    }

    public static <T extends OnLite<M>, M> T create(Class<T> lite) {
        OnLiteFactory factory = getInstance();
        OnLite baseLite = null;
        try {
            baseLite = lite.newInstance();
            if (!lite.equals(TableDbLite.class)) {
                List<TableDb> tableDbs = factory.tableDbLite.select(new TableDb(baseLite.tableName));
                if (tableDbs.size() == 0) {
                    factory.tableDbLite.insert(new TableDb(baseLite.tableName, baseLite.version));
                } else {
                    if (!tableDbs.get(0).tableVersion.equals(baseLite.version)) {
                        factory.tableDbLite.update(new TableDb(baseLite.tableName, baseLite.version), new TableDb(baseLite.tableName));
                        baseLite.init(factory.sqLiteDatabase);
                        baseLite.delete();
                    }
                }
            }
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
