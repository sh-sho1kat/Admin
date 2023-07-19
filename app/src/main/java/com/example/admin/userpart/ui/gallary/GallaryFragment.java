package com.example.admin.userpart.ui.gallary;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.admin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class GallaryFragment extends Fragment {

    RecyclerView IntraCompetitionRecycler,ConvocationRecycler,OtherRecycler;
    Gallary_Adapter adapter;
    DatabaseReference reference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallary, container, false);

        IntraCompetitionRecycler = view.findViewById(R.id.IntraCompetitionRecycler);
        ConvocationRecycler = view.findViewById(R.id.ConvocationRecycler);
        OtherRecycler = view.findViewById(R.id.OtherRecycler);

        reference = FirebaseDatabase.getInstance().getReference().child("Gallary");

        getIntraCompetitionImage();
        getConvocationImage();
        getOtherImage();
        return view;
    }

    private void getOtherImage() {
        reference.child("Others").addValueEventListener(new ValueEventListener() {
            List<String> imagelist = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for(DataSnapshot snapshot :datasnapshot.getChildren())
                {
                    String data = (String) snapshot.getValue();
                    imagelist.add(data);
                }
                adapter = new Gallary_Adapter(getContext(),imagelist);
                OtherRecycler.setLayoutManager(new GridLayoutManager(getContext(),3));
                OtherRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void getConvocationImage() {
        reference.child("Intra University Competitions").addValueEventListener(new ValueEventListener() {
            List<String> imagelist = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for(DataSnapshot snapshot :datasnapshot.getChildren())
                {
                    String data = (String) snapshot.getValue();
                    imagelist.add(data);
                }
                adapter = new Gallary_Adapter(getContext(),imagelist);
                IntraCompetitionRecycler.setLayoutManager(new GridLayoutManager(getContext(),3));
                IntraCompetitionRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void getIntraCompetitionImage() {
        reference.child("Convocation").addValueEventListener(new ValueEventListener() {
            List<String> imagelist = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for(DataSnapshot snapshot :datasnapshot.getChildren())
                {
                    String data = (String) snapshot.getValue();
                    imagelist.add(data);
                }
                adapter = new Gallary_Adapter(getContext(),imagelist);
                ConvocationRecycler.setLayoutManager(new GridLayoutManager(getContext(),3));
                ConvocationRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}