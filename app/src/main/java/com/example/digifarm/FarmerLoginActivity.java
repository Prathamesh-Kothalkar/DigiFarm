package com.example.digifarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import java.util.regex.*;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import org.w3c.dom.Text;

public class FarmerLoginActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference usersRef = database.getReference("users"); // "users" is the name of the node in the database
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_login);
        Button nextButton = findViewById(R.id.NextButton);
        TextInputEditText tname = findViewById(R.id.textFieldName);
        // TextInputEditText tcity=findViewById(R.id.textFieldCity);

        String cities[]={"Jalgaon","Pune","Shambhaji Nagar","Jalna"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.cities, cities);

        // get reference to the autocomplete text view
        AutoCompleteTextView autocompleteTV = findViewById(R.id.city);

        // set adapter to the autocomplete tv to the arrayAdapter
        autocompleteTV.setAdapter(arrayAdapter);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = tname.getText().toString();
                String city = autocompleteTV.getText().toString();

                // Check if name and city are not empty before storing
                if (!name.isEmpty() && !city.isEmpty()) {
                    // Create a unique key for each user
                    String userId = usersRef.push().getKey();

                    // Create a User object
                    User user = new User(name, city);

                    // Store user data in the "users" node in Firebase
                    usersRef.child(userId).setValue(user);

                    Toast.makeText(FarmerLoginActivity.this, "User data stored in Firebase", Toast.LENGTH_SHORT).show();

                    // Move to the next activity
                    Intent in = new Intent(FarmerLoginActivity.this, FarmerActivity.class);
                    startActivity(in);
                    finish();
                } else {
                    Toast.makeText(FarmerLoginActivity.this, "Name and city cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}