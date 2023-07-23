package com.example.admin.userpart.ui.routine;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.admin.R;
import com.example.admin.adminpart.notice.NoticeAdapter;
import com.example.admin.adminpart.notice.NoticeData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class RoutineFragment extends Fragment {
    private RecyclerView RoutineRecycler;
    private ProgressBar progressBar;
    private DatabaseReference reference,dbref;
    private NoticeAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routine, container, false);
        RoutineRecycler = view.findViewById(R.id.RoutineRecycler);
        progressBar = view.findViewById(R.id.progressBar);

        reference = FirebaseDatabase.getInstance().getReference().child("Departments");
        showroutine();
        progressBar.setVisibility(View.GONE);
        // Inflate the layout for this fragment
        return view;
    }

    private void showroutine() {
        assert getArguments() != null;
        String dept = getArguments().getString("dept");
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
    }
}