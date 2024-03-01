package com.example.digifarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.digifarm.Adapter.MyProductAdapter;
import com.example.digifarm.Adapter.ShowAllAdapter;
import com.example.digifarm.model.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyProductsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyProductAdapter myProductAdapter;
    List<ShowAllModel> showAllModelList;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_products);

        recyclerView = findViewById(R.id.rec_myProduct);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        showAllModelList = new ArrayList<>();
        myProductAdapter = new MyProductAdapter(this, showAllModelList);
        recyclerView.setAdapter(myProductAdapter);

        // Retrieve products belonging to the current user
        fetchUserProducts();
    }

    private void fetchUserProducts() {
        String currentUserId = mAuth.getCurrentUser().getUid();

        Query query = firestore.collection("ShowAll").whereEqualTo("uid", currentUserId);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult().getDocuments()) {
                        ShowAllModel showAllModel = document.toObject(ShowAllModel.class);
                        showAllModelList.add(showAllModel);
                    }
                    myProductAdapter.notifyDataSetChanged();
                } else {

                }
            }
        });
    }
}
