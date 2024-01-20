package com.example.digifarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {


    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mAuth=FirebaseAuth.getInstance();
        Thread thread = new Thread(){
            public void run(){
                try{

                    sleep(3000);

                }
                catch (Exception e){
                    System.out.print(e);
                }
                finally {
                    if (mAuth.getCurrentUser()!=null) {
                        Intent in = new Intent(SplashActivity.this, FarmerActivity.class);
                        startActivity(in);

                    }
                    else{
                        Intent in = new Intent(SplashActivity.this, SelectLoginActivity.class);
                        startActivity(in);

                    }
                    finish();
                }
            }
        };
        thread.start();
    }
}