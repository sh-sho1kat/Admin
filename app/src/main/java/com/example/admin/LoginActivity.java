package com.example.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText username;
    private EditText password;
    private Button login;
    private TextView wrongpass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=(EditText) findViewById(R.id.AddTitle);
        password=(EditText) findViewById(R.id.password);
        login=(Button) findViewById(R.id.loginButton);
        wrongpass=(TextView) findViewById(R.id.WrongPassword);

        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            wrongpass.setText("Wrong Password!!!");
        }

    }
}