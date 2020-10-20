package com.hacknife.onlite;

/**
 * author  : Hacknife
 * e-mail  : hacknife@outlook.com
 * github  : http://github.com/hacknife
 * project : OnLite
 */
public class EmptyCondition extends Condition {

    @Override
    public String whereClause() {
        return "1 = 1";
    }

    @Override
    public String[] whereArgs() {
        return new String[]{};
    }

}
