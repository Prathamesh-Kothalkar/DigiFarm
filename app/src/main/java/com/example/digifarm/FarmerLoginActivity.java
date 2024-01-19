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

import org.w3c.dom.Text;

public class FarmerLoginActivity extends AppCompatActivity {

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
                String city= autocompleteTV.getText().toString();
                Toast.makeText(FarmerLoginActivity.this, city, Toast.LENGTH_SHORT).show();
                Intent in = new Intent(FarmerLoginActivity.this,FarmerActivity.class);
                startActivity(in);
                finish();
            }
        });
    }
}