package com.iwdael.dblite;

import java.util.List;

public class DbLiteSharedPreferences {
    private final static String DbLiteSharedPreferencesConverter = "com.iwdael.dblite.converter.DbLiteSharedPreferencesConverter";
    private static SharedPreferencesConverter converter;

    static {
        try {
            converter = (SharedPreferencesConverter) Class.forName(DbLiteSharedPreferencesConverter).newInstance();
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
        DbLiteFactory
                .create(SharedPreferencesLite.class)
                .updateOrInsert(entity, where);
    }

    public static <T> T obtain(String key, T defaultT) {
        if (defaultT == null || key == null) return defaultT;
        if (converter == null)
            throw new IllegalArgumentException("you should convert string/object to object/string");
        SharedPreferences where = new SharedPreferences();
        where.setKey(key);
        List<SharedPreferences> preferences = DbLiteFactory
                .create(SharedPreferencesLite.class)
                .select(where);
        if (preferences.isEmpty()) return defaultT;
        SharedPreferences sharedPreferences = preferences.get(0);
        if (sharedPreferences.getValue() == null) return defaultT;
        return converter.stringConvertObject((Class<T>) defaultT.getClass(), sharedPreferences.getValue());
    }


    public static <T> T obtain(String key, Class<T> clazz) {
        if (key == null) return null;
        if (converter == null)
            throw new IllegalArgumentException("you should convert string/object to object/string");
        SharedPreferences where = new SharedPreferences();
        where.setKey(key);
        List<SharedPreferences> preferences = DbLiteFactory
                .create(SharedPreferencesLite.class)
                .select(where);
        if (preferences.isEmpty()) return null;
        SharedPreferences sharedPreferences = preferences.get(0);
        if (sharedPreferences.getValue() == null) return null;
        return converter.stringConvertObject(clazz, sharedPreferences.getValue());
    }
}
