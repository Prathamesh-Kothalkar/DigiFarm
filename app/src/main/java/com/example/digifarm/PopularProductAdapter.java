package com.example.digifarm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.digifarm.model.PopularProductModel;

import java.util.List;

public class PopularProductAdapter extends RecyclerView.Adapter<PopularProductAdapter.ViewHolder> {

    private Context context;
    private List<PopularProductModel> list;

    public PopularProductAdapter(Context context, List<PopularProductModel>list) {
        this.context=context;
        this.list=list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_product,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(list.get(position).getImg_url()).into(holder.popImg);
        holder.popName.setText(list.get(position).getName());
        holder.popPrice.setText(String.valueOf(list.get(position).getRupees()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DetailedActivity.class);
                intent.putExtra("detailed",list.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView popImg;
        TextView popName,popPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            popImg=itemView.findViewById(R.id.pop_img);
            popName=itemView.findViewById(R.id.pop_product_name);
            popPrice=itemView.findViewById(R.id.pop_price);
        }
    }

}
