package com.hacknife.example;


import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.hacknife.onlite.OnLiteFactory;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OnLiteFactory.init(getApplicationContext(),"db");
    }

    public void onInsertClick(View view) {
        Entity entity = new Entity();
        entity.setName("name" + new Random().nextInt(100));
        entity.setPwsd("pwsd" + new Random().nextInt(100));
        entity.setIsPublish(new Random().nextBoolean());
        OnLiteFactory.create(EntityLite.class).insert(entity);
        Log.v("dzq", "entity:" + entity.toString());
    }

    public void onDeleteClick(View view) {
    }

    public void onUpdateClick(View view) {
    }

    public void onQueryClick(View view) {
        Log.v("dzq", "entity:" + OnLiteFactory.create(EntityLite.class).select().toString());
    }
}