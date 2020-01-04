package com.example.eventz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import storage.UserSettings;

public class MainActivity extends AppCompatActivity {

    private TextView tv_hi;
    private ImageView iv_img;
    //private FrontPageController controller;

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String name = UserSettings.getInstance().getName();
        if (!TextUtils.isEmpty(name)) {
            startActivity(new Intent(this, Home.class));
            finish();
        } else {
            // controller=new FrontPageController(this);

            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            }, 6000);

        }
    }
}
