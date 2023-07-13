package com.example.admin.adminpart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.admin.R;
import com.example.admin.adminpart.departments.Department_activity;
import com.example.admin.adminpart.faculty.update_faculty;
import com.example.admin.adminpart.notice.Notice_Activity;
import com.example.admin.adminpart.students.Student_Activity;

public class MainActivity extends AppCompatActivity {

    private CardView Card1,Card2,Card3,Card4,Card5,Card6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Card1 = (CardView) findViewById(R.id.Card1);
        Card2 = (CardView) findViewById(R.id.Card2);
        Card3 = (CardView) findViewById(R.id.Card3);
        Card4 = (CardView) findViewById(R.id.Card4);
        Card5 = (CardView) findViewById(R.id.Card5);
        Card6 = (CardView) findViewById(R.id.Card6);

        Card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, Department_activity.class);
                startActivity(intent);
            }
        });

        Card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, Notice_Activity.class);
                startActivity(intent);
            }
        });

        Card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, update_faculty.class);
                startActivity(intent);
            }
        });

        Card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, Student_Activity.class);
                startActivity(intent);
            }
        });

        Card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, Gallary_Activity.class);
                startActivity(intent);
            }
        });

        Card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, Ebook_Activity.class);
                startActivity(intent);
            }
        });


    }
}