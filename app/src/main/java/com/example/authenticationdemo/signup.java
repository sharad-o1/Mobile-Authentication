package com.example.authenticationdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class signup extends AppCompatActivity {
    EditText edemail1,edpassword1,edphone1,edname1;
    Button btnsignup;
    TextView tvsignup;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        auth=FirebaseAuth.getInstance();
        edemail1=findViewById(R.id.edemailsignup);
        edpassword1=findViewById(R.id.edpasswordsignup);
        tvsignup=findViewById(R.id.tvsignup);
        edphone1=findViewById(R.id.edphone);
        edname1=findViewById(R.id.ednamesignup);
        btnsignup=findViewById(R.id.btnsignup);
        String  emailformal="[a-zA-Z0-9._-]+@[a-z]+\\\\.[a-z]+";
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Name=edname1.getText().toString();
                String Password=edpassword1.getText().toString();
                String Phone=edphone1.getText().toString();
                String Email=edemail1.getText().toString();


                if (Name.isEmpty() || Email.isEmpty() || Password.isEmpty() ||Phone.isEmpty()) {
                    Toast.makeText(signup.this,"fill the fields ",Toast.LENGTH_SHORT).show();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches())
                {
                    Toast.makeText(signup.this, "check email", Toast.LENGTH_SHORT).show();
                }
                else if (Password.length()<8)
                {
                    Toast.makeText(signup.this, "user 8 charctr or more ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(signup.this, "Filled ", Toast.LENGTH_SHORT).show();
                    CheckEmail(Name,Password,Phone,Email);
                }
            }
        });


    }

    private void CheckEmail(String name, String password, String phone, String email) {
        auth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                boolean check=task.getResult().getSignInMethods().isEmpty();
                if (check)
                {
                    Toast.makeText(signup.this, "new user", Toast.LENGTH_SHORT).show();
                    authcreate(name,password,phone,email);
                }
                else
                {
                    Toast.makeText(signup.this, "This Email is already taken", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(signup.this, "error::"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void authcreate(String name, String password, String phone, String email) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    Toast.makeText(signup.this, "auth done", Toast.LENGTH_SHORT).show();
                    DatabaseReference reference= FirebaseDatabase.getInstance().getReference("User");
                    String uid=auth.getUid();
                    Model model=new Model(name,phone,email,password,uid);
                    reference.child(uid).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(signup.this, "data add in realtime database", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(signup.this,LoginActivity.class));
                            }
                            else
                            {
                                Toast.makeText(signup.this, "failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else
                {
                    Toast.makeText(signup.this, "sign fail", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }
}