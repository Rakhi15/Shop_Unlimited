package com.oakspro.shopunlimited;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView pic;
    private int SCREEN_TIME=7000;
    private Animation slideAnim;
    SharedPreferences preferences;
    Boolean isLogged=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences=getSharedPreferences("MyLogin", 0);
        isLogged=preferences.getBoolean("isLogged", false);
        //set ids

        pic=findViewById(R.id.pic1);

        slideAnim= AnimationUtils.loadAnimation(this, R.anim.slide_animation);

        pic.setAnimation(slideAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isLogged==true){
                    Intent intent=new Intent(MainActivity.this, ShopActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent=new Intent(MainActivity.this, SigninActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },SCREEN_TIME);
    }
}