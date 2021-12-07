package com.iwdael.dblite;

public interface SharedPreferencesConverter {
    String objectConvertString(String key, Object value);

    <T> T stringConvertObject(Class<T> clazz, String content);
}
