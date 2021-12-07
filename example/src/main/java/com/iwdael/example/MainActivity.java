package com.iwdael.example;


import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.iwdael.dblite.DbLiteFactory;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DbLiteFactory.init(getApplicationContext(),"db");
    }

    public void onInsertClick(View view) {
        Entity entity = new Entity();
        entity.setName("name" + new Random().nextInt(100));
        entity.setPwsd("pwsd" + new Random().nextInt(100));
//        entity.setPublish(new Random().nextBoolean());
        DbLiteFactory.create(EntityLite.class).insert(entity);
        Log.v("dzq", "entity:" + entity.toString());
    }

    public void onDeleteClick(View view) {
    }

    public void onUpdateClick(View view) {
    }

    public void onQueryClick(View view) {
        Log.v("dzq", "entity:" + DbLiteFactory.create(EntityLite.class).select().toString());
    }
}