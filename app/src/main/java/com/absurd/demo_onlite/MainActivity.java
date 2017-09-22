package com.absurd.demo_onlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.absurd.onlite.dao.OnLiteFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    UserLite userLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userLite = OnLiteFactory.getInstance().getDataHelper(UserLite.class, User.class);
    }

    @Override
    public void onClick(View view) {

        User user=new User();
        user.setPswd("21312");
        user.setUsername("admin");
        user.setMbyte("sdasqewrqew".getBytes());
        user.setMdouble(123);
        user.setMfloat(1.000f);
        short s=1;
        user.setMshort(s);
        user.setMlong((long) 10000);
        userLite.insert(user);
        Log.v("TAG",userLite.select(null).toString());
        userLite.delete();
    }
}
