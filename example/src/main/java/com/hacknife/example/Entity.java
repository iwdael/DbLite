package com.hacknife.example;

import android.util.Log;

import com.hacknife.onlite.annotation.Column;
import com.hacknife.onlite.annotation.Table;
import com.hacknife.onlite.annotation.Version;

@Table
@Version(2)
public class Entity {
    @Column
    private String name;
    @Column
    private String pwsd;
    @Column
    private Boolean isPublish;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwsd() {
        return pwsd;
    }

    public void setPwsd(String pwsd) {
        this.pwsd = pwsd;
    }

    public Boolean getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(Boolean publish) {
        isPublish = publish;
    }

    @Override
    public String toString() {
        return "{" +
                "\"name\":\'" + name + "\'" +
                ", \"pwsd\":\'" + pwsd + "\'" +
                ", \"isPublish\":" + isPublish +
                '}';
    }
}
