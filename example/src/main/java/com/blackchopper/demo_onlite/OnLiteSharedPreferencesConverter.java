package com.blackchopper.demo_onlite;

import com.hacknife.onlite.SharedPreferencesConverter;

public class OnLiteSharedPreferencesConverter implements SharedPreferencesConverter {
    @Override
    public String objectConvertString(String key, Object value) {
        return null;
    }

    @Override
    public <T> T stringConvertObject(Class<T> clazz, String string) {
        return null;
    }
}
