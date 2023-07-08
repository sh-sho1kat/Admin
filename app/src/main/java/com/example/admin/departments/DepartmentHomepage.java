package com.example.admin.departments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.admin.R;
import com.example.admin.departments.facultymembers.FacultyInformation;
import com.example.admin.departments.routine.Routine_Activity;

public class DepartmentHomepage extends AppCompatActivity {
    private Button DepartmentInfo,FacultyInfo,Routine;
    private String dept;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_homepage);

        DepartmentInfo = findViewById(R.id.DepartmentInfo);
        FacultyInfo = findViewById(R.id.FacultyInfo);
        Routine = findViewById(R.id.Routine);

        DepartmentInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(DepartmentHomepage.this, DepartmentInformation.class);
                dept = getIntent().getStringExtra("dept");
                intent.putExtra("dept",dept);
                startActivity(intent);
            }
        });

        FacultyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(DepartmentHomepage.this, FacultyInformation.class);
                dept = getIntent().getStringExtra("dept");
                intent.putExtra("dept",dept);
                startActivity(intent);
            }
        });

        Routine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(DepartmentHomepage.this, Routine_Activity.class);
                dept = getIntent().getStringExtra("dept");
                intent.putExtra("dept",dept);
                startActivity(intent);
            }
        });
    }
}