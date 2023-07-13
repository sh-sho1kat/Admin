package com.example.admin.adminpart.departments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DepartmentInformation extends AppCompatActivity {
        private TextView HeadInfo,DeptInfo;
        private LinearLayout HeadNoData,DeptNoData;
        private Button UpdateHeadMessage,UpdateDeptMessage;
        private String dept;
        private DatabaseReference reference,headref,deptref;
        private String message="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_information);

        HeadInfo = findViewById(R.id.HeadInfo);
        DeptInfo = findViewById(R.id.DeptInfo);
        HeadNoData = findViewById(R.id.HeadNoData);
        DeptNoData = findViewById(R.id.DeptNoData);
        UpdateHeadMessage = findViewById(R.id.UpdateHeadMessage);
        UpdateDeptMessage = findViewById(R.id.UpdateDepartmentMessage);

        dept = getIntent().getStringExtra("dept");

        reference = FirebaseDatabase.getInstance().getReference().child("Departments");
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
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DepartmentInformation.this, "Error!", Toast.LENGTH_SHORT).show();
            }
        });

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
                Toast.makeText(DepartmentInformation.this, "Error!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(DepartmentInformation.this, "Error!", Toast.LENGTH_SHORT).show();
            }
        });

        UpdateHeadMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DepartmentInformation.this,UpdateInformation.class);
                intent.putExtra("dept",dept);
                intent.putExtra("info","Message From Head");
                startActivity(intent);
            }
        });

        UpdateDeptMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DepartmentInformation.this,UpdateInformation.class);
                dept = getIntent().getStringExtra("dept");
                intent.putExtra("dept",dept);
                intent.putExtra("info","Department Info");
                startActivity(intent);
            }
        });

    }
}