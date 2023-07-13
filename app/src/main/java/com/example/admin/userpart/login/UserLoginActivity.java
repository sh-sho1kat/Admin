package com.example.admin.userpart.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.admin.R;
import com.example.admin.adminpart.students.StudentData;
import com.example.admin.userpart.UserMainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserLoginActivity extends AppCompatActivity {
    private EditText StudentID, Password;
    String UserID = "";
    String password = "", Email;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private ArrayList<StudentData> list;

    private ProgressDialog PB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        StudentID = findViewById(R.id.studentid);
        Password = findViewById(R.id.password);
        TextView register = findViewById(R.id.register);
        TextView forgotPassword = findViewById(R.id.forgotPassword);
        Button logInButton = findViewById(R.id.loginButton);

        PB = new ProgressDialog(this);

        reference = FirebaseDatabase.getInstance().getReference().child("Students");
        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserLoginActivity.this, UserRegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserLoginActivity.this, ForgotPassword.class);
                startActivity(intent);
            }
        });

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserID = StudentID.getText().toString();
                password = Password.getText().toString();
                checkValidation();
            }
        });

    }

    private void checkValidation() {
        if (UserID.isEmpty()) {
            Toast.makeText(UserLoginActivity.this, "Please Enter Student ID", Toast.LENGTH_SHORT).show();
            StudentID.setError("Empty");
            StudentID.requestFocus();
        } else if (password.isEmpty()) {
            Password.setError("Required");
            Password.requestFocus();
        } else
        {
            PB.setMessage("Please Wait...");
            PB.show();
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                    list = new ArrayList<>();
                    if (!datasnapshot.exists())
                    {
                        Toast.makeText(UserLoginActivity.this, "Invalid Student ID 1", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        for (DataSnapshot snapshot : datasnapshot.getChildren())
                        {
                            StudentData data = snapshot.getValue(StudentData.class);
                            assert data != null;
                            if (data.id.equals(UserID))
                            {
                                list.add(data);
                            }
                        }
                        if (list.isEmpty()) {
                            Toast.makeText(UserLoginActivity.this, "Invalid Student ID", Toast.LENGTH_SHORT).show();
                        } else {
                            StudentData studentData = list.get(0);
                            Email = studentData.email;
                            login(Email,password);
                        }
                    }
                    PB.dismiss();
                }
                private void login(String email, String pass) {
                    mAuth.signInWithEmailAndPassword(email, pass)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Toast.makeText(UserLoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(UserLoginActivity.this, UserMainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(UserLoginActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                                }
                            });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(UserLoginActivity.this, "Database Error", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}