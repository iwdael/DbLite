package com.iwdael.dblite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.List;

/**
 * author  : iwdael
 * e-mail  : iwdael@outlook.com
 * github  : http://github.com/iwdael
 * project : DbLite
 */
public class DbLiteFactory {
    private static final String DATABASENAME = "dblite.db";
    private static volatile DbLiteFactory instence = null;
    private String path;
    private SQLiteDatabase sqLiteDatabase;
    private TableDbLite tableDbLite;

    private DbLiteFactory(String path) {
        this.path = path;
        openDatabase();
    }

    public static DbLiteFactory init(String absPath) {
        return DbLiteFactory.getInstance(absPath);
    }

    public static DbLiteFactory init(Context context, String directoryName) {
        return DbLiteFactory.getInstance(context.getDir(directoryName, Context.MODE_PRIVATE).getAbsolutePath() + "/");
    }

    private static DbLiteFactory getInstance() {
        if (instence == null) {
            synchronized (DbLiteFactory.class) {
                if (instence == null)
                    throw new RuntimeException("You must first implement a parameter constructor before you use DbLite !");
            }
        }
        return instence;
    }

    private static DbLiteFactory getInstance(String path) {
        if (instence == null) {
            synchronized (DbLiteFactory.class) {
                if (instence == null)
                    instence = new DbLiteFactory(path);
            }
        }
        getInstance().tableDbLite = DbLiteFactory.create(TableDbLite.class);
        return instence;
    }

    public static <T extends DbLite<M>, M> T create(Class<T> lite) {
        DbLiteFactory factory = getInstance();
        DbLite baseLite = null;
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
