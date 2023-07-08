package com.example.admin.departments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.R;
import com.example.admin.faculty.Add_Teachers;
import com.example.admin.faculty.TeacherData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

public class UpdateInformation extends AppCompatActivity {
    private EditText Information;
    private Button ClearInformation,UpdateInformation;
    private DatabaseReference reference,ref;
    private ProgressDialog PB;
    private String information;
    private String dept,message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_information);

        Information = findViewById(R.id.Information);
        ClearInformation = findViewById(R.id.ClearInformation);
        UpdateInformation = findViewById(R.id.UpdtaeInformation);

        PB = new ProgressDialog(this);

        reference = FirebaseDatabase.getInstance().getReference().child("Departments");

        ClearInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Information.getText().clear();
            }
        });

        UpdateInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateData();
            }

            private void UpdateData() {
                dept = getIntent().getStringExtra("dept");
                message = getIntent().getStringExtra("info");
                ref = reference.child(dept);
                information = Information.getText().toString();
                ref.child(message).setValue(information).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(UpdateInformation.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                        PB.dismiss();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateInformation.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        PB.dismiss();
                    }
                });
            }
        });
    }
}