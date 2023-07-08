package com.example.admin.departments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.admin.R;

public class Department_activity extends AppCompatActivity implements View.OnClickListener {
    private CardView CSE,EEE,ECE,ETE,Civil,Mechanical,IPE,Architecture;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);
        CSE = findViewById(R.id.CSE);
        EEE = findViewById(R.id.EEE);
        ECE = findViewById(R.id.ECE);
        ETE = findViewById(R.id.ETE);
        Civil = findViewById(R.id.Civil);
        Mechanical = findViewById(R.id.Mechanical);
        IPE = findViewById(R.id.IPE);
        Architecture = findViewById(R.id.Architecture);

        CSE.setOnClickListener(this);
        EEE.setOnClickListener(this);
        ECE.setOnClickListener(this);
        ETE.setOnClickListener(this);
        Civil.setOnClickListener(this);
        Mechanical.setOnClickListener(this);
        IPE.setOnClickListener(this);
        Architecture.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.CSE:
                intent =new Intent(Department_activity.this,DepartmentHomepage.class);
                intent.putExtra("dept","CSE");
                startActivity(intent);
                break;
            case R.id.EEE:
                intent =new Intent(Department_activity.this,DepartmentHomepage.class);
                intent.putExtra("dept","EEE");
                startActivity(intent);
                break;
            case R.id.ECE:
                intent =new Intent(Department_activity.this,DepartmentHomepage.class);
                intent.putExtra("dept","ECE");
                startActivity(intent);
                break;
            case R.id.ETE:
                intent =new Intent(Department_activity.this,DepartmentHomepage.class);
                intent.putExtra("dept","ETE");
                startActivity(intent);
                break;
            case R.id.Civil:
                intent =new Intent(Department_activity.this,DepartmentHomepage.class);
                intent.putExtra("dept","Civil");
                startActivity(intent);
                break;
            case R.id.Mechanical:
                intent =new Intent(Department_activity.this,DepartmentHomepage.class);
                intent.putExtra("dept","Mechanical");
                startActivity(intent);
                break;
            case R.id.IPE:
                intent =new Intent(Department_activity.this,DepartmentHomepage.class);
                intent.putExtra("dept","IPE");
                startActivity(intent);
                break;
            case R.id.Architecture:
                intent =new Intent(Department_activity.this,DepartmentHomepage.class);
                intent.putExtra("dept","Architecture");
                startActivity(intent);
                break;

        }

    }
}