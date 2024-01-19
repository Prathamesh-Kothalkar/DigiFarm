package com.example.digifarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;
import java.util.regex.*;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

public class FarmerOtpActivity extends AppCompatActivity {
    Button login;
    Button next;
    TextView resend;
    ProgressBar pb;
    PinView otp;
    TextInputEditText mobile;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    long timeOutSecond=60L;
    String verificationCode;
    PhoneAuthProvider.ForceResendingToken resendingToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_otp);
         login=findViewById(R.id.farmer_login);
         next=findViewById(R.id.getOtp);
         resend=findViewById(R.id.sendAgain);
        pb= findViewById(R.id.loading);
         otp=findViewById(R.id.pinview);
         mobile= findViewById(R.id.phoneNumber);


        next.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String phone=mobile.getText().toString();
               Pattern regexMobile=Pattern.compile("\\+91[789]{1}[0-9]{9}");
               Matcher match = regexMobile.matcher(phone);
               if(match.matches()){
                   otp.setEnabled(true);
                    setInProgress(true);
                   login.setEnabled(true);
                   showToast("Wait Verify");
                   sendOtp(phone,false);
               }
               else{
                   showToast("Invalid Number");
                   setInProgress(false);
               }
           }
       });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.GONE);
                String in_otp=otp.getText().toString();
                PhoneAuthCredential credential =PhoneAuthProvider.getCredential(verificationCode,in_otp);
                signIn(credential);
                setInProgress(true);
            }
        });
    }

    void showToast(String msg){
        Toast.makeText(FarmerOtpActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    void sendOtp(String mobile,boolean isResend){
        PhoneAuthOptions.Builder builder =PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(mobile)
                .setTimeout(timeOutSecond, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        signIn(phoneAuthCredential);
                        setInProgress(true);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        showToast("Verification Failed");
                        setInProgress(false);
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        verificationCode =s;
                        resendingToken=forceResendingToken;
                        showToast("Otp Sended");
                        setInProgress(false);
                    }
                });
        if(isResend){
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(resendingToken).build());
        }
        else{
            PhoneAuthProvider.verifyPhoneNumber(builder.build());
        }
    }

    void setInProgress(Boolean inProgress){
        if(inProgress){
            pb.setVisibility(View.VISIBLE);
        }else{
            pb.setVisibility(View.INVISIBLE);
        }
    }

    void signIn(PhoneAuthCredential phoneAuthCredential){
        //login part
        setInProgress(true);
        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
                   Intent in = new Intent(FarmerOtpActivity.this,FarmerLoginActivity.class);
                   startActivity(in);
                   finish();
               }
               else{
                   showToast("Otp verification Failed");
                   setInProgress(false);
               }
            }
        });

    }
}