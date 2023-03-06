package com.example.authenticationdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences sharedPreferences=getSharedPreferences("auth",MODE_PRIVATE);
                boolean usercheck=sharedPreferences.getBoolean("flag",false);
                Intent intent;
                if (usercheck)
                {
                    Toast.makeText(splashscreen.this, "login", Toast.LENGTH_SHORT).show();
                    intent=new Intent(splashscreen.this,HomeActivity.class);
                }
                else
                {
                    Toast.makeText(splashscreen.this, "not", Toast.LENGTH_SHORT).show();
                    intent=new Intent(splashscreen.this,LoginActivity.class);
                }
            startActivity(intent);

            }
        },2000);
    }
}