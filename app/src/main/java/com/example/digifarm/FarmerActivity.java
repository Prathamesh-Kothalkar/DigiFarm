package com.example.digifarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FarmerActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth  mAuth=FirebaseAuth.getInstance();
    Button signout;
    Button getdata;
    TextView xx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer);

        String mb= mAuth.getCurrentUser().getPhoneNumber().toString();
        Toast.makeText(this, mb, Toast.LENGTH_LONG).show();
        signout=findViewById(R.id.signout);
        getdata=findViewById(R.id.getData);
        xx=findViewById(R.id.ss);


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