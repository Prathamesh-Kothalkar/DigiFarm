package com.example.digifarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread thread = new Thread(){
            public void run(){
                try{
                    sleep(3000);

                }
                catch (Exception e){
                    System.out.print(e);
                }
                finally {
                    Intent in = new Intent(SplashActivity.this,SelectLoginActivity.class);
                    startActivity(in);
                    finish();
                }
            }
        };
        thread.start();
    }
}