package com.example.admin.userpart.userfaculty;

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
import com.example.admin.adminpart.faculty.Add_Teachers;
import com.example.admin.adminpart.faculty.TeacherAdapter;
import com.example.admin.adminpart.faculty.TeacherData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DisplayFacultyInfo extends AppCompatActivity {

    FloatingActionButton fab;
    private RecyclerView viceChancellor,eceFaculty,civilFaculty,meFaculty,humFaculty;
    private LinearLayout vcNoData,eceNoData,civilNoData,meNoData,humNoData;
    private List<TeacherData>  list1,list2,list3,list4,list5;
    private DatabaseReference reference,dbref;

    private TeacherAdapter adapter;
    String catagory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_faculty_info);

        viceChancellor = findViewById(R.id.viceChancellor);
        eceFaculty = findViewById(R.id.eceFaculty);
        civilFaculty = findViewById(R.id.civilFaculty);
        meFaculty = findViewById(R.id.meFaculty);
        humFaculty = findViewById(R.id.humFaculty);

        vcNoData = findViewById(R.id.vcNoData);
        eceNoData = findViewById(R.id.eceNoData);
        civilNoData = findViewById(R.id.civilNoData);
        meNoData = findViewById(R.id.meNoData);
        humNoData = findViewById(R.id.humNoData);

        reference = FirebaseDatabase.getInstance().getReference().child("Faculty");

        viceChancellor();
        eceFaculty();
        civilFaculty();
        meFaculty();
        humFaculty();

    }

    private void viceChancellor() {
        catagory = "Vice Chancellor";
        dbref = reference.child(catagory);
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list1 = new ArrayList<>();
                if(!datasnapshot.exists())
                {
                    vcNoData.setVisibility(View.VISIBLE);
                    viceChancellor.setVisibility(View.GONE);
                }
                else
                {
                    vcNoData.setVisibility(View.GONE);
                    viceChancellor.setVisibility(View.VISIBLE);

                    for(DataSnapshot snapshot : datasnapshot.getChildren())
                    {
                        TeacherData data = snapshot.getValue(TeacherData.class);
                        list1.add(data);
                        viceChancellor.setHasFixedSize(true);
                        viceChancellor.setLayoutManager(new LinearLayoutManager(com.example.admin.userpart.userfaculty.DisplayFacultyInfo.this));

                        adapter = new TeacherAdapter(list1,"Vice Chancellor", com.example.admin.userpart.userfaculty.DisplayFacultyInfo.this,"user");
                        viceChancellor.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(com.example.admin.userpart.userfaculty.DisplayFacultyInfo.this, "Database Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void eceFaculty() {
        catagory = "Faculty of Electrical and Computer Engineering";
        dbref = reference.child(catagory);
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list2 = new ArrayList<>();
                if(!datasnapshot.exists())
                {
                    eceNoData.setVisibility(View.VISIBLE);
                    eceFaculty.setVisibility(View.GONE);
                }
                else
                {
                    eceNoData.setVisibility(View.GONE);
                    eceFaculty.setVisibility(View.VISIBLE);

                    for(DataSnapshot snapshot : datasnapshot.getChildren())
                    {
                        TeacherData data = snapshot.getValue(TeacherData.class);
                        list2.add(data);
                        eceFaculty.setHasFixedSize(true);
                        eceFaculty.setLayoutManager(new LinearLayoutManager(com.example.admin.userpart.userfaculty.DisplayFacultyInfo.this));

                        adapter = new TeacherAdapter(list2,"Faculty of Electrical and Computer Engineering", com.example.admin.userpart.userfaculty.DisplayFacultyInfo.this,"user");
                        eceFaculty.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(com.example.admin.userpart.userfaculty.DisplayFacultyInfo.this, "Database Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void civilFaculty() {
        catagory="Faculty of Civil Engineering";
        dbref = reference.child(catagory);
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list3 = new ArrayList<>();
                if(!datasnapshot.exists())
                {
                    civilNoData.setVisibility(View.VISIBLE);
                    civilFaculty.setVisibility(View.GONE);
                }
                else
                {
                    civilNoData.setVisibility(View.GONE);
                    civilFaculty.setVisibility(View.VISIBLE);

                    for(DataSnapshot snapshot : datasnapshot.getChildren())
                    {
                        TeacherData data = snapshot.getValue(TeacherData.class);
                        list3.add(data);
                        civilFaculty.setHasFixedSize(true);
                        civilFaculty.setLayoutManager(new LinearLayoutManager(com.example.admin.userpart.userfaculty.DisplayFacultyInfo.this));

                        adapter = new TeacherAdapter(list3,"Faculty of Civil Engineering", com.example.admin.userpart.userfaculty.DisplayFacultyInfo.this,"user");
                        civilFaculty.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(com.example.admin.userpart.userfaculty.DisplayFacultyInfo.this, "Database Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void meFaculty() {
        catagory = "Faculty of Mechanical Engineering";
        dbref = reference.child(catagory);
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list4 = new ArrayList<>();
                if(!datasnapshot.exists())
                {
                    meNoData.setVisibility(View.VISIBLE);
                    meFaculty.setVisibility(View.GONE);
                }
                else
                {
                    meNoData.setVisibility(View.GONE);
                    meFaculty.setVisibility(View.VISIBLE);

                    for(DataSnapshot snapshot : datasnapshot.getChildren())
                    {
                        TeacherData data = snapshot.getValue(TeacherData.class);
                        list4.add(data);
                        meFaculty.setHasFixedSize(true);
                        meFaculty.setLayoutManager(new LinearLayoutManager(com.example.admin.userpart.userfaculty.DisplayFacultyInfo.this));

                        adapter = new TeacherAdapter(list4,"Faculty of Mechanical Engineering", com.example.admin.userpart.userfaculty.DisplayFacultyInfo.this,"user");
                        meFaculty.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(com.example.admin.userpart.userfaculty.DisplayFacultyInfo.this, "Database Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void humFaculty() {
        catagory = "Faculty of Humanities";
        dbref = reference.child(catagory);
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list5 = new ArrayList<>();
                if(!datasnapshot.exists())
                {
                    humNoData.setVisibility(View.VISIBLE);
                    humFaculty.setVisibility(View.GONE);
                }
                else
                {
                    humNoData.setVisibility(View.GONE);
                    humFaculty.setVisibility(View.VISIBLE);

                    for(DataSnapshot snapshot : datasnapshot.getChildren())
                    {
                        TeacherData data = snapshot.getValue(TeacherData.class);
                        list5.add(data);
                        humFaculty.setHasFixedSize(true);
                        humFaculty.setLayoutManager(new LinearLayoutManager(com.example.admin.userpart.userfaculty.DisplayFacultyInfo.this));

                        adapter = new TeacherAdapter(list5,"Faculty of Humanities", com.example.admin.userpart.userfaculty.DisplayFacultyInfo.this,"user");
                        humFaculty.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(com.example.admin.userpart.userfaculty.DisplayFacultyInfo.this, "Database Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}