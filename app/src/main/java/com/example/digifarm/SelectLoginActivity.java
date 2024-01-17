package com.example.digifarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class SelectLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_login);

        ImageButton farmer_btn = findViewById(R.id.farmer_btn);
        ImageButton customer_btn=findViewById(R.id.customer_btn);
        farmer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(SelectLoginActivity.this,FarmerActivity.class);
                startActivity(in);
                finish();
            }
        });

        customer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(SelectLoginActivity.this,MainActivity.class);
                startActivity(in);
                finish();
            }
        });



    }
}