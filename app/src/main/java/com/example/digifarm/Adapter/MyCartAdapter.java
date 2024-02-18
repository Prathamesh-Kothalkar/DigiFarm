package com.example.digifarm.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digifarm.R;
import com.example.digifarm.model.MyCartModel;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {

    Context context;
    List<MyCartModel> list;
    int totalAmount=0;

    public MyCartAdapter(Context context,List<MyCartModel>list){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartAdapter.ViewHolder holder, int position) {

        holder.date.setText(list.get(position).getCurrentDate());
        holder.name.setText(list.get(position).getProductName());
        holder.time.setText(list.get(position).getCurrentDate());
        holder.price.setText(list.get(position).getProductPrice());
        holder.totalPrice.setText(String.valueOf(list.get(position).getTotalPrice()));
        totalAmount+=list.get(position).getTotalPrice();
        Intent intent = new Intent("TotalAmount");
        intent.putExtra("totalAmount",totalAmount);

        int price =getPrice(list.get(position).getProductPrice());
        int quantiy=list.get(position).getTotalPrice()/price;
        holder.totalQuantity.setText(String.valueOf(quantiy));
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,price,date,time,totalPrice,totalQuantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.product_name);
            price=itemView.findViewById(R.id.product_price);
            date=itemView.findViewById(R.id.current_date);
            time=itemView.findViewById(R.id.current_time);
            totalPrice=itemView.findViewById(R.id.total_price);
            totalQuantity=itemView.findViewById(R.id.total_quantity);
        }
    }

    public int getPrice(String price){
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(price);
        StringBuilder numericPart = new StringBuilder();

        while (matcher.find()) {
            numericPart.append(matcher.group());
        }

        return Integer.parseInt(numericPart.toString());
    }
}
