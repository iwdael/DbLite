package com.blackchopper.onlite;

import com.blackchopper.onlite.Filed;
import com.blackchopper.onlite.util.Logger;

import java.util.LinkedList;
import java.util.List;

/**
 * author  : Black Chopper
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/BlackChopper
 * project : OnLite
 */
public class Lite {
    private String source;

    public Lite(String source) {
        this.source = source;
        Logger.v("------>>" + source);
    }

    public List<Filed> getFileds() {
        String[] contents = source.split(";");
        List<Filed> target = new LinkedList<>();
        for (String content : contents) {
            if ((content.contains("(")) & (!content.contains("@"))
                    | content.contains("}")
                    | content.contains("{")
                    | content.contains("@Ignore")
                    | content.contains("="))
                continue;
            Logger.v(content);
            target.add(new Filed(content));
            Logger.v(target.get(target.size()-1).toString());
        }
        return target;
    }
}
