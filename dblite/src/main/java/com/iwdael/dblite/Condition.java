package com.iwdael.dblite;
/**
 * author  : iwdael
 * e-mail  : iwdael@outlook.com
 * github  : http://github.com/iwdael
 * project : DbLite
 */
public abstract class Condition {
     public abstract String whereClause();

    public  abstract String[] whereArgs();
}
