package com.absurd.demo_onlite;

import com.absurd.onlite.base.OnAutoIncreament;
import com.absurd.onlite.base.OnUnique;

import java.util.Arrays;

/**
 * Author: mr-absurd
 * Github: http://github.com/mr-absurd
 * Data: 2017/9/14.
 */

public class User {
    @OnAutoIncreament
    private Integer id;
    private String username;
    private String pswd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", pswd='" + pswd + '\'' +
                '}';
    }
}
