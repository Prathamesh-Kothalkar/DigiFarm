package com.example.digifarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.digifarm.model.NewProductModel;
import com.example.digifarm.model.PopularProductModel;
import com.example.digifarm.model.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {

    TextView productName,productPrice,productCategory,productCount,productTotalPrice;
    ImageView productImg,productInc,productDec,backBtn;
    Button cartBtn;
    int count,totalAmount;

    //newProduct
    NewProductModel newProductModel=null;
    PopularProductModel popularProductModel=null;

    ShowAllModel showAllModel=null;

    public FirebaseFirestore firestore;
    public FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        firestore=FirebaseFirestore.getInstance();
        count=0;
        //totalAmount=1;
        final Object obj=getIntent().getSerializableExtra("detailed");
        if(obj instanceof NewProductModel) {
            newProductModel=(NewProductModel) obj;
        }

        if(obj instanceof PopularProductModel){
            popularProductModel=(PopularProductModel) obj;
        }

        if(obj instanceof  ShowAllModel){
            showAllModel=(ShowAllModel) obj;
        }

            productName = findViewById(R.id.detail_name);
            productCategory = findViewById(R.id.detail_cat);
            productPrice = findViewById(R.id.detail_price);
            productCount = findViewById(R.id.item_count);
            productImg = findViewById(R.id.detail_img);
            productTotalPrice=findViewById(R.id.total_amount);
            productInc = findViewById(R.id.inc);
            productDec = findViewById(R.id.dec);
            backBtn=findViewById(R.id.backbtn);
            cartBtn=findViewById(R.id.cartBtn);

            //New Product
            if(newProductModel!=null){
                totalAmount=newProductModel.getRupees();
                Glide.with(getApplicationContext()).load(newProductModel.getImg_url()).into(productImg);
                productName.setText(newProductModel.getName());
                productPrice.setText("Rs/kg: ₹"+String.valueOf(newProductModel.getRupees())+"/-");
                productCategory.setText(newProductModel.getCategory());
            }

            //popular product
            if(popularProductModel!=null){
                totalAmount=popularProductModel.getRupees();
                Glide.with(getApplicationContext()).load(popularProductModel.getImg_url()).into(productImg);
                productName.setText(popularProductModel.getName());
                productPrice.setText("Rs/kg: ₹"+String.valueOf(popularProductModel.getRupees())+"/-");
                productCategory.setText(popularProductModel.getCategory());
            }

            //showallProduct

            if(showAllModel!=null){
                totalAmount=showAllModel.getPrice();
                Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(productImg);
                productName.setText(showAllModel.getName());
                productPrice.setText("Rs/kg: ₹"+String.valueOf(showAllModel.getPrice())+"/-");
                productCategory.setText(showAllModel.getCategory());
            }

            productInc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count++;
                    productCount.setText(String.valueOf(count));
                    productTotalPrice.setText(String.valueOf("₹ "+totalAmount*count+"/-"));
                }
            });

            productDec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(count!=0) {
                        count--;
                        productCount.setText(String.valueOf(count));
                        productTotalPrice.setText(String.valueOf("₹ "+totalAmount*count+"/-"));
                    }
                }
            });


            cartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(count!=0){
                        addToCart();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Please select item Quantity",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
    }

    void addToCart(){
        mAuth=FirebaseAuth.getInstance();
        String onDate,onTime;
        Calendar calendar =Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MM dd, yyyy");
        onDate=currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        onTime=currentTime.format(calendar.getTime());
        final HashMap<String,Object>cartMap =new HashMap<>();
        cartMap.put("productName",productName.getText().toString());
        cartMap.put("productPrice",String.valueOf(productPrice.getText()));
        cartMap.put("totalPrice",totalAmount*count);
        cartMap.put("currentTime",onTime);
        cartMap.put("currentDate",onDate);

        firestore.collection("cart").document(mAuth.getCurrentUser().getUid())
                .collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(DetailedActivity.this,"Added to cart",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
}