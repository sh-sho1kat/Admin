package com.example.admin.adminpart.notice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.R;
import com.example.admin.adminpart.faculty.TeacherData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewAdapter> {
    private Context context;
    private ArrayList<NoticeData> list;

    public NoticeAdapter(Context context, ArrayList<NoticeData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NoticeAdapter.NoticeViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notice_item_layout,parent,false);

        return new NoticeViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeAdapter.NoticeViewAdapter holder, int position) {
        String downloadurl = null;

        NoticeData item = list.get(position);
        holder.notice_title.setText(item.getTitle());
        holder.notice_date.setText(item.getDate());
        holder.notice_time.setText(item.getTime());
        downloadurl=item.getImage();
        Picasso.get().load(item.getImage()).into(holder.notice_image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class NoticeViewAdapter extends RecyclerView.ViewHolder {

        private ImageView notice_image;
        private TextView notice_title;
        private TextView notice_time;
        private TextView notice_date;

        public NoticeViewAdapter(@NonNull View itemView) {
            super(itemView);

            notice_image = itemView.findViewById(R.id.notice_image);
            notice_title = itemView.findViewById(R.id.notice_title);
            notice_date = itemView.findViewById(R.id.date);
            notice_time = itemView.findViewById(R.id.time);

        }
    }
}
