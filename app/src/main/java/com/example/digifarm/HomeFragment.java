package com.example.digifarm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digifarm.model.CategoryModel;
import com.example.digifarm.model.NewProductModel;
import com.example.digifarm.model.PopularProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.N;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView city;
    ProgressBar pb,pbc,pbn,pbp;

    FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    private ViewPager bannerViewPager;
    private List<Integer> bannerImages;
    private int currentPage = 0;
    private Timer timer;
    private final long DELAY_MS = 500; // delay in milliseconds before task is to be executed
    private final long PERIOD_MS = 2000; // time in milliseconds between successive task executions.

    //category recycleview
    RecyclerView catRecyclerView,newProductRecylcerView,popProductRecylcerView;
    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModelList;

    //newProduct
    NewProductsAdapter newProductsAdapter;
    List<NewProductModel> newProductModelList;

    //popular product;

    PopularProductAdapter popularProductAdapter;
    List<PopularProductModel> popProductModelList;

    FirebaseFirestore db;


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        catRecyclerView= view.findViewById(R.id.rec_category);
        newProductRecylcerView=view.findViewById(R.id.new_product_rec);
        popProductRecylcerView=view.findViewById(R.id.popular_rec);
        db=FirebaseFirestore.getInstance();
        bannerViewPager = view.findViewById(R.id.bannerViewPager);
        city=view.findViewById(R.id.city);
        pb=view.findViewById(R.id.wait);
        pbc=view.findViewById(R.id.cat_load);
        pbn=view.findViewById(R.id.new_load);
        pbp=view.findViewById(R.id.popular_load);
        // Initialize banner images
        bannerImages = new ArrayList<>();
        bannerImages.add(R.drawable.banner1);
        bannerImages.add(R.drawable.banner2);
        bannerImages.add(R.drawable.banner3);

        // Set up ViewPager with adapter
        BannerPagerAdapter bannerPagerAdapter = new BannerPagerAdapter(getChildFragmentManager(), bannerImages);
        bannerViewPager.setAdapter(bannerPagerAdapter);

        // Auto-scroll banner images
        final Handler handler = new Handler(Looper.getMainLooper());
        final Runnable update = new Runnable() {
            public void run() {
                if (currentPage == bannerImages.size()) {
                    currentPage = 0;
                }
                bannerViewPager.setCurrentItem(currentPage++,true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(update);
            }
        }, DELAY_MS, PERIOD_MS);

        //data
        mAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("users/"+mAuth.getCurrentUser().getUid());

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    User user = snapshot.getValue(User.class);
                    setProgess(Boolean.FALSE);
                    city.setText(user.getCity());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //category

        catRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        categoryModelList=new ArrayList<>();
        categoryAdapter =new CategoryAdapter(getActivity(),categoryModelList);
        catRecyclerView.setAdapter(categoryAdapter);

        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            pbc.setVisibility(View.GONE);
                            for(QueryDocumentSnapshot document : task.getResult()){
                                CategoryModel categoryModel=document.toObject(CategoryModel.class);
                                categoryModelList.add(categoryModel);
                                categoryAdapter.notifyDataSetChanged();

                            }
                        }
                    }
                });

        //newProduct;
        newProductRecylcerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        newProductModelList=new ArrayList<>();
        newProductsAdapter=new NewProductsAdapter(getContext(),newProductModelList);
        newProductRecylcerView.setAdapter(newProductsAdapter);

        db.collection("NewProduct")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                       if(task.isSuccessful()){
                           pbn.setVisibility(View.GONE);
                           for(QueryDocumentSnapshot document:task.getResult()){
                               NewProductModel newProductModel=document.toObject(NewProductModel.class);
                               newProductModelList.add(newProductModel);
                               newProductsAdapter.notifyDataSetChanged();
                           }
                       }
                    }
                });

        //popular product
        popProductRecylcerView.setLayoutManager(new LinearLayoutManager((getActivity()),RecyclerView.HORIZONTAL,false));
        popProductModelList=new ArrayList<>();
        popularProductAdapter= new PopularProductAdapter(getContext(),popProductModelList);
        popProductRecylcerView.setAdapter(popularProductAdapter);
        db.collection("Polpular")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            pbp.setVisibility(View.GONE);
                            for(QueryDocumentSnapshot document:task.getResult()){
                                PopularProductModel popProductModel=document.toObject(PopularProductModel.class);
                                popProductModelList.add(popProductModel);
                                popularProductAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });


        return view;
    }


    public void onDestroyView() {
        super.onDestroyView();
        if (timer != null) {
            timer.cancel();
        }
    }

    void setProgess(Boolean isVisible){
        if(isVisible){
            pb.setVisibility(View.VISIBLE);
        }
        else{
            pb.setVisibility(View.GONE);
        }
    }
}