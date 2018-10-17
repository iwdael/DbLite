package com.hacknife.onlite.util;

import java.io.File;

/**
 * author  : Hacknife
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/hacknife
 * project : OnLite
 */
public class StringUtil {
    //首字母转小写
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    //首字母转大写
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }


    public static String findBuildDir(String path) {
        File file = new File(path);
        while (!file.getParentFile().getPath().endsWith(Constant.build)) {
            file = file.getParentFile();
        }
        return file.getParentFile().getParentFile().getPath();
    }
}
