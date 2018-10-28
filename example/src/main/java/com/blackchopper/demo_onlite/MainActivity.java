package com.blackchopper.demo_onlite;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.hacknife.onlite.OnLiteFactory;

import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {
    UserLite userLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OnLiteFactory.init("/sdcard/Music/");
        userLite = OnLiteFactory.create(UserLite.class);
    }

    @Override
    public void onClick(View view) {
//        UserLite lite = OnLiteFactory.create(UserLite.class);
//        List<User> userLimitAndPage;
//        User where = new User();
//        where.setUsername("admin10");
//        where.setPswd("123");
//        lite.insert(where);
//
//        for (int i = 1; i < 7; i++) {
//            userLimitAndPage = lite.select(where, 10, i, "id", false);
//            Log.v("TAG", "----------------------page-->" + i + "-----------------------------------");
//            for (User user : userLimitAndPage) {
//                Log.v("TAG", user.toString());
//            }
//        }
        Log.v("TAG", "---------------------------------------------------------");
        MusicLite lite = OnLiteFactory.create(MusicLite.class);
//        lite.insert(new Music("admin", "123"));
//        lite.insert(new Music("admin1", "1231"));
//        lite.insert(new Music("admin2", "1232"));
//        lite.insert(new Music("admin3", "1233"));
        List<Music> music = lite.select(new Music("admin2", null));
        for (int i = 0; i < music.size(); i++) {
            Log.v("TAG", music.toString());
        }
    }
}
