package com.example.admin.adminpart.students;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewAdapter> {
    private List<StudentData> list;
    private Context context;

    public StudentAdapter(List<StudentData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public StudentAdapter.StudentViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_item_layout,parent,false);
        return new StudentViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.StudentViewAdapter holder, int position) {

        String downloadurl=null;

        StudentData item = list.get(position);
        holder.id.setText(item.getId());
        holder.name.setText(item.getName());
        holder.department.setText(item.getDepartment());
        holder.phone.setText(item.getPhone());
        holder.email.setText(item.getEmail());
        holder.gender.setText(item.getGender());
        holder.address.setText(item.getAddress());
        Picasso.get().load(item.getImage()).into(holder.imageView);

        holder.updateinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Update_Student.class);
                intent.putExtra("id",item.getId());
                intent.putExtra("name",item.getName());
                intent.putExtra("department",item.getDepartment());
                intent.putExtra("phone",item.getPhone());
                intent.putExtra("email",item.getEmail());
                intent.putExtra("gender",item.getGender());
                intent.putExtra("address",item.getAddress());
                intent.putExtra("image",item.getImage());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class StudentViewAdapter extends RecyclerView.ViewHolder {
        private ImageView imageView;
        public TextView id;
        private TextView name,department,phone,email,gender,address;
        private Button updateinfo;
        public StudentViewAdapter(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.StudentImage);
            id = itemView.findViewById(R.id.StudentID);
            name = itemView.findViewById(R.id.StudentName);
            department = itemView.findViewById(R.id.StudentDepartment);
            phone = itemView.findViewById(R.id.StudentPhone);
            email = itemView.findViewById(R.id.StudentEmail);
            gender = itemView.findViewById(R.id.StudentGender);
            address = itemView.findViewById(R.id.StudentAddress);
            updateinfo = itemView.findViewById(R.id.UpdateInfo);
        }
    }
}
