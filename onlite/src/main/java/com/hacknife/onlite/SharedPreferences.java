package com.hacknife.onlite;

import com.hacknife.onlite.annotation.Column;
import com.hacknife.onlite.annotation.Table;
import com.hacknife.onlite.annotation.Unique;
import com.hacknife.onlite.annotation.Version;

//@Table("shared_preferences_map")
//@Version(1)
class SharedPreferences {
    //    @Unique
//    @Column(name = "shared_preferences_key")
    String key;
    //    @Column(name = "shared_preferences_value")
    String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
