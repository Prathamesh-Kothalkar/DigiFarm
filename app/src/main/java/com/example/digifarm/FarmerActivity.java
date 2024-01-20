package com.example.digifarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class FarmerActivity extends AppCompatActivity {

    FirebaseAuth  mAuth=FirebaseAuth.getInstance();
    Button signout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer);

        String mb= mAuth.getCurrentUser().getPhoneNumber().toString();
        Toast.makeText(this, mb, Toast.LENGTH_LONG).show();
        signout=findViewById(R.id.signout);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAuth != null) {
                    mAuth.signOut();
                    Toast.makeText(FarmerActivity.this, "Logout Successfull", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(FarmerActivity.this, SelectLoginActivity.class);
                    startActivity(in);
                    finish();
                }
            }
        });
    }
}