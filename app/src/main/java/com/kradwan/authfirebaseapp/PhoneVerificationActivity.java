package com.kradwan.authfirebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneVerificationActivity extends AppCompatActivity {


    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    EditText edPhone, edCode;
    Button btnSendSMS, btnVerifyCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);


        edCode = findViewById(R.id.ed_sms_code);
        edPhone = findViewById(R.id.ed_phone);
        btnSendSMS = findViewById(R.id.btn_send_sms);
        btnVerifyCode = findViewById(R.id.btn_verifiy);


        btnSendSMS.setOnClickListener(view -> {

            PhoneAuthOptions options = PhoneAuthOptions.newBuilder()
                    .setPhoneNumber(edPhone.getText().toString())
                    .setActivity(PhoneVerificationActivity.this)
                    .setCallbacks(callback)
                    .setTimeout(30L, TimeUnit.SECONDS)
                    .build();

            PhoneAuthProvider.verifyPhoneNumber(options);

        });

        btnVerifyCode.setOnClickListener(view -> {

            String code = edCode.getText().toString();


            AuthCredential phoneCredential = PhoneAuthProvider.getCredential(verificationId ,code );

            mAuth.signInWithCredential(phoneCredential)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()){
                        Log.d("DDDD" , "VerifyPhone: Successful go To Home");
                    }else{
                        Log.d("DDDD" , "VerifyPhone: Failed " + task.getException().getLocalizedMessage());
                    }
                }
            });

        });
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            Log.d("DDDD", "PhoneVerification: onVerificationCompleted");
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Log.d("DDDD", "PhoneVerification: onVerificationFailed " + e.getLocalizedMessage());

        }


        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);


            PhoneVerificationActivity.this.verificationId = s;
        }
    };


    private String verificationId = "";


}