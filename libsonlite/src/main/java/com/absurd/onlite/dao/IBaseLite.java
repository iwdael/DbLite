package com.absurd.onlite.dao;

import com.absurd.onlite.entity.Condition;

import java.util.List;
import java.util.Map;

/**
 * Author: mr-absurd
 * Github: http://github.com/mr-absurd
 * Data: 2017/9/13.
 */

public interface IBaseLite<T> {
   public static String CONDITION_WHERE = "WHERE_CLASS";
    public static String CONDITION_ARGS = "WHERE_ARGS";

    Long insert(T entity);

    Long insert(List<T> entity);

    int updata(T entity, T where);

    int updataOrInsert(T entity, T where);

    List<T> select(T where);

    List<T> select(T where, String orderColumnName, Boolean asc);

    List<T> select(T where, Integer limit);

    List<T> select(T where, Integer limit, String orderColumnName, Boolean asc);


    List<T> select(T where, Integer limit, Integer page);

    List<T> select(T where, Integer limit, Integer page, String orderColumnName, Boolean asc);

    List<T> select(T where, List<Condition> condition, Integer limit, Integer page, String orderColumnName, Boolean asc);

    int delete(T where);

    boolean deleteTable();
}
