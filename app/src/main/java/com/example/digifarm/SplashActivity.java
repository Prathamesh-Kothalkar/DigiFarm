package com.example.digifarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.TotpSecret;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity {


    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mAuth = FirebaseAuth.getInstance();
        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
//                    if (mAuth.getCurrentUser() != null && mAuth.getCurrentUser().getUid() != null) {
//
//                        databaseReference=FirebaseDatabase.getInstance().getReference("users/"+mAuth.getCurrentUser().getUid().toString());
//                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                if(snapshot.exists()){
//                                    User user = snapshot.getValue(User.class);
//                                    if("Farmer".equals(user.getType())){
//                                        Intent in = new Intent(SplashActivity.this, FarmerActivity.class);
//                                        startActivity(in);
//                                    }
//                                    else{
//                                        Intent in = new Intent(SplashActivity.this, MainActivity.class);
//                                        startActivity(in);
//                                    }
//                                    finish();
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//                                //error
//                            }
//                        });
//
//                    } else {
                    Intent in = new Intent(SplashActivity.this, SelectLoginActivity.class);
                    startActivity(in);
                    finish();
                }
            }
        };
        thread.start();
    }

}