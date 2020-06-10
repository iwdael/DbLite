package com.blackchopper.demo_onlite;

import com.hacknife.onlite.annotation.Column;
import com.hacknife.onlite.annotation.NotNull;
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

    @Unique
    @NotNull
    @Column
    private String path;
    @Unique
    @Column
    String name;
    @Column
    String author;
    @Column
    String abulm;
    @Column
    String length;

    public Music() {

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAbulm() {
        return abulm;
    }

    public void setAbulm(String abulm) {
        this.abulm = abulm;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getLength() {
        return length;
    }
}
