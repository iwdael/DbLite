package com.absurd.onlite.dao;

import java.util.List;

/**
 * Author: mr-absurd
 * Github: http://github.com/mr-absurd
 * Data: 2017/9/13.
 */

public interface IBaseLite<T> {
    String CONDITION_WHERE = "WHERE_CLASS";
    String CONDITION_ARGS = "WHERE_ARGS";

    Long insert(T entity);

    Long insert(List<T> entity);

    int updata(T entity, T where);

    int updataOrInsert(T entity, T where);

    List<T> select(T where);

    List<T> select(T where, Integer limit);

    int delete(T where);

    boolean deleteTable();
}
