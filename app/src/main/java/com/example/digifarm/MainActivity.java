
package com.example.digifarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.digifarm.Fragment.CartFragment;
import com.example.digifarm.Fragment.CategoryFragment;
import com.example.digifarm.Fragment.HomeFragment;
import com.example.digifarm.Fragment.ProfileFragment;
import com.example.digifarm.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
         replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int id=item.getItemId();
            if(id==R.id.home){
                replaceFragment(new HomeFragment());
            }
            if(id==R.id.cart){
                replaceFragment(new CartFragment());
            }
            if(id==R.id.category){
                replaceFragment(new CategoryFragment());
            }
            if(id==R.id.profile){
                replaceFragment(new ProfileFragment());
            }
            return true;
        });
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }

}