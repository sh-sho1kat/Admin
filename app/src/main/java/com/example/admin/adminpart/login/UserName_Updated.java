package com.example.admin.adminpart.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.admin.R;

public class UserName_Updated extends AppCompatActivity {

    private Button SucceswsMesage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_name_updated);

        SucceswsMesage = findViewById(R.id.success_message_btn);

        SucceswsMesage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(UserName_Updated.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}