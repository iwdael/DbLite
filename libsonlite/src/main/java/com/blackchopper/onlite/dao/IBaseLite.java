package com.blackchopper.onlite.dao;

import java.util.List;

/**
 * Author: aliletter
 * Github: http://github.com/aliletter
 * Data: 2017/9/13.
 */

public interface IBaseLite<T> {
    String CONDITION_WHERE = "WHERE_CLASS";
    String CONDITION_ARGS = "WHERE_ARGS";

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

    boolean deleteTable();
}
