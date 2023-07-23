package com.example.admin.userpart.ui.ebook;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.admin.R;
import com.example.admin.userpart.ui.gallary.Gallary_Adapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class EbookFragment extends Fragment {
    private RecyclerView ebookRecycler;
    private List<EbookData> list;
    private DatabaseReference reference;
    private EbookAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ebook, container, false);
        ebookRecycler = view.findViewById(R.id.ebookRecycler);

        reference = FirebaseDatabase.getInstance().getReference().child("Ebook");
        
        getPdfData();
        // Inflate the layout for this fragment
        return view ;
    }

    private void getPdfData() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for(DataSnapshot snapshot:dataSnapshot.getChildren() )
                {
                    EbookData data =snapshot.getValue(EbookData.class);
                    list.add(data);
                }
                adapter = new EbookAdapter(getContext(),list);
                ebookRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                ebookRecycler.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}