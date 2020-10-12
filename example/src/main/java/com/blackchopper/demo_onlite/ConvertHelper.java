package com.blackchopper.demo_onlite;

import com.hacknife.onlite.annotation.Convert;

import java.util.List;

public class ConvertHelper {
    @Convert
    public static List<V> convert(String c) {
        return null;
    }

    @Convert
    public static String convert(List<V> c) {
        return null;
    }

    @Convert
    public static String objectConvertString(String key, Object value) {
        return null;
    }

    @Convert
    public static <T> T stringConvertObject(Class<T> clazz, String string) {
        return null;
    }
}
