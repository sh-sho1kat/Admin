package com.example.admin.userpart.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.admin.R;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

        private ImageSlider imageSlider;
        private ArrayList<SlideModel> arrayList;
        private ImageView imagemap;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        imageSlider =view.findViewById(R.id.imageslider);
        imagemap = view.findViewById(R.id.imagemap);
        arrayList = new ArrayList<>();
        arrayList.add(new SlideModel(R.drawable.ruet1, ScaleTypes.CENTER_CROP));
        arrayList.add(new SlideModel(R.drawable.ruet2, ScaleTypes.CENTER_CROP));
        arrayList.add(new SlideModel(R.drawable.ruet3, ScaleTypes.CENTER_CROP));
        arrayList.add(new SlideModel(R.drawable.ruet4, ScaleTypes.CENTER_CROP));
        arrayList.add(new SlideModel(R.drawable.ruet5, ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(arrayList,ScaleTypes.FIT);

        imagemap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMap();
            }
        });

        // Inflate the layout for this fragment
        return view;


    }
    private void openMap() {
        Uri uri = Uri.parse("geo:0, 0?q=RUET");
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }
}