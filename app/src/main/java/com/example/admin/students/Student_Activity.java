package com.example.admin.students;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.admin.R;
import com.example.admin.faculty.Add_Teachers;
import com.example.admin.faculty.update_faculty;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Student_Activity extends AppCompatActivity {
    FloatingActionButton fab;
    private RecyclerView StudentInfo;
    private LinearLayout StudentNoData;
    private ArrayList<StudentData> list;
    private StudentAdapter adapter;

    private DatabaseReference reference,dbref;
    private EditText inputStudentID;
    private Button search;
    String studentIDno="";
    private ProgressDialog PB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        fab = findViewById(R.id.fab);
        StudentInfo = findViewById(R.id.StudentInfo);
        StudentNoData = findViewById(R.id.StudentNoData);
        inputStudentID = findViewById(R.id.searchStudentID);
        search = findViewById(R.id.searchStudentButton);
        PB=new ProgressDialog(this);

        reference = FirebaseDatabase.getInstance().getReference().child("Students");

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentIDno = inputStudentID.getText().toString();
                if(studentIDno.isEmpty())
                {
                    inputStudentID.setError("Please Input ID");
                    inputStudentID.requestFocus();
                }
                else
                {
                    PB.setMessage("Please Wait...");
                    PB.show();
                    StudentInfo(studentIDno);
                    inputStudentID.setText("");
                }
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Student_Activity.this, Add_Student.class));
            }
        });
    }

    private void StudentInfo(String studentIDno) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list = new ArrayList<>();
                if(!datasnapshot.exists())
                {
                    StudentNoData.setVisibility(View.VISIBLE);
                    StudentInfo.setVisibility(View.GONE);
                    PB.dismiss();
                }
                else
                {
                    for(DataSnapshot snapshot : datasnapshot.getChildren())
                    {
                        StudentData data =snapshot.getValue(StudentData.class);
                        if(data.id.equals(studentIDno))
                        {
                            list.add(data);
                            StudentInfo.setHasFixedSize(true);
                            StudentInfo.setLayoutManager(new LinearLayoutManager(Student_Activity.this));

                            adapter = new StudentAdapter(list,Student_Activity.this);
                            StudentInfo.setAdapter(adapter);
                        }
                    }
                    if(list.isEmpty())
                    {
                        StudentNoData.setVisibility(View.VISIBLE);
                        StudentInfo.setVisibility(View.GONE);
                    }
                    else
                    {
                        StudentNoData.setVisibility(View.GONE);
                        StudentInfo.setVisibility(View.VISIBLE);
                    }
                    PB.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Student_Activity.this, "Database Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}