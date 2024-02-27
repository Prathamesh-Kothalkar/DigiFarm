package com.example.digifarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.digifarm.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAddress0Activity extends AppCompatActivity {
    EditText nameV,addressV,cityV,postal_codeV,phoneV;
    Button addAddress;
    String finalAddress="";
    FirebaseFirestore firestore;
    FirebaseAuth mAuth;

    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address0);
        nameV=findViewById(R.id.ad_name);
        addressV=findViewById(R.id.ad_address);
        cityV=findViewById(R.id.ad_city);

        postal_codeV=findViewById(R.id.ad_code);
        phoneV=findViewById(R.id.ad_phone);
        addAddress=findViewById(R.id.ad_add_address);

        mAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();

        databaseReference= FirebaseDatabase.getInstance().getReference("/users/"+mAuth.getCurrentUser().getUid());
        cityV.setEnabled(false);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    User user=snapshot.getValue(User.class);
                    cityV.setText(user.getCity());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name,address,city,postal,phone;
                name=nameV.getText().toString();
                address=addressV.getText().toString();
                city=cityV.getText().toString();
                postal=postal_codeV.getText().toString();
                phone=phoneV.getText().toString();

                if(!name.isEmpty()){
                    finalAddress+=name+" ";
                }
                if(!city.isEmpty()){
                    finalAddress+=city+" ";
                }
                if(!address.isEmpty()){
                    finalAddress+=address+" ";
                }
                if(!postal.isEmpty()){
                    finalAddress+=postal+" ";
                }
                if(!phone.isEmpty()){
                    finalAddress+=phone+" ";
                }

                if(name.isEmpty() && city.isEmpty() && address.isEmpty() && postal.isEmpty() && phone.isEmpty()){
                    Toast.makeText(AddAddress0Activity.this,"All fields are required",Toast.LENGTH_SHORT).show();
                }
                else{
                    Map<String,String> map=new HashMap<>();
                    map.put("userAddress",finalAddress);

                    firestore.collection("currentUser").document(mAuth.getCurrentUser().getUid())
                            .collection("Address").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(AddAddress0Activity.this,"Address Added Succesfully",Toast.LENGTH_SHORT).show();
//                                        startActivity(new Intent(AddAddress0Activity.this,DetailedActivity.class));
                                        finish();
                                    }
                                }
                            });
                }
            }
        });

    }
}