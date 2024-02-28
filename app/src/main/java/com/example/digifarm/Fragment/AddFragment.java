package com.example.digifarm.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.digifarm.MainActivity;
import com.example.digifarm.R;
import com.example.digifarm.model.ShowAllModel;
import com.example.digifarm.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.ByteArrayOutputStream;


public class AddFragment extends Fragment {

    String name,city,uid,cat;
    int price,quantity;
    private static final int PICK_IMAGE_REQUEST = 1;

    EditText pro_name,pro_price,pro_quantity;
    ImageView productImage;
    Spinner spinner;

    Button add_product;

    FirebaseAuth mAuth;

    ProgressBar progressBar;

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
        productImage = view.findViewById(R.id.product_image);
        spinner = view.findViewById(R.id.spinner);
        pro_name=view.findViewById(R.id.ad_name);
        progressBar=view.findViewById(R.id.progess_bar);
        pro_price=view.findViewById(R.id.ad_price);
        pro_quantity=view.findViewById(R.id.ad_quantity);
        add_product=view.findViewById(R.id.product_add);
        firestore = FirebaseFirestore.getInstance();
//        mAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("/users/"+mAuth.getCurrentUser().getUid());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.product_category, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhotoIntent, PICK_IMAGE_REQUEST);
            }
        });



        add_product.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                add_product.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                uid = mAuth.getCurrentUser().getUid();
                name = pro_name.getText().toString();
                price = Integer.parseInt(pro_price.getText().toString());
                quantity = Integer.parseInt(pro_quantity.getText().toString());
                cat = spinner.getSelectedItem().toString();

                //Get a city
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            User user=snapshot.getValue(User.class);
                            city=user.getCity();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                // Get a reference to Firebase Storage
                StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("product_images");

                // Generate a unique filename for the image
                String imageName = System.currentTimeMillis() + ".jpg";
                StorageReference imageRef = storageRef.child(imageName);

                // Get the image URI
                productImage.setDrawingCacheEnabled(true);
                productImage.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) productImage.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageData = baos.toByteArray();

                // Upload the image to Firebase Storage
                UploadTask uploadTask = imageRef.putBytes(imageData);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get the download URL of the uploaded image
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String imageUrl = uri.toString();

                                // Create a ShowAllModel object with the entered data and image URL
                                ShowAllModel product = new ShowAllModel(imageUrl, name, cat, city, uid, price,quantity);

                                // Add the product to the Realtime Database
                                firestore.collection("ShowAll").add(product).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                        Toast.makeText(getContext(),"Added",Toast.LENGTH_SHORT).show();
                                        pro_name.setText(null);
                                        pro_price.setText(null);
                                        pro_quantity.setText(null);
                                        spinner.setSelection(0);
                                        productImage.setImageResource(R.drawable.select_img);
                                        progressBar.setVisibility(View.GONE);
                                        add_product.setVisibility(View.VISIBLE);
                                    }
                                });
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            productImage.setImageURI(selectedImageUri);
        }
    }

}