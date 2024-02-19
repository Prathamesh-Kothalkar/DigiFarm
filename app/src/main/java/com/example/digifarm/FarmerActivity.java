package com.example.digifarm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.digifarm.Fragment.AddFragment;
import com.example.digifarm.Fragment.AgrishopFragment;
import com.example.digifarm.Fragment.CaretakerFragment;
import com.example.digifarm.Fragment.FarmerProfileFragment;
import com.example.digifarm.databinding.ActivityFarmerBinding;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FarmerActivity extends AppCompatActivity {

    //FirebaseDatabase firebaseDatabase;
    //DatabaseReference databaseReference;
    //FirebaseAuth  mAuth=FirebaseAuth.getInstance();
    //Button signout;
   // Button getdata;
    //TextView xx;
    ActivityFarmerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_farmer);

      /*  String mb= mAuth.getCurrentUser().getPhoneNumber().toString();
        Toast.makeText(this, mb, Toast.LENGTH_LONG).show();
        signout=findViewById(R.id.signout);
        getdata=findViewById(R.id.getData);
        xx=findViewById(R.id.ss);*/


       /* signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAuth != null) {
                    mAuth.signOut();
                    Toast.makeText(FarmerActivity.this, "Logout Successfull", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(FarmerActivity.this, SelectLoginActivity.class);
                    startActivity(in);
                    finish();
                }
            }
        });*/
        super.onCreate(savedInstanceState);
        binding= ActivityFarmerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new FarmerHomeFragment());
        binding.fbottomNavigationView.setBackground(null);
        binding.fbottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.fhome){
                replaceFragment(new FarmerHomeFragment());
            } else if (id==R.id.fcaretaker) {
                replaceFragment(new CaretakerFragment());
            } else if (id==R.id.addb) {
                replaceFragment(new AddFragment());
            } else if (id==R.id.fagrishop) {
                replaceFragment(new AgrishopFragment());
            }else if (id==R.id.fprofile){
                replaceFragment(new FarmerProfileFragment());
            }
            return true;
        });
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fframe_layout, fragment);
        fragmentTransaction.commit();
    }
}