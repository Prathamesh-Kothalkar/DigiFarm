package com.example.digifarm.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.digifarm.Item;
import com.example.digifarm.MyAdapter;
import com.example.digifarm.R;

import java.util.ArrayList;
import java.util.List;


public class AgrishopFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_agrishop, container, false);
        RecyclerView recyclerView=view.findViewById(R.id.recyclerview);
        List<Item> items=new ArrayList<Item>();
        items.add(new Item("Jay Gajanan Agri","Vedant Deshmukh","9975298954",R.drawable.profile));
        items.add(new Item("Maharastra Krishi Seva Kendra","Saurabh Bagul","8600491186",R.drawable.profile));
        items.add(new Item("Modern Agri Shop","Vipul Patil","8080798958",R.drawable.profile));



        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new MyAdapter(getActivity().getApplicationContext(),items ));



        RecyclerView recyclerView2=view.findViewById(R.id.recyclerview2);
        List<Item> items2=new ArrayList<Item>();
        items2.add(new Item("MH Bhumi Seva Kendra","Dipak Chaudhari","8378065430",R.drawable.profile));
        items2.add(new Item("MH CSC Center","Amit Hirole","9405456435",R.drawable.profile));
        items2.add(new Item("Rites Multi-Seva Center","Ritesh Aade","+919021023183",R.drawable.profile));


        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView2.setAdapter(new MyAdapter(getActivity().getApplicationContext(),items2 ));

        return view;



    }

}