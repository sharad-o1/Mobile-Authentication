package com.example.authenticationdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

public class LoginActivity extends AppCompatActivity {
    EditText edemail, edpassword;
    Button btnlogin;
    TextView tvlogin, tvsignuplink;
    FirebaseAuth auth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog=new ProgressDialog(this);



        edemail = findViewById(R.id.edemaillogin);
        edpassword = findViewById(R.id.edpasswordlogin);
        btnlogin = findViewById(R.id.btnloginlogin);
        tvlogin = findViewById(R.id.tvlogin);
        tvsignuplink = findViewById(R.id.tvsignuplink);

        auth = FirebaseAuth.getInstance();
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = edemail.getText().toString();
                String Password = edpassword.getText().toString();
                if (Email.isEmpty() || Password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "fill the fields", Toast.LENGTH_SHORT).show();
                } else {
                    checkemail(Email, Password);
                    progressDialog.setMessage("Loading..");
                    progressDialog.show();

                }

            }
        });

        tvsignuplink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, signup.class));

            }
        });


    }

    private void checkemail(String email, String password) {
        auth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                boolean check = task.getResult().getSignInMethods().isEmpty();
                if (check) {
                    Toast.makeText(LoginActivity.this, "new user", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(LoginActivity.this, "old user", Toast.LENGTH_SHORT).show();
                    userlogin(email, password);

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }

    private void userlogin(String email, String password) {

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                    SharedPreferences sharedPreferences=getSharedPreferences("auth",MODE_PRIVATE);
                   SharedPreferences.Editor editor= sharedPreferences.edit();
                   editor.putBoolean("flag",true);
                   editor.apply();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    progressDialog.dismiss();

                } else {
                    Toast.makeText(LoginActivity.this, "login fail", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "error::" + e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}