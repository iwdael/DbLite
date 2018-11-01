package com.blackchopper.demo_onlite;

import com.hacknife.onlite.annotation.Column;
import com.hacknife.onlite.annotation.Table;
import com.hacknife.onlite.annotation.Unique;

/**
 * author  : Hacknife
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/hacknife
 * project : OnLite
 */
@Table("music")
public class Music {
    @Column( "url" )
    String path;
    @Column( "fileName" )
    @Unique
    String name;

    public Music(String admin, String s) {
        path = admin;
        name = s;
    }

    public Music() {
    }

    public String getPath() {
        return path ;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name ;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Music{" +
                "path='" + path + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
