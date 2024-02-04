package com.example.digifarm;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView nameView,noView,shopNameView;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.fimageView);
        shopNameView=itemView.findViewById(R.id.shop_name);
        noView=itemView.findViewById(R.id.shop_no);
        nameView=itemView.findViewById(R.id.shop_owner);

    }
}
