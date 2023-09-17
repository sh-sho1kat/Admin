package com.example.admin.adminpart.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.R;
import com.example.admin.adminpart.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login;
    private TextView changepass,changeuser;
    private DatabaseReference reference,userref,passref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=(EditText) findViewById(R.id.AddTitle);
        password=(EditText) findViewById(R.id.password);
        login=(Button) findViewById(R.id.loginButton);
        changepass=(TextView) findViewById(R.id.ChangePassword);
        changeuser=(TextView) findViewById(R.id.ChangeUsername);

        reference = FirebaseDatabase.getInstance().getReference().child("Admin");
        userref = reference.child("UserName");
        passref = reference.child("Password");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                userref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        if(!datasnapshot.exists())
                        {
                            userref.setValue("admin");
                            passref.setValue("admin");
                        }
                        else
                        {
                            String userName = datasnapshot.getValue(String.class);
                            passref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String passWord = snapshot.getValue(String.class);
                                    if (username.getText().toString().equals(userName) && password.getText().toString().equals(passWord)) {
                                        Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                                        Toast.makeText(LoginActivity.this, "Login Successfull!!!", Toast.LENGTH_SHORT).show();
                                        startActivity(intent);
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(LoginActivity.this, "Wrong Username or Password!!!", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(LoginActivity.this, "DataBase Error!!!", Toast.LENGTH_SHORT).show();
                    }
                });





            }
        });
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this, update_password.class);
                startActivity(intent);
                finish();
            }
        });

        changeuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this, Change_UserName.class);
                startActivity(intent);
                finish();
            }
        });

    }
}