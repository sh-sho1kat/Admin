package com.example.admin.faculty;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class update_faculty extends AppCompatActivity {

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
        setContentView(R.layout.activity_update_faculty);

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


        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(update_faculty.this,Add_Teachers.class));
            }
        });
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
                        viceChancellor.setLayoutManager(new LinearLayoutManager(update_faculty.this));

                        adapter = new TeacherAdapter(list1,"Vice Chancellor",update_faculty.this);
                        viceChancellor.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(update_faculty.this, "Database Error", Toast.LENGTH_SHORT).show();
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
                        eceFaculty.setLayoutManager(new LinearLayoutManager(update_faculty.this));

                        adapter = new TeacherAdapter(list2,"Faculty of Electrical and Computer Engineering",update_faculty.this);
                        eceFaculty.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(update_faculty.this, "Database Error", Toast.LENGTH_SHORT).show();
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
                        civilFaculty.setLayoutManager(new LinearLayoutManager(update_faculty.this));

                        adapter = new TeacherAdapter(list3,"Faculty of Civil Engineering",update_faculty.this);
                        civilFaculty.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(update_faculty.this, "Database Error", Toast.LENGTH_SHORT).show();
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
                        meFaculty.setLayoutManager(new LinearLayoutManager(update_faculty.this));

                        adapter = new TeacherAdapter(list4,"Faculty of Mechanical Engineering",update_faculty.this);
                        meFaculty.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(update_faculty.this, "Database Error", Toast.LENGTH_SHORT).show();
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
                        humFaculty.setLayoutManager(new LinearLayoutManager(update_faculty.this));

                        adapter = new TeacherAdapter(list5,"Faculty of Humanities",update_faculty.this);
                        humFaculty.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(update_faculty.this, "Database Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}