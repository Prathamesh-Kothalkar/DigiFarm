package com.example.digifarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SelectLoginActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser()!=null && mAuth.getCurrentUser().getUid()!=null){
            databaseReference= FirebaseDatabase.getInstance().getReference("users/"+mAuth.getCurrentUser().getUid());
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        User user = snapshot.getValue(User.class);

                        if("Farmer".equals(user.getType())){
                            Intent in = new Intent(SelectLoginActivity.this,FarmerActivity.class);
                            startActivity(in);
                        }
                        else{
                            Intent in = new Intent(SelectLoginActivity.this,MainActivity.class);
                            startActivity(in);
                        }
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            setContentView(R.layout.activity_select_login);
            ImageButton farmer_btn = findViewById(R.id.farmer_btn);
            ImageButton customer_btn = findViewById(R.id.customer_btn);
            farmer_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(SelectLoginActivity.this, FarmerOtpActivity.class);
                    startActivity(in);
                    finish();
                }
            });

            customer_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(SelectLoginActivity.this, CustomerOtpActivity.class);
                    startActivity(in);
                    finish();
                }
            });


        }
    }
}