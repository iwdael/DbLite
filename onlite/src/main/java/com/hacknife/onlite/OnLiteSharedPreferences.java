package com.hacknife.onlite;

import java.util.List;

public class OnLiteSharedPreferences {
    private final static String OnLiteSharedPreferencesConverter = "com.hacknife.onlite.converter.OnLiteSharedPreferencesConverter";
    private static SharedPreferencesConverter converter;

    static {
        try {
            converter = (SharedPreferencesConverter) Class.forName(OnLiteSharedPreferencesConverter).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hold(String key, Object value) {
        if (value == null || key == null) return;

        if (converter == null)
            throw new IllegalArgumentException("you should convert string/object to object/string");

        SharedPreferences entity = new SharedPreferences();
        entity.setKey(key);
        entity.setValue(converter.objectConvertString(key, value));

        SharedPreferences where = new SharedPreferences();
        where.setKey(key);
        OnLiteFactory
                .create(SharedPreferencesLite.class)
                .updataOrInsert(entity, where);
    }

    public static <T> T obtain(Class<T> clazz, String key) {
        if (clazz == null || key == null) return null;
        if (converter == null)
            throw new IllegalArgumentException("you should convert string/object to object/string");
        SharedPreferences where = new SharedPreferences();
        where.setKey(key);
        List<SharedPreferences> preferences = OnLiteFactory
                .create(SharedPreferencesLite.class)
                .select(where);
        if (preferences.isEmpty()) return null;
        SharedPreferences sharedPreferences = preferences.get(0);
        if (sharedPreferences.getValue() == null) return null;
        return converter.stringConvertObject(clazz, sharedPreferences.getValue());
    }



}
