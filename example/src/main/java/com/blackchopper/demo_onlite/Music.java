package com.blackchopper.demo_onlite;

import com.blackchopper.onlite.annotation.Table;

/**
 * author  : Black Chopper
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/BlackChopper
 * project : OnLite
 */
@Table("music")
public class Music {
    String path;
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
