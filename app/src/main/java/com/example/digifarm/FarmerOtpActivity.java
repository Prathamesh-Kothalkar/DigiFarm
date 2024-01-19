package com.example.digifarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.*;

import com.chaos.view.PinView;
import com.google.android.material.textfield.TextInputEditText;

public class FarmerOtpActivity extends AppCompatActivity {
    Button login;
    Button next;
    TextView resend;
    ProgressBar pb;
    PinView otp;
    TextInputEditText mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_otp);

         login=findViewById(R.id.farmer_login);
         next=findViewById(R.id.getOtp);
         resend=findViewById(R.id.sendAgain);
        pb= findViewById(R.id.loading);
         otp=findViewById(R.id.pinview);
         mobile= findViewById(R.id.phoneNumber);


        next.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String phone=mobile.getText().toString();
               Pattern regexMobile=Pattern.compile("\\+91[789]{1}[0-9]{9}");
               Matcher match = regexMobile.matcher(phone);
               if(match.matches()){
                   otp.setEnabled(true);
                   pb.setVisibility(View.VISIBLE);
                   login.setEnabled(true);
                   showToast("Otp Genrated");
               }
               else{
                   showToast("Invalid Number");
               }
           }
       });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.GONE);
                Intent in = new Intent(FarmerOtpActivity.this,FarmerLoginActivity.class);
                startActivity(in);
                finish();
            }
        });
    }

    void showToast(String msg){
        Toast.makeText(FarmerOtpActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}