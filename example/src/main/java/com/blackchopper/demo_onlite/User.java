package com.blackchopper.demo_onlite;

import com.hacknife.onlite.annotation.AutoInc;
import com.hacknife.onlite.annotation.Column;
import com.hacknife.onlite.annotation.Table;
import com.hacknife.onlite.annotation.Unique;
import com.hacknife.onlite.annotation.Version;

/**
 * author  : Hacknife
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/hacknife
 * project : OnLite
 */
@Table("user_info")
@Version(1)
public class User {

    @Column(name = "_id")
    @AutoInc
    Integer id;

    @Column(name = "name")
    String name;

    @Unique
    @Column(name = "_pwd")
    String pwd;
    @Column(name = "_img")
    String img;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
