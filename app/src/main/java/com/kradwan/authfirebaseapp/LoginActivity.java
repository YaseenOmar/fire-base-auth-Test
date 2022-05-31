package com.kradwan.authfirebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText edEmail, edPassword;
    Button btnLogin;

    ProgressBar progressBar;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = findViewById(R.id.loading);
        edEmail = findViewById(R.id.ed_email);
        edPassword = findViewById(R.id.ed_password);

        btnLogin = findViewById(R.id.btn_login);


        btnLogin.setOnClickListener(view -> {

            progressBar.setVisibility(View.VISIBLE);

            Task<AuthResult> result = mAuth.signInWithEmailAndPassword(edEmail.getText().toString(), edPassword.getText().toString());

            result.addOnCompleteListener(task -> {

                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()){

                 FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();

                    Log.d("DDDD" , "Login: Thanks for Login ");
                    // TOOD
                }else{
                    Log.d("DDDD" ,"Login:  Error on Login Operation; " + task.getException().getLocalizedMessage());
                }
            });

        });
    }
}