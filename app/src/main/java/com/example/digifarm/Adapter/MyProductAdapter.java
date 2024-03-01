package com.example.digifarm.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.digifarm.DetailedActivity;
import com.example.digifarm.R;
import com.example.digifarm.model.ShowAllModel;

import java.util.ArrayList;
import java.util.List;

public class MyProductAdapter extends RecyclerView.Adapter<MyProductAdapter.ViewHolder> {
    private Context context;
    private List<ShowAllModel> list;
    private List<ShowAllModel> filteredList;

    public MyProductAdapter(Context context, List<ShowAllModel> list) {
        this.context = context;
        this.list = list;
        this.filteredList = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public MyProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_product, parent, false));
    }

    public void filterList(List<ShowAllModel> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.price.setText(String.valueOf("₹" + list.get(position).getPrice() + "/kg"));
        holder.name.setText(list.get(position).getName());
        holder.category.setText(list.get(position).getCategory());
        holder.quantity.setText(list.get(position).getQuantity());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra("detailed", list.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView price, name, category,quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.product_price);
            name = itemView.findViewById(R.id.product_name);
            category = itemView.findViewById(R.id.category);
            quantity=itemView.findViewById(R.id.product_quantity);
        }
    }
}
