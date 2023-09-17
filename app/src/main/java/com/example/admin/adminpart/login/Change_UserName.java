package com.example.admin.adminpart.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Change_UserName extends AppCompatActivity {

    private EditText userName,Password;
    private Button submit;
    private DatabaseReference reference,passref,userref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_name);

        userName = findViewById(R.id.new_username);
        Password = findViewById(R.id.password);
        submit = findViewById(R.id.Submit);

        reference = FirebaseDatabase.getInstance().getReference().child("Admin");
        passref = reference.child("Password");
        userref = reference.child("UserName");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                passref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        if(!datasnapshot.exists())
                        {
                            passref.setValue("admin");
                        }
                        else
                        {
                            String passWord = datasnapshot.getValue(String.class);
                            if (Password.getText().toString().equals(passWord)) {
                                if(userName.getText().toString().isEmpty())
                                {
                                    Toast.makeText(Change_UserName.this, "Please Type Username", Toast.LENGTH_SHORT).show();

                                }
                                else
                                {
                                    userref.setValue(userName.getText().toString());
                                    Intent intent = new Intent(Change_UserName.this, UserName_Updated.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Change_UserName.this, "DataBase Error!!!", Toast.LENGTH_SHORT).show();
                    }
                });





            }
        });

    }
}