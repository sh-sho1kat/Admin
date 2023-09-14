package com.example.admin.userpart.showdepartment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.admin.R;
import com.example.admin.adminpart.departments.facultymembers.FacultyAdapter;
import com.example.admin.adminpart.departments.facultymembers.FacultyInformation;
import com.example.admin.adminpart.faculty.TeacherAdapter;
import com.example.admin.adminpart.faculty.TeacherData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Show_Department_Info extends AppCompatActivity {

    private RecyclerView HeadOfDepartment,FacultyMembers;
    private LinearLayout HeadNoData,FacultyNoData;
    private List<TeacherData> list1,list2;
    private DatabaseReference reference,dbref;

    private TeacherAdapter adapter;
    String catagory,dept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_department_info);

        HeadOfDepartment = findViewById(R.id.HeadOfDepartment);
        FacultyMembers = findViewById(R.id.FacultyMembers);
        HeadNoData = findViewById(R.id.HeadNoData);
        FacultyNoData = findViewById(R.id.FacultyNoData);

        dept = getIntent().getStringExtra("dept");

        reference = FirebaseDatabase.getInstance().getReference().child("Departments");

        headofdepartment();
        facultymembers();
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
                        HeadOfDepartment.setLayoutManager(new LinearLayoutManager(Show_Department_Info.this));

                        adapter = new TeacherAdapter(list1,"Head Of Department",Show_Department_Info.this,"user");
                        HeadOfDepartment.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Show_Department_Info.this, "Database Error", Toast.LENGTH_SHORT).show();
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
                        FacultyMembers.setLayoutManager(new LinearLayoutManager(Show_Department_Info.this));

                        adapter = new TeacherAdapter(list2,"Faculty Members",Show_Department_Info.this,"user");
                        FacultyMembers.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Show_Department_Info.this, "Database Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}