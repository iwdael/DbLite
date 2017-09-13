package com.absurd.onlite.base;

/**
 * Author: mr-absurd
 * Github: http://github.com/mr-absurd
 * Data: 2017/9/13.
 */

public interface IBaseDao<T> {
    Long insert(T entity);
    int updata(T entity, T where);
}
