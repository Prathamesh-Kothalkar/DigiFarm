package com.example.digifarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.digifarm.Adapter.ShowAllAdapter;
import com.example.digifarm.model.ShowAllModel;
import com.example.digifarm.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowAllActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ShowAllAdapter showAllAdapter;
    List<ShowAllModel> showAllModelList;
    String city;
    FirebaseFirestore firestore;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);

        String category = getIntent().getStringExtra("category");
        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("/users/" + mAuth.getCurrentUser().getUid());

        recyclerView = findViewById(R.id.show_all_rec);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        showAllModelList = new ArrayList<>();
        showAllAdapter = new ShowAllAdapter(this, showAllModelList);
        recyclerView.setAdapter(showAllAdapter);

        // Retrieve the city value from Firebase Realtime Database
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    User user = snapshot.getValue(User.class);
                    city = user.getCity();
                    Toast.makeText(getApplicationContext(), city, Toast.LENGTH_SHORT).show();
                    fetchProducts(category);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event
            }
        });
    }

    private void fetchProducts(String category) {
        Query query;
        if (category == null || category.isEmpty()) {
            query = firestore.collection("ShowAll").whereEqualTo("city", city);
        } else {
            query = firestore.collection("ShowAll")
                    .whereEqualTo("category", getCategoryName(category))
                    .whereEqualTo("city", city);
        }

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult().getDocuments()) {
                        ShowAllModel showAllModel = document.toObject(ShowAllModel.class);
                        showAllModelList.add(showAllModel);
                    }
                    showAllAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ShowAllActivity.this, "Failed to fetch products", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String getCategoryName(String category) {
        switch (category.toLowerCase()) {
            case "vegetables":
                return "Vegetable";
            case "exotics":
                return "Exotic";
            case "fruits":
                return "Fruit";
            case "flowers":
                return "Flower";
            case "milks":
                return "Milk";
            case "staples":
                return "Staple";
            default:
                return "";
        }
    }
}
