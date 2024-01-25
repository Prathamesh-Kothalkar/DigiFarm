package com.example.digifarm;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;


public class FarmerProfileFragment extends Fragment {

    Button logout;
    Button support;
    FirebaseAuth mAuth;

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
        logout=view.findViewById(R.id.FarmerlogoutButton);
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
                Intent in = new Intent(getContext(),SelectLoginActivity.class);
                in. setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(in);
            }
        });

        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext(),SupportActivity.class);
                startActivity(in);
            }
        });
        return view;
    }
}