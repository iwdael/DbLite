package com.blackchopper.onlite.entity;

/**
 * Author: aliletter
 * Github: http://github.com/aliletter
 * Data: 2017/10/31.
 */

public class Condition {
    private String condition;
    private String value;

    public Condition(String condition, String value) {
        this.condition = condition;
        this.value = value;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
