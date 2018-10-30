package com.blackchopper.demo_onlite;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;


import com.hacknife.briefness.BindLayout;
import com.hacknife.briefness.Briefness;
import com.hacknife.onlite.OnLiteFactory;
import com.hacknife.onpermission.OnPermission;
import com.hacknife.onpermission.Permission;


@BindLayout(R.layout.activity_main)
public class MainActivity extends Activity {
    UserLite userLite;
    MainActivityBriefnessor briefnessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        briefnessor = (MainActivityBriefnessor) Briefness.bind(this);
        new OnPermission(this).grant(new Permission() {
            @Override
            public String[] permissions() {
                return new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                };
            }

            @Override
            public void onGranted(String[] strings) {
                OnLiteFactory.init("/sdcard/db/");
                userLite = OnLiteFactory.create(UserLite.class);
            }

            @Override
            public void onDenied(String[] strings) {
                finish();
            }
        });
    }

    public void OnInsertClick() {
        //插入单条数据
        User user = new User();
        user.setUsername("hacknife");
        user.setPswd("hacknife1234");
        userLite.insert(user);

    }

    public void OnDeleteClick() {
        User user = new User();
        user.setUsername("hacknife");
        userLite.delete(user);//删除用户名为hacknife的数据
    }

    public void OnUpdateClick() {
        User where = new User();
        where.setUsername("hacknife");

        User user = new User();
        user.setUsername("java");
        user.setPswd("123");
        userLite.updata(user,where);//用户名为hacknife改为 用户名为Java  密码为123
    }

    public void OnQueryClick() {
        User where = new User();
        where.setUsername("hacknife");
        userLite.select(where);//查询用户名为hacknife的所有数据
    }
}
