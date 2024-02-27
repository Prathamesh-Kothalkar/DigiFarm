package com.example.digifarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {
    Toolbar toolbar;
    TextView subTotal, discount, shipping, total;
    int totalAmount;
    Button paymentBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        toolbar = findViewById(R.id.payment_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        subTotal = findViewById(R.id.sub_total);
        discount = findViewById(R.id.textView17);
        shipping = findViewById(R.id.textView18);
        total = findViewById(R.id.total_amt);
        paymentBtn=findViewById(R.id.pay_btn);

        // Retrieve total amount
        totalAmount = getIntent().getIntExtra("totalAmount", 0);
        // Now you can use totalAmount as needed, for example:
        subTotal.setText("₹ " + totalAmount + "/-");
        total.setText("₹ " + totalAmount + "/-");
        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentMethod();
            }
        });
    }

    private void paymentMethod() {
        Checkout checkout = new Checkout();
        final Activity activity = PaymentActivity.this;

        try {
            JSONObject options = new JSONObject();
            options.put("name", "DigiFarm");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            totalAmount=totalAmount*100;
            options.put("amount", totalAmount);

            JSONObject preFill = new JSONObject();
            preFill.put("email", "developer.pritamkharate3@gmail.com");
            preFill.put("contact", "8600491186");
            options.put("prefill", preFill);

            checkout.setKeyID("rzp_test_fDiNufpzdERK1T"); // Add your Razorpay Key ID here
            checkout.open(activity, options);

        } catch (Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Cancel",Toast.LENGTH_SHORT).show();
    }
}
