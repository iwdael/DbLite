package com.blackchopper.onlite;

import java.util.List;

/**
 * author  : Black Chopper
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/BlackChopper
 * project : OnLite
 */
public interface ILite<T> {

    Long insert(T entity);

    Long insert(List<T> entity);

    int updata(T entity, T where);

    int updata(T entity, String where, String[] value);

    int updataOrInsert(T entity, T where);

    int updataOrInsert(T entity, String where, String[] value);

    List<T> select(T where);

    List<T> select(T where, Integer limit);

    List<T> select(T where, Integer limit, Integer page);

    List<T> select(T where, String orderColumnName, Boolean asc);

    List<T> select(T where, Integer limit, String orderColumnName, Boolean asc);

    List<T> select(T where, Integer limit, Integer page, String orderColumnName, Boolean asc);

    List<T> select(String where, String[] value, Integer limit, Integer page, String orderColumnName, Boolean asc);

    List<T> select(String where, String[] value);

    List<T> select(String where, String[] value, String orderColumnName, Boolean asc);

    List<T> select(String where, String[] value, Integer limit);

    List<T> select(String where, String[] value, Integer limit, String orderColumnName, Boolean asc);

    List<T> select(String where, String[] value, Integer limit, Integer page);

    int delete(T where);

    int delete(String where, String[] value);

    boolean delete();

    boolean exists();
}
