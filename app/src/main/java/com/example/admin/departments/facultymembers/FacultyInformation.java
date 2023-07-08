package com.example.admin.departments.facultymembers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.admin.R;
import com.example.admin.faculty.Add_Teachers;
import com.example.admin.faculty.TeacherAdapter;
import com.example.admin.faculty.TeacherData;
import com.example.admin.faculty.update_faculty;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FacultyInformation extends AppCompatActivity {

    FloatingActionButton fab;
    private RecyclerView HeadOfDepartment,FacultyMembers;
    private LinearLayout HeadNoData,FacultyNoData;
    private List<TeacherData> list1,list2;
    private DatabaseReference reference,dbref;

    private FacultyAdapter adapter;
    String catagory,dept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_information);

        fab = findViewById(R.id.fab);

        HeadOfDepartment = findViewById(R.id.HeadOfDepartment);
        FacultyMembers = findViewById(R.id.FacultyMembers);
        HeadNoData = findViewById(R.id.HeadNoData);
        FacultyNoData = findViewById(R.id.FacultyNoData);

        dept = getIntent().getStringExtra("dept");

        reference = FirebaseDatabase.getInstance().getReference().child("Departments");

        headofdepartment();
        facultymembers();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FacultyInformation.this, AddDepartmentFaculty.class);
                intent.putExtra("dept",dept);
                startActivity(intent);
            }
        });
    }
    private void headofdepartment() {
        catagory = "Head Of Department";
        dbref = reference.child(dept).child(catagory);
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list1 = new ArrayList<>();
                if(!datasnapshot.exists())
                {
                    HeadNoData.setVisibility(View.VISIBLE);
                    HeadOfDepartment.setVisibility(View.GONE);
                }
                else
                {
                    HeadNoData.setVisibility(View.GONE);
                    HeadOfDepartment.setVisibility(View.VISIBLE);

                    for(DataSnapshot snapshot : datasnapshot.getChildren())
                    {
                        TeacherData data = snapshot.getValue(TeacherData.class);
                        list1.add(data);
                        HeadOfDepartment.setHasFixedSize(true);
                        HeadOfDepartment.setLayoutManager(new LinearLayoutManager(FacultyInformation.this));

                        adapter = new FacultyAdapter(list1,"Head Of Department",FacultyInformation.this);
                        HeadOfDepartment.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FacultyInformation.this, "Database Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void facultymembers() {
        catagory = "Faculty Members";
        dbref = reference.child(dept).child(catagory);
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list2 = new ArrayList<>();
                if(!datasnapshot.exists())
                {
                    FacultyNoData.setVisibility(View.VISIBLE);
                    FacultyMembers.setVisibility(View.GONE);
                }
                else
                {
                    FacultyNoData.setVisibility(View.GONE);
                    FacultyMembers.setVisibility(View.VISIBLE);

                    for(DataSnapshot snapshot : datasnapshot.getChildren())
                    {
                        TeacherData data = snapshot.getValue(TeacherData.class);
                        list2.add(data);
                        FacultyMembers.setHasFixedSize(true);
                        FacultyMembers.setLayoutManager(new LinearLayoutManager(FacultyInformation.this));

                        adapter = new FacultyAdapter(list2,"Faculty Members",FacultyInformation.this);
                        FacultyMembers.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FacultyInformation.this, "Database Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}