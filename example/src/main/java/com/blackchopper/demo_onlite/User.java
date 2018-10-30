package com.blackchopper.demo_onlite;

import com.hacknife.onlite.annotation.AutoInc;
import com.hacknife.onlite.annotation.Table;

/**
 * author  : Hacknife
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/hacknife
 * project : OnLite
 */
@Table("userinfo")
public class User {
    @AutoInc
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
