package com.example.admin.userpart.ui.gallary;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.admin.R;
import com.example.admin.adminpart.students.StudentAdapter;
import com.example.admin.userpart.FullImageView;
import com.example.admin.userpart.UserMainActivity;

import java.util.List;

public class Gallary_Adapter extends RecyclerView.Adapter<Gallary_Adapter.GallaryViewAdapter> {
    private Context context;
    private List<String> images;

    public Gallary_Adapter(Context context, List<String> images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public GallaryViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gallary_image,parent,false);
        return new Gallary_Adapter.GallaryViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GallaryViewAdapter holder, int position) {
        Glide.with(context).load(images.get(position)).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FullImageView.class);
                intent.putExtra("image",images.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class GallaryViewAdapter extends RecyclerView.ViewHolder {
        ImageView imageView;

        public GallaryViewAdapter(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
