package com.hacknife.onlite.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * author  : Hacknife
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/hacknife
 * project : OnLite
 */
public class FileUtil {
    private static final String SPLIT = "/";

    public static String readFile(String packages, String clazz, String type) {
        String path = getPath(packages, clazz, type).replace("\\", "/");
        Logger.v("path--->" + path);
        return readTextFile(path);
    }

    private static String getPath(String packages, String clazz, String type) {
        String module = findMainModule();
        if (type.contains("java")) {
            return module + SPLIT + "src/main/java/" + packages.replace(".", SPLIT) + SPLIT + clazz + "." + type;
        } else if (type.contains("xml")) {
            return module + SPLIT + "src/main/res/" + packages + SPLIT + clazz + "." + type;
        }
        return "";
    }

    private static String readTextFile(String path) {
        StringBuffer sb = new StringBuffer();
        try {
            File file = new File(path);
            if (!file.exists() || file.isDirectory())
                throw new FileNotFoundException();
            BufferedReader br = new BufferedReader(new FileReader(file));
            String temp;

            temp = br.readLine();
            while (temp != null) {
                String replace = temp.replace(" ", "");
                if (replace.startsWith("//") | replace.startsWith("*") | replace.startsWith("/")) {
                } else if (replace.contains("//")) {
                    sb.append(temp.substring(0, temp.indexOf("//")));
                } else {
                    sb.append(temp);
                }
                temp = br.readLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private static String findMainModule() {
        File dir = new File(System.getProperty("user.dir") + SPLIT);
        Logger.v("dir:" + dir.getAbsolutePath());
        File[] modules = dir.listFiles();
        for (File module : modules) {
            if (!module.isDirectory()) continue;
            if (!new File(module.getAbsoluteFile() + "/build.gradle").exists()) continue;
            if (readTextFile(module.getAbsolutePath() + "/build.gradle").replace(" ","").contains("applyplugin:'com.android.application'")) {
                Logger.v("main module: " + module.getAbsolutePath());
                return module.getAbsolutePath();
            }
        }
        return "";
    }
}
