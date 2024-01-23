package com.example.digifarm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FarmerHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FarmerHomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseUser currentUser;

    TextView namex;

    public FarmerHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FarmerHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FarmerHomeFragment newInstance(String param1, String param2) {
        FarmerHomeFragment fragment = new FarmerHomeFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_farmer_home, container, false);
        namex=view.findViewById(R.id.f_name);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();

        if (currentUser != null) {
            String uid = currentUser.getUid();
            //String uid = currentUser.getUid();
            Log.d("UID", "Current UID: " + uid);

            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users/"+uid);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            User user=snapshot.getValue(User.class);
                            namex.setText(user.getName());
                        }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle errors

                    Toast.makeText(getActivity(), "Failed to read user data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // No user is signed in
            Toast.makeText(getActivity(), "No user is signed in", Toast.LENGTH_SHORT).show();
        }


        return view;
    }
}