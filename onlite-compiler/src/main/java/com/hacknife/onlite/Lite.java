package com.hacknife.onlite;


import com.hacknife.onlite.util.Logger;

import java.util.LinkedList;
import java.util.List;

/**
 * author   : Black Chopper
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/BlackChopper
 * project : OnLite
 */
public class Lite {
    private String source;

    public Lite(String source) {
        this.source = source;
    }

    public List<Filed> getFileds() {
        String[] contents = source.split(";");
        List<Filed> target = new LinkedList<>();
        for (String content : contents) {
            if ((content.contains("(")) & (!content.contains("@"))
                    | content.contains("}")
                    | content.contains("{")
                    | content.contains("@Ignore")
                    | content.contains("=")
                    | content.contains("+")
                    | content.contains("-")
                    | content.contains("*")
                    | content.contains("/")
                    | content.contains("%")
                    | content.contains("&"))
                continue;
            target.add(new Filed(content));

        }
        return target;
    }
}
