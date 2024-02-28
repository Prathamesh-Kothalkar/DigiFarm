package com.example.digifarm.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.digifarm.MainActivity;
import com.example.digifarm.R;
import com.example.digifarm.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;


public class AddFragment extends Fragment {

    String name,city,uid,cat;
    int price,quantity;

    EditText pro_name,pro_price,pro_quantity;
    Spinner spinner;

    Button add_product;

    FirebaseAuth mAuth;

    FirebaseFirestore firestore;
    DatabaseReference databaseReference;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddFragment() {
        // Required empty public constructor
    }


    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
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
        View view =inflater.inflate(R.layout.fragment_add, container, false);

        mAuth=FirebaseAuth.getInstance();

        spinner = view.findViewById(R.id.spinner);
        pro_name=view.findViewById(R.id.ad_name);
        pro_price=view.findViewById(R.id.ad_price);
        pro_quantity=view.findViewById(R.id.ad_quantity);
        add_product=view.findViewById(R.id.product_add);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.product_category, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                uid=mAuth.getCurrentUser().getUid().toString();
                name=pro_name.getText().toString();

                cat = spinner.getSelectedItem().toString();

                firestore= FirebaseFirestore.getInstance();
                databaseReference= FirebaseDatabase.getInstance().getReference("/users/"+mAuth.getCurrentUser().getUid());

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            User user = snapshot.getValue(User.class);
                            city= user.getCity().toString();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Toast.makeText(getContext(),cat,Toast.LENGTH_SHORT).show();

            }
        });


        return view;
    }
}