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
import com.example.admin.adminpart.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class update_password extends AppCompatActivity {

    private EditText oldPass,newPass,reTypePass;
    private Button submit;
    private DatabaseReference reference,passref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_passward);

        oldPass = findViewById(R.id.old_password);
        newPass = findViewById(R.id.new_password);
        reTypePass = findViewById(R.id.retypepassword);
        submit = findViewById(R.id.Submit);

        reference = FirebaseDatabase.getInstance().getReference().child("Admin");
        passref = reference.child("Password");


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
                            if (oldPass.getText().toString().equals(passWord)) {
                                if(newPass.getText().toString().equals(reTypePass.getText().toString()))
                                {
                                    passref.setValue(newPass.getText().toString());
                                    Intent intent = new Intent(update_password.this, Password_Updated_Message.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(update_password.this, "Wrong Password!!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(update_password.this, "DataBase Error!!!", Toast.LENGTH_SHORT).show();
                    }
                });





            }
        });



    }
}