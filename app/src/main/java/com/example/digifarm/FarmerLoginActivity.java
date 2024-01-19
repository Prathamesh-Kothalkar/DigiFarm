package com.example.digifarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.regex.*;

import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

public class FarmerLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_login);
        Button nextButton = findViewById(R.id.NextButton);
        TextInputEditText phone=findViewById(R.id.textFieldPhone);
        TextInputEditText tname = findViewById(R.id.textFieldName);
        TextInputEditText tcity=findViewById(R.id.textFieldCity);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String mobile = phone.getText().toString();
              String name = tname.getText().toString();
              String city= tcity.getText().toString();
                if (mobile.isEmpty() || name.isEmpty() || city.isEmpty()) {
                    Toast.makeText(FarmerLoginActivity.this, "All Fields are Required", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(FarmerLoginActivity.this, "Yes", Toast.LENGTH_SHORT).show();
                    Pattern regexPhone=Pattern.compile("[987]{1}[0-9]{9}");
                    Matcher phoneMatch=regexPhone.matcher(mobile);
                    if(phoneMatch.matches()){
                        //Toast.makeText(FarmerLoginActivity.this, "Yes", Toast.LENGTH_SHORT).show();
                        Toast.makeText(FarmerLoginActivity.this,"Otp sended",Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(FarmerLoginActivity.this,FarmerOtpActivity.class);
                        startActivity(in);
                    }
                    else{
                        Toast.makeText(FarmerLoginActivity.this,"Incorrect Mobile Number",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}