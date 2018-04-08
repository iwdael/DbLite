package com.blackchopper.onlite.entity;

/**
 * author  : Black Chopper
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/BlackChopper
 * project : OnLite
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
