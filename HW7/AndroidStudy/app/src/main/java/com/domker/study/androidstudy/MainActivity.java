package com.domker.study.androidstudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initButton();
    }

    private void initButton() {
        open(R.id.permission, PermissionActivity.class);
        open(R.id.image, GlideImageActivity.class);
        open(R.id.videoView, VideoActivity.class);

    }

    private void open(int buttonId, final Class<?> clz) {
        findViewById(buttonId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, clz));
            }
        });
    }



}
