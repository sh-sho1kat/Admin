package com.example.admin.userpart.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.R;
import com.example.admin.adminpart.students.StudentData;
import com.example.admin.userpart.UserMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ForgotPassword extends AppCompatActivity {
    private EditText idBox;
    private TextView emailfinder;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private DatabaseReference reference;
    private ArrayList<StudentData> list;
    String Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        idBox = findViewById(R.id.idBox);

        Button btnReset = findViewById(R.id.btnReset);

        Button btnCancel = findViewById(R.id.btnCancel);

        progressBar = findViewById(R.id.progressBar);
        emailfinder = findViewById(R.id.emailfinder);

        reference = FirebaseDatabase.getInstance().getReference().child("Students");
        auth = FirebaseAuth.getInstance();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        list = new ArrayList<>();
                        if (!datasnapshot.exists())
                        {
                            Toast.makeText(ForgotPassword.this, "Invalid Student ID ", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            for (DataSnapshot snapshot : datasnapshot.getChildren())
                            {
                                StudentData data = snapshot.getValue(StudentData.class);
                                assert data != null;
                                if (data.id.equals(idBox.getText().toString().trim()))
                                {
                                    list.add(data);
                                }
                            }
                            if (list.isEmpty()) {
                                Toast.makeText(ForgotPassword.this, "Invalid Student ID", Toast.LENGTH_SHORT).show();
                            } else {
                                StudentData studentData = list.get(0);
                                String EMAIL = studentData.email;

                                auth.sendPasswordResetEmail(studentData.email)

                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
//                                                    emailfinder.setText("An Email has been sent to "+ EMAIL);
//                                                    emailfinder.setVisibility(View.VISIBLE);
                                                    Toast.makeText(ForgotPassword.this, "An Email has been sent to "+ EMAIL+". Please check your email inbox!", Toast.LENGTH_LONG).show();
                                                    finish();
                                                } else {
                                                    Toast.makeText(ForgotPassword.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                                }

                                                progressBar.setVisibility(View.GONE);
                                            }
                                        });
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ForgotPassword.this, "Database Error", Toast.LENGTH_SHORT).show();
                    }
                });

            }

        });
    }
}