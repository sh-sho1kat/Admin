package com.example.admin.faculty;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewAdapter>{
    private List<TeacherData> list;
    private Context context;
    private String catagory;

    public TeacherAdapter(List<TeacherData> list,String catagory, Context context) {
        this.list = list;
        this.context = context;
        this.catagory = catagory;
    }

    @NonNull
    @Override
    public TeacherViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.faculty_item_layout,parent,false);

        return new TeacherViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewAdapter holder, int position) {

        String downloadurl = null;

        TeacherData item = list.get(position);
        holder.name.setText(item.getName());
        holder.degree.setText(item.getDegree());
        holder.email.setText(item.getEmail());
        downloadurl=item.getImage();
        Picasso.get().load(item.getImage()).into(holder.imageView);

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,Update_Teacher.class);
                intent.putExtra("name",item.getName());
                intent.putExtra("degree",item.getDegree());
                intent.putExtra("email",item.getEmail());
                intent.putExtra("image",item.getImage());
                intent.putExtra("key",item.getKey());
                intent.putExtra("catagory",catagory);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TeacherViewAdapter extends RecyclerView.ViewHolder {

        private TextView name,degree,email;
        private Button update;
        private ImageView imageView;


        public TeacherViewAdapter(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.TeacherName);
            degree = itemView.findViewById(R.id.TeacherDegree);
            email = itemView.findViewById(R.id.TeacherEmail);
            imageView = itemView.findViewById(R.id.TeacherImage);
            update = itemView.findViewById(R.id.UpdateInfo);

        }
    }

}
