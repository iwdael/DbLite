package com.hacknife.onlite;


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
