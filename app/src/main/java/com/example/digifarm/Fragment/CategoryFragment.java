package com.example.digifarm.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digifarm.Adapter.ShowAllAdapter;
import com.example.digifarm.R;
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
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    RecyclerView recyclerView;
    ShowAllAdapter showAllAdapter;
    List<ShowAllModel> showAllModelList;
    FirebaseFirestore firestore;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    String city;

    List<String> names = new ArrayList<>();
    ProgressBar pb;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public CategoryFragment() {
        // Required empty public constructor
    }

    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        recyclerView = view.findViewById(R.id.cart_rec);
        pb = view.findViewById(R.id.loading_all);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        showAllModelList = new ArrayList<>();
        showAllAdapter = new ShowAllAdapter(requireContext(), showAllModelList);
        recyclerView.setAdapter(showAllAdapter);

        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("/users/" + mAuth.getCurrentUser().getUid());

        // Retrieve the city value from Firebase Realtime Database
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    User user = snapshot.getValue(User.class);
                    city = user.getCity();
                    // Execute Firestore query after retrieving city
                    executeFirestoreQuery();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event
            }
        });

        SearchView searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String userInput = newText.toLowerCase();
                List<ShowAllModel> filteredList = new ArrayList<>();
                for (ShowAllModel model : showAllModelList) {
                    if (model.getName().toLowerCase().contains(userInput)) {
                        filteredList.add(model);
                    }
                }
                // Update the adapter with the filtered list
                showAllAdapter.filterList(filteredList);
                return true;
            }
        });

        return view;
    }

    private void executeFirestoreQuery() {
        firestore.collection("ShowAll").whereEqualTo("city", city)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            pb.setVisibility(View.GONE);
                            for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                ShowAllModel showAllModel = document.toObject(ShowAllModel.class);
                                showAllModelList.add(showAllModel);
                                names.add(showAllModel.getName());
                            }
                            showAllAdapter.notifyDataSetChanged(); // Notify adapter after adding data
                        } else {
                            Log.e("Firestore", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
