package com.iwdael.dblite;

import java.util.List;


/**
 * author  : iwdael
 * e-mail  : iwdael@outlook.com
 * github  : http://github.com/iwdael
 * project : DbLite
 */
public interface ILite<T> {

    long insert(T entity);

    long insert(List<T> entity);

    int update(T entity, T where);

    int update(T entity, Condition condition);

    int update(T entity, String where, String[] value);

    int updateOrInsert(T entity, T where);

    int updateOrInsert(T entity, Condition condition);

    int updateOrInsert(T entity, String where, String[] value);

    List<T> select();

    List<T> select(T where);

    List<T> select(Condition where);

    List<T> select(T where, Integer limit);

    List<T> select(Condition where, Integer limit);

    List<T> select(T where, Integer limit, Integer page);

    List<T> select(Condition where, Integer limit, Integer page);

    List<T> select(T where, String orderColumnName, Boolean asc);

    List<T> select(Condition where, String orderColumnName, Boolean asc);

    List<T> select(T where, Integer limit, String orderColumnName, Boolean asc);

    List<T> select(Condition where, Integer limit, String orderColumnName, Boolean asc);

    List<T> select(T where, Integer limit, Integer page, String orderColumnName, Boolean asc);

    List<T> select(Condition where, Integer limit, Integer page, String orderColumnName, Boolean asc);

    List<T> select(String where, String[] value, Integer limit, Integer page, String orderColumnName, Boolean asc);

    List<T> select(String where, String[] value);

    List<T> select(String where, String[] value, String orderColumnName, Boolean asc);

    List<T> select(String where, String[] value, Integer limit);

    List<T> select(String where, String[] value, Integer limit, String orderColumnName, Boolean asc);

    List<T> select(String where, String[] value, Integer limit, Integer page);

    int delete();

    int delete(T where);

    int delete(Condition where);

    int delete(String where, String[] value);

    boolean drop();

    boolean exists();

    int count();
}
