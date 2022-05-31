package com.kradwan.authfirebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    EditText edUsername, edEmail, edPassword;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        edUsername = findViewById(R.id.ed_username);
        edPassword = findViewById(R.id.ed_password);
        edEmail = findViewById(R.id.ed_email);
        btnRegister = findViewById(R.id.btn_register);


        btnRegister.setOnClickListener(view -> {

            String fullName = edUsername.getText().toString();
            String email = edEmail.getText().toString();
            String password = edPassword.getText().toString();


            Task<AuthResult> result = mAuth.createUserWithEmailAndPassword(email, password);


            result.addOnCompleteListener(task -> {

                // TODO
                if (task.isSuccessful()) {
                    Log.d("DDDD", "Create Account Successfully");
//                    FirebaseUser user = mAuth.getCurrentUser();
//                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    FirebaseUser user = task.getResult().getUser();
                    updateUserProfile(user);
                } else {
                    Log.d("DDDD", "Create Account Failed : " + task.getException().getLocalizedMessage());
                }

            });
            // TODO
        });
    }

    private void updateUserProfile(FirebaseUser user) {
        // TODO
        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setDisplayName(edUsername.getText().toString())
                .setPhotoUri(Uri.parse("https//www.google.com/photo"))
                .build();

        Task<Void> result = user.updateProfile(request);

        result.addOnCompleteListener(task -> {

            if (task.isSuccessful()){


                Log.d("DDDD" , " Thanks for register");
            }else{
                Log.d("DDDD" , " Error on update profile: " + task.getException().getLocalizedMessage());
            }
        });
    }
}