package com.example.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.admin.adminpart.LoginActivity;
import com.example.admin.userpart.login.UserLoginActivity;

public class LandingPage extends AppCompatActivity {

    private Button UserButton,AdminButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        UserButton = findViewById(R.id.UserButton);
        AdminButton = findViewById(R.id.AdminButton);

        AdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingPage.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        UserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingPage.this, UserLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}