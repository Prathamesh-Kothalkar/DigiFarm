package com.example.digifarm.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.digifarm.R;

public class BannerFragment extends Fragment {

    private int imageResId;

    public BannerFragment() {
        // Required empty public constructor
    }

    public static BannerFragment newInstance(int imageResId) {
        BannerFragment fragment = new BannerFragment();
        Bundle args = new Bundle();
        args.putInt("imageResId", imageResId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageResId = getArguments().getInt("imageResId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_banner, container, false);
        ImageView imageView = view.findViewById(R.id.bannerImageView);
        imageView.setImageResource(imageResId);
        return view;
    }
}

