package com.absurd.onlite.base;

import android.database.sqlite.SQLiteDatabase;

/**
 * Author: mr-absurd
 * Github: http://github.com/mr-absurd
 * Data: 2017/9/13.
 */

public class DaoManagerFactory {
    private static DaoManagerFactory instence;
    private static final String DATABASENAME="onlite.db";
    private String path="/sdcard/Music/"+DATABASENAME;
    private SQLiteDatabase sqLiteDatabase;
    public static DaoManagerFactory getInstance() {
        if (instence == null) {
            synchronized (DaoManagerFactory.class) {
                if (instence == null)
                    instence = new DaoManagerFactory();
            }
        }
        return instence;
    }

    private DaoManagerFactory() {
        openDatabase();
    }

    private void openDatabase(){
        sqLiteDatabase=SQLiteDatabase.openOrCreateDatabase(path,null);
    }

    public  synchronized    <T extends BaseDao<M>,M> T getDataHelper(Class<T> clazz,Class<M> entityClass){
        BaseDao baseDao=null;
        try {
            baseDao=clazz.newInstance();
            baseDao.init(entityClass,sqLiteDatabase);

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return (T) baseDao;
    }
}
