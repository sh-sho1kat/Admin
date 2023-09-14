package com.example.admin.userpart.ui.routine;

import static java.sql.DriverManager.println;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.R;
import com.example.admin.adminpart.notice.NoticeAdapter;
import com.example.admin.adminpart.notice.NoticeData;
import com.example.admin.adminpart.students.StudentData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


public class RoutineFragment extends Fragment {
    private RecyclerView RoutineRecycler;
    private ProgressBar progressBar;
    private DatabaseReference reference,dbref;
    String dept="";
    private NoticeAdapter adapter;
    //TextView depttext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routine, container, false);
        RoutineRecycler = view.findViewById(R.id.RoutineRecycler);
        progressBar = view.findViewById(R.id.progressBar);
        //depttext = view.findViewById(R.id.depttext);

        reference = FirebaseDatabase.getInstance().getReference().child("Departments");
        dbref = FirebaseDatabase.getInstance().getReference().child("Students");
        showroutine();
        progressBar.setVisibility(View.GONE);
        // Inflate the layout for this fragment
        return view;
    }

    private void showroutine() {
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for(DataSnapshot snapshot : datasnapshot.getChildren())
                {
                    StudentData data = snapshot.getValue(StudentData.class);
                    assert data != null;
                    assert getArguments() != null;
                    if (data.id.equals(getArguments().getString("userID")))
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
                        //depttext.setText(dept);
                        reference.child(dept).child("routine").addValueEventListener(new ValueEventListener() {
                            ArrayList<NoticeData> list = new ArrayList<>();
                            @Override
                            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                                for(DataSnapshot snapshot :datasnapshot.getChildren())
                                {
                                    NoticeData data = snapshot.getValue(NoticeData.class);
                                    list.add(0,data);
                                }
                                RoutineRecycler.setHasFixedSize(true);
                                adapter = new NoticeAdapter(getContext(),list);
                                RoutineRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                                RoutineRecycler.setAdapter(adapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}