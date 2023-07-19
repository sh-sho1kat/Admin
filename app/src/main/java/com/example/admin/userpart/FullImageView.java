 package com.example.admin.userpart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.admin.R;
import com.github.chrisbanes.photoview.PhotoView;

 public class FullImageView extends AppCompatActivity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image_view);

        imageView = findViewById(R.id.image_view);

        String image = getIntent().getStringExtra("image");
        Glide.with(this).load(image).into(imageView);
    }
}