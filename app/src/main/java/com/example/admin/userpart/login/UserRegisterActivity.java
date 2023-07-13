package com.example.admin.userpart.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.R;
import com.example.admin.adminpart.students.StudentData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserRegisterActivity extends AppCompatActivity {

    private EditText userID, password, retypepassword;
    Button signup;
    String UserID = "";
    String Password = "", RetypePassword = "", Email;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private ArrayList<StudentData> list;

    private ProgressDialog PB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        userID = findViewById(R.id.UserID);
        password = findViewById(R.id.password);
        retypepassword = findViewById(R.id.retypepassword);
        signup = findViewById(R.id.signUpButton);

        PB = new ProgressDialog(this);

        reference = FirebaseDatabase.getInstance().getReference().child("Students");
        mAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserID = userID.getText().toString();
                Password = password.getText().toString();
                RetypePassword = retypepassword.getText().toString();
                checkValidation();
            }
        });
    }

    private void checkValidation() {
        if (UserID.isEmpty()) {
            Toast.makeText(UserRegisterActivity.this, "Please Enter Student ID", Toast.LENGTH_SHORT).show();
            userID.setError("Empty");
            userID.requestFocus();
        } else if (Password.isEmpty()) {
            password.setError("Required");
            password.requestFocus();
        } else if (RetypePassword.isEmpty()) {
            retypepassword.setError("Required");
            retypepassword.requestFocus();
        } else if (!Password.equals(RetypePassword)) {
            password.setText("");
            retypepassword.setText("");
            password.setError("Error");
            password.requestFocus();
            Toast.makeText(UserRegisterActivity.this, "Enter Password Again", Toast.LENGTH_SHORT).show();
        } else {
            PB.setMessage("Please Wait...");
            PB.show();
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                    list = new ArrayList<>();
                    if (!datasnapshot.exists()) {
                        Toast.makeText(UserRegisterActivity.this, "Invalid Student ID 1", Toast.LENGTH_SHORT).show();
                        PB.dismiss();
                    } else {
                        for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                            StudentData data = snapshot.getValue(StudentData.class);
                            if (data.id.equals(UserID)) {
                                list.add(data);
                            }
                        }
                        if (list.isEmpty()) {
                            Toast.makeText(UserRegisterActivity.this, "Invalid Student ID", Toast.LENGTH_SHORT).show();
                        } else {

                            if (Password.length() < 6) {
                                Toast.makeText(UserRegisterActivity.this, "Password length should be at least 6 characters.", Toast.LENGTH_SHORT).show();
                                password.setText("");
                                retypepassword.setText("");
                                password.requestFocus();
                            } else if (!Password.matches("^[a-zA-Z0-9]*$")) {
                                Toast.makeText(UserRegisterActivity.this, "Password should only contain alphanumeric characters.", Toast.LENGTH_SHORT).show();

                                password.setText("");
                                retypepassword.setText("");
                                password.requestFocus();
                            } else {
                                StudentData studentData = list.get(0);
                                Email = studentData.email;
                                mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(UserRegisterActivity.this, "Registration Successfull!!!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(UserRegisterActivity.this, UserLoginActivity.class);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(UserRegisterActivity.this, "SignUp Failed. " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    }
                    PB.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(UserRegisterActivity.this, "Database Error", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    private void reload() {
    }
}