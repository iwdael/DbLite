package com.iwdael.dblite;

/**
 * author  : iwdael
 * e-mail  : iwdael@outlook.com
 * github  : http://github.com/iwdael
 * project : DbLite
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
