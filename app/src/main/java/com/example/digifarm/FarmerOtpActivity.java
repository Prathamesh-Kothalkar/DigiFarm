package com.example.digifarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FarmerOtpActivity extends AppCompatActivity {
    Button login;
    Button next;
    TextView resend;
    ProgressBar pb;
    PinView otp;
    TextInputEditText mobile;
    CountDownTimer resendCountDownTimer;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    long timeOutSecond = 60L;
    String verificationCode;
    PhoneAuthProvider.ForceResendingToken resendingToken;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference usersRef = database.getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_otp);
        login = findViewById(R.id.farmer_login);
        next = findViewById(R.id.getOtp);
        resend = findViewById(R.id.sendAgain);
        pb = findViewById(R.id.loading);
        otp = findViewById(R.id.pinview);
        mobile = findViewById(R.id.phoneNumber);

        resend.setEnabled(false);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = "+91"+mobile.getText().toString();
                Pattern regexMobile = Pattern.compile("\\+91[789]{1}[0-9]{9}");
                Matcher match = regexMobile.matcher(phone);
                if (match.matches()) {
                    setInProgress(true);
                    showToast("Wait Verify");
                    sendOtp(phone, false);
                } else {
                    showToast("Invalid Number");
                    setInProgress(false);
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.GONE);
                String in_otp = otp.getText().toString();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, in_otp);
                signIn(credential,"+91"+mobile.getText().toString());
                setInProgress(true);
            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInProgress(true);
                String phone = "+91"+mobile.getText().toString();
                Pattern regexMobile = Pattern.compile("\\+91[789]{1}[0-9]{9}");
                Matcher match = regexMobile.matcher(phone);
                if (match.matches()) {
                    otp.setEnabled(true);
                    setInProgress(true);
                    login.setEnabled(true);
                    showToast("Wait for Verification");
                    sendOtp(phone, true);
                    resend.setEnabled(false);

                    // Start the countdown timer
                    startResendCountdown();
                } else {
                    showToast("Invalid Number");
                    setInProgress(false);
                }
            }
        });
    }

    void showToast(String msg) {
        Toast.makeText(FarmerOtpActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    void sendOtp(String mobile, boolean isResend) {
        PhoneAuthOptions.Builder builder = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(mobile)
                .setTimeout(timeOutSecond, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        signIn(phoneAuthCredential,mobile);
                        setInProgress(true);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        showToast("Verification Failed");
                        setInProgress(false);

                        if (resendCountDownTimer != null) {
                            resendCountDownTimer.cancel();
                        }
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        verificationCode = s;
                        resendingToken = forceResendingToken;
                        showToast("Otp Send Suc");
                        otp.setEnabled(true);
                        login.setEnabled(true);
                        setInProgress(false);
                        resend.setEnabled(true);

                        if (resendCountDownTimer != null) {
                            resendCountDownTimer.cancel();
                        }
                    }
                });
        if (isResend) {
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(resendingToken).build());
        } else {
            PhoneAuthProvider.verifyPhoneNumber(builder.build());
        }
    }

    void setInProgress(Boolean inProgress) {
        if (inProgress) {
            pb.setVisibility(View.VISIBLE);
        } else {
            pb.setVisibility(View.INVISIBLE);
        }
    }

    void signIn(PhoneAuthCredential phoneAuthCredential, String mobile) {
        //login part
        setInProgress(true);
        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    checkUser(mobile);
                } else {
                    showToast("Otp verification Failed");
                    setInProgress(false);
                }
            }
        });

    }

    void startResendCountdown() {
        resendCountDownTimer = new CountDownTimer(timeOutSecond * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                // You can update UI to show the countdown (optional)
                long secondsRemaining = millisUntilFinished / 1000;
                resend.setText("Resend in " + secondsRemaining + "s");
            }

            public void onFinish() {
                // Enable the resend button when the timer finishes
                resend.setEnabled(true);
                resend.setText("Resend");
            }
        }.start();
    }

    void checkUser(String phoneNumber) {
        usersRef.orderByChild("phoneNumber").equalTo(phoneNumber)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            showToast("Welcome Back");
                            Intent in = new Intent(FarmerOtpActivity.this, FarmerActivity.class);
                            startActivity(in);
                            finish();
                        } else {
                            Intent in = new Intent(FarmerOtpActivity.this, FarmerLoginActivity.class);
                            startActivity(in);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

}