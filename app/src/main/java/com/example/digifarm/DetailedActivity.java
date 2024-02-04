package com.example.digifarm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.digifarm.model.NewProductModel;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailedActivity extends AppCompatActivity {

    TextView productName,productPrice,productCategory,productCount,productTotalPrice;
    ImageView productImg,productInc,productDec,backBtn;
    int count,totalAmount;

    //newProduct
    NewProductModel newProductModel=null;

    public FirebaseFirestore firestore;

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

            productName = findViewById(R.id.detail_name);
            productCategory = findViewById(R.id.detail_cat);
            productPrice = findViewById(R.id.detail_price);
            productCount = findViewById(R.id.item_count);
            productImg = findViewById(R.id.detail_img);
            productTotalPrice=findViewById(R.id.total_amount);
            productInc = findViewById(R.id.inc);
            productDec = findViewById(R.id.dec);
            backBtn=findViewById(R.id.backbtn);

            //New Product
            if(newProductModel!=null){
                totalAmount=newProductModel.getRupees();
                Glide.with(getApplicationContext()).load(newProductModel.getImg_url()).into(productImg);
                productName.setText(newProductModel.getName());
                productPrice.setText("Rs/kg: "+String.valueOf(newProductModel.getRupees()));
                productCategory.setText(newProductModel.getCategory());
            }

            productInc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count++;
                    productCount.setText(String.valueOf(count));
                    productTotalPrice.setText(String.valueOf(totalAmount*count));
                }
            });

            productDec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(count!=0) {
                        count--;
                        productCount.setText(String.valueOf(count));
                        productTotalPrice.setText(String.valueOf(totalAmount*count));
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
}