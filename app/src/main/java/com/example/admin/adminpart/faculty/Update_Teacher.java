package com.example.admin.adminpart.faculty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Update_Teacher extends AppCompatActivity {

    private ImageView imageView;
    private EditText teacherName,teacherDegree,teacherEmail;
    private Button updateTeacherButton,deleteTeacherButton;
    String name,degree,email,image,uniquekey,catagory;

    private final int REQ = 1;
    private Bitmap bitmap = null;
    private DatabaseReference reference,ref;
    private StorageReference storageReference;
    String downloadurl="";
    private ProgressDialog PB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_teacher);

        name= getIntent().getStringExtra("name");
        degree= getIntent().getStringExtra("degree");
        email= getIntent().getStringExtra("email");
        image= getIntent().getStringExtra("image");
        uniquekey = getIntent().getStringExtra("key");
        catagory = getIntent().getStringExtra("catagory");

        imageView = findViewById(R.id.teacher_image);
        teacherName = findViewById(R.id.teacher_name);
        teacherDegree = findViewById(R.id.teacher_degree);
        teacherEmail = findViewById(R.id.teacher_email);
        updateTeacherButton = findViewById(R.id.updateTeacherButton);
        deleteTeacherButton = findViewById(R.id.deleteTeacherButton);
        PB= new ProgressDialog(this);

        reference = FirebaseDatabase.getInstance().getReference().child("Faculty");
        storageReference = FirebaseStorage.getInstance().getReference().child("Faculty");

        Picasso.get().load(image).into(imageView);
        teacherName.setText(name);
        teacherDegree.setText(degree);
        teacherEmail.setText(email);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallary();
            }
        });

        updateTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();
            }

            private void checkValidation() {
                name = teacherName.getText().toString();
                degree = teacherDegree.getText().toString();
                email = teacherEmail.getText().toString();

                if(name.isEmpty())
                {
                    teacherName.setError("Empty");
                    teacherName.requestFocus();
                }
                else if(degree.isEmpty())
                {
                    teacherDegree.setError("Empty");
                    teacherDegree.requestFocus();
                }
                else if(email.isEmpty())
                {
                    teacherEmail.setError("Empty");
                    teacherEmail.requestFocus();
                }
                else if(bitmap==null)
                {
                    uploadData(image);
                }
                else
                {
                    uploadImage();
                }
            }

            private void uploadData(String downloadurl) {
                PB.setMessage("Updating...");
                PB.show();
                ref = reference.child(catagory);
                TeacherData teacherData = new TeacherData(name,degree,email,downloadurl,uniquekey);

                ref.child(uniquekey).setValue(teacherData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Update_Teacher.this, "Teacher Updated Successfully", Toast.LENGTH_SHORT).show();
                        PB.dismiss();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Update_Teacher.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        PB.dismiss();
                    }
                });
            }

            private void uploadImage() {
                PB.setMessage("Updating...");
                PB.show();
                ByteArrayOutputStream baos =new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
                byte[] finalimg = baos.toByteArray();
                final StorageReference filepath;
                filepath =  storageReference.child(catagory).child(finalimg+"jpg");
                final UploadTask uploadTask = filepath.putBytes(finalimg);
                uploadTask.addOnCompleteListener(Update_Teacher.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            downloadurl = String.valueOf(uri);
                                            uploadData(downloadurl);
                                        }
                                    });
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(Update_Teacher.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            PB.dismiss();
                        }
                    }
                });

            }
        });

        deleteTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reference.child(catagory).child(uniquekey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Update_Teacher.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Update_Teacher.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


    }

    private void openGallary()
    {
        Intent PickFile = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityIfNeeded(PickFile,REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ && resultCode == RESULT_OK)
        {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        imageView.setImageBitmap(bitmap);
    }
}