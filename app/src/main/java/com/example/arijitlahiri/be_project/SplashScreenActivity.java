package com.example.arijitlahiri.be_project;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.content.Intent;
import android.os.Handler;
import android.content.SharedPreferences;


public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences myPrefs = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                String did = myPrefs.getString("did",null);
                Intent i=new Intent(SplashScreenActivity.this,
                        MainActivity.class);
                Intent j=new Intent(SplashScreenActivity.this,
                        SignUpActivity.class);
                if(did != null){
                    startActivity(i);
                }
                else{
                    startActivity(j);
                }
                finish();
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
}
