package com.example.admin.userpart.ui.notice;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.admin.R;
import com.example.admin.adminpart.departments.facultymembers.FacultyAdapter;
import com.example.admin.adminpart.departments.facultymembers.FacultyInformation;
import com.example.admin.adminpart.faculty.TeacherData;
import com.example.admin.adminpart.notice.NoticeAdapter;
import com.example.admin.adminpart.notice.NoticeData;
import com.example.admin.userpart.ui.gallary.Gallary_Adapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class NoticeFragment extends Fragment {
    private RecyclerView noticeRecycler;
    private ProgressBar progressBar;
    private DatabaseReference reference;
    private NoticeAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notice, container, false);
        noticeRecycler = view.findViewById(R.id.noticeRecycler);
        progressBar = view.findViewById(R.id.progressBar);

        reference = FirebaseDatabase.getInstance().getReference().child("Notice");
        showNotice();
        progressBar.setVisibility(View.GONE);
        // Inflate the layout for this fragment
        return view;
    }

    private void showNotice() {
        reference.addValueEventListener(new ValueEventListener() {
            ArrayList<NoticeData> list = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for(DataSnapshot snapshot :datasnapshot.getChildren())
                {
                    NoticeData data = snapshot.getValue(NoticeData.class);
                    list.add(0,data);
                }
                noticeRecycler.setHasFixedSize(true);
                adapter = new NoticeAdapter(getContext(),list);
                noticeRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                noticeRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}