package com.example.admin.userpart.about;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.R;
import com.example.admin.adminpart.departments.DepartmentInformation;
import com.example.admin.adminpart.notice.NoticeAdapter;
import com.example.admin.adminpart.notice.NoticeData;
import com.example.admin.adminpart.students.StudentData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class About_Activity extends AppCompatActivity {

    private TextView HeadInfo,DeptInfo;
    private LinearLayout HeadNoData,DeptNoData;
    private DatabaseReference reference,headref,deptref,dbref;
    private String message="";
    String dept="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        HeadInfo = findViewById(R.id.HeadInfo);
        DeptInfo = findViewById(R.id.DeptInfo);
        HeadNoData = findViewById(R.id.HeadNoData);
        DeptNoData = findViewById(R.id.DeptNoData);

        dbref = FirebaseDatabase.getInstance().getReference().child("Students");
        reference = FirebaseDatabase.getInstance().getReference().child("Departments");

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for(DataSnapshot snapshot : datasnapshot.getChildren())
                {
                    StudentData data = snapshot.getValue(StudentData.class);
                    assert data != null;
                    if (data.id.equals(getIntent().getStringExtra("userID")))
                    {
                        dept = data.department;
                        switch(dept)
                        {
                            case "Computer Science and Engineering":
                                dept="CSE";
                                break;
                            case "Electrical and Electronic Engineering":
                                dept="EEE";
                                break;
                            case "Civil Engineering":
                                dept="Civil";
                                break;
                            case "Mechanical Engineering":
                                dept="Mechanical";
                                break;
                            case "Electrical and Computer Engineering":
                                dept="ECE";
                                break;
                            case "Electronic and Telecommunication Engineering":
                                dept="ETE";
                                break;
                            case "Industrial and Production Engineering":
                                dept="IPE";
                                break;
                            case "Architecture":
                                dept="Architecture";
                                break;

                        }

                        headref=reference.child(dept).child("Message From Head");
                        deptref=reference.child(dept).child("Department Info");

                        headref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                message = dataSnapshot.getValue(String.class);
                                if(!dataSnapshot.exists())
                                {
                                    HeadNoData.setVisibility(View.VISIBLE);
                                    HeadInfo.setVisibility(View.GONE);
                                }
                                else if(message.isEmpty())
                                {
                                    HeadNoData.setVisibility(View.VISIBLE);
                                    HeadInfo.setVisibility(View.GONE);
                                }
                                else
                                {
                                    HeadInfo.setText(message);
                                    HeadInfo.setVisibility(View.VISIBLE);
                                    HeadNoData.setVisibility(View.GONE);
                                    message="";
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(About_Activity.this, "Error!", Toast.LENGTH_SHORT).show();
                            }
                        });

                        deptref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                message = dataSnapshot.getValue(String.class);
                                if(!dataSnapshot.exists())
                                {
                                    DeptNoData.setVisibility(View.VISIBLE);
                                    DeptInfo.setVisibility(View.GONE);
                                }
                                else if(message.isEmpty())
                                {
                                    DeptNoData.setVisibility(View.VISIBLE);
                                    DeptInfo.setVisibility(View.GONE);
                                }
                                else
                                {
                                    DeptInfo.setText(message);
                                    DeptInfo.setVisibility(View.VISIBLE);
                                    DeptNoData.setVisibility(View.GONE);
                                    message="";
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(About_Activity.this, "Error!", Toast.LENGTH_SHORT).show();
                            }
                        });

                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(About_Activity.this, "Database Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}