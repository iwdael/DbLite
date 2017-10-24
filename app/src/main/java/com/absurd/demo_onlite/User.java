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
    private Long mlong;
    private Short mshort;
    private Double mdouble;
    private Float mfloat;
    private byte[] mbyte;

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

    public Long getMlong() {
        return mlong;
    }

    public void setMlong(Long mlong) {
        this.mlong = mlong;
    }

    public Short getMshort() {
        return mshort;
    }

    public void setMshort(Short mshort) {
        this.mshort = mshort;
    }

    public double getMdouble() {
        return mdouble;
    }

    public void setMdouble(double mdouble) {
        this.mdouble = mdouble;
    }

    public float getMfloat() {
        return mfloat;
    }

    public void setMfloat(float mfloat) {
        this.mfloat = mfloat;
    }

    public byte[] getMbyte() {
        return mbyte;
    }

    public void setMbyte(byte[] mbyte) {
        this.mbyte = mbyte;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", pswd='" + pswd + '\'' +
                ", mlong=" + mlong +
                ", mshort=" + mshort +
                ", mdouble=" + mdouble +
                ", mfloat=" + mfloat +
                ", mbyte=" + Arrays.toString(mbyte) +
                '}';
    }
}
