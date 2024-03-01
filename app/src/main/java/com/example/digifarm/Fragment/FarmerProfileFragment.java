package com.example.digifarm.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digifarm.MyContactActivity;
import com.example.digifarm.MyProductsActivity;
import com.example.digifarm.R;
import com.example.digifarm.SelectLoginActivity;
import com.example.digifarm.SupportActivity;
import com.example.digifarm.TermsActivity;
import com.example.digifarm.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class FarmerProfileFragment extends Fragment {

    Button logout;
    Button support;
    Button contact;
    FirebaseAuth mAuth;
    Button tcButton;

    Button myProducts;

    TextView name;
    TextView mobile;

    DatabaseReference databaseReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_farmer_profile, container, false);
        name=view.findViewById(R.id.farmer_name);
        mobile=view.findViewById(R.id.farmer_mobile);
        support=view.findViewById(R.id.supportButton);
        contact=view.findViewById(R.id.myContactButton);
        logout=view.findViewById(R.id.FarmerlogoutButton);
        tcButton=view.findViewById(R.id.tcButton);
        myProducts=view.findViewById(R.id.myProductsButton);
        mAuth=FirebaseAuth.getInstance();

        databaseReference=FirebaseDatabase.getInstance().getReference("users/"+mAuth.getCurrentUser().getUid());

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    User user = snapshot.getValue(User.class);
                    name.setText(user.getName());
                    mobile.setText(user.getPhoneNumber());
                }
                else{
                    Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Toast.makeText(getContext(),"Logout Successfull",Toast.LENGTH_SHORT).show();
                Intent in = new Intent(getContext(), SelectLoginActivity.class);
                in. setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(in);
            }
        });

        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext(), SupportActivity.class);
                startActivity(in);
            }
        });

        tcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext(), TermsActivity.class);
                startActivity(in);
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext(), MyContactActivity.class);
                startActivity(in);
            }
        });

        myProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyProductsActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}