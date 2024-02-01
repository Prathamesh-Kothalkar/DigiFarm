package com.example.digifarm;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        items.add(new Item("John wick","john.wick@email.com","9999999999",R.drawable.profile));
        items.add(new Item("John wick","john.wick@email.com","9999999999",R.drawable.profile));
        items.add(new Item("John wick","john.wick@email.com","9999999999",R.drawable.profile));
        items.add(new Item("John wick","john.wick@email.com","9999999999",R.drawable.profile));
        items.add(new Item("John wick","john.wick@email.com","9999999999",R.drawable.profile));
        items.add(new Item("John wick","john.wick@email.com","9999999999",R.drawable.profile));
        items.add(new Item("John wick","john.wick@email.com","9999999999",R.drawable.profile));
        items.add(new Item("John wick","john.wick@email.com","9999999999",R.drawable.profile));
        items.add(new Item("John wick","john.wick@email.com","9999999999",R.drawable.profile));
        items.add(new Item("John wick","john.wick@email.com","9999999999",R.drawable.profile));


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new MyAdapter(getActivity().getApplicationContext(),items ));

        return view;
    }
}