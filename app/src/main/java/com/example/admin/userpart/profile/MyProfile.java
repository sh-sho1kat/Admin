package com.example.admin.userpart.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.R;
import com.example.admin.adminpart.students.StudentData;
import com.example.admin.userpart.login.UserLoginActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyProfile extends AppCompatActivity {
    private ImageView userImage;
    private TextView name,id,email,department,gender,phone,address;
    private DatabaseReference reference;
    private ArrayList<StudentData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        userImage = findViewById(R.id.user_imageview);
        id = findViewById(R.id.studentid);
        email = findViewById(R.id.email_textview);
        department = findViewById(R.id.department_textview);
        gender = findViewById(R.id.gender_textview);
        phone = findViewById(R.id.phone_textview);
        address = findViewById(R.id.adress_textview);
        name = findViewById(R.id.name_textview);

        reference = FirebaseDatabase.getInstance().getReference().child("Students");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list = new ArrayList<>();
                for (DataSnapshot snapshot : datasnapshot.getChildren())
                {
                    StudentData data = snapshot.getValue(StudentData.class);
                    assert data != null;
                    if (data.id.equals(getIntent().getStringExtra("StudentID")))
                    {
                        list.add(data);
                    }
                }
                StudentData studentData = list.get(0);
                Picasso.get().load(studentData.image).into(userImage);
                id.setText(studentData.id);
                email.setText(studentData.email);
                department.setText(studentData.department);
                gender.setText(studentData.gender);
                phone.setText(studentData.phone);
                name.setText(studentData.name);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}