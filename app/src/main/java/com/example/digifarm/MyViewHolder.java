package com.example.digifarm;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView nameView,noView,emailView;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.fimageView);
        nameView=itemView.findViewById(R.id.shop_name);
        noView=itemView.findViewById(R.id.shop_no);
        emailView=itemView.findViewById(R.id.shop_email);

    }
}
