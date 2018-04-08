package com.blackchopper.onlite;

/**
 * author  : Black Chopper
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/BlackChopper
 * project : OnLite
 */
public interface JsonConverter {
    <T> T OnConvert(T t, String json);
}
