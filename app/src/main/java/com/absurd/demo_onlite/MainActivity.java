package com.absurd.demo_onlite;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.absurd.onlite.OnLite;
import com.absurd.onlite.dao.OnLiteFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity implements View.OnClickListener {
    UserLite userLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OnLiteFactory.getInstance("/sdcard/Music/");
        userLite = OnLiteFactory.getInstance().getDataHelper(UserLite.class, User.class);
    }

    @Override
    public void onClick(View view) {
        UserLite lite = OnLiteFactory.getInstance().getDataHelper(UserLite.class, User.class);

        List<User> userLimitAndPage;
        User where = new User();
        where.setUsername("admin10");
        List<String> w=new ArrayList<>();
        w.add("id > ?");
        List<String> a=new ArrayList<>();
        a.add("300");
        Map<String,List<String>> condition=new HashMap<>();
        condition.put(OnLite.CONDITION_WHERE,w);
        condition.put(OnLite.CONDITION_ARGS,a);
        for (int i = 1; i < 7; i++) {
            userLimitAndPage = lite.select(null,null, 10, i,"id",false);
            Log.v("TAG", "----------------------page-->" + i + "-----------------------------------");
            for (User user : userLimitAndPage) {
                Log.v("TAG", user.toString());
            }
        }
        Log.v("TAG", "---------------------------------------------------------");
    }
}
