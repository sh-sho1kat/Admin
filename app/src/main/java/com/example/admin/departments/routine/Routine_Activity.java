package com.example.admin.departments.routine;

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
import com.example.admin.notice.NoticeData;
import com.example.admin.notice.Notice_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Routine_Activity extends AppCompatActivity {
    private Button ChooseFile,uploadNotice;
    private ImageView showimage;
    private EditText addtitle;
    private final int REQ = 1;
    private Bitmap bitmap;
    private DatabaseReference reference,dbref;
    private StorageReference storageReference;
    String downloadurl="";
    private String dept;
    private ProgressDialog PB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

        ChooseFile = findViewById(R.id.chooseFileButton);
        showimage = findViewById(R.id.showImage);
        addtitle = findViewById(R.id.AddTitle);
        uploadNotice = findViewById(R.id.uploadButton);
        dept = getIntent().getStringExtra("dept");

        reference = FirebaseDatabase.getInstance().getReference().child("Departments");
        storageReference = FirebaseStorage.getInstance().getReference().child("Departments");

        PB = new ProgressDialog(this);

        ChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallary();
            }
        });
        uploadNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(addtitle.getText().toString().isEmpty())
                {
                    addtitle.setError("Empty");
                    addtitle.requestFocus();
                }
                else if(bitmap==null)
                {
                    Toast.makeText(Routine_Activity.this, "No File Selected", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    uploadImage();
                }
            }

            private void uploaddata() {
                dbref = reference.child(dept).child("routine");
                final String uniquekey = reference.push().getKey();


                String title = addtitle.getText().toString();

                Calendar calForDate = Calendar.getInstance();
                SimpleDateFormat currDate = new SimpleDateFormat("dd-MM-yy");
                String date = currDate.format(calForDate.getTime());

                Calendar calForTime = Calendar.getInstance();
                SimpleDateFormat currTime = new SimpleDateFormat("hh:mm a");
                String time =currTime.format(calForTime.getTime());

                NoticeData noticeData = new NoticeData(title,downloadurl,date,time,uniquekey);

                dbref.child(uniquekey).setValue(noticeData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        PB.dismiss();
                        Toast.makeText(Routine_Activity.this, "Routine Uploaded", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        PB.dismiss();
                        Toast.makeText(Routine_Activity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            private void uploadImage() {
                PB.setMessage("Uploading...");
                PB.show();
                ByteArrayOutputStream baos =new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
                byte[] finalimg = baos.toByteArray();
                final StorageReference filepath;
                filepath =  storageReference.child(dept).child("routine").child(finalimg+"jpg");
                final UploadTask uploadTask = filepath.putBytes(finalimg);
                uploadTask.addOnCompleteListener(Routine_Activity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                            uploaddata();
                                        }
                                    });
                                }
                            });
                        }
                        else
                        {
                            PB.dismiss();
                            Toast.makeText(Routine_Activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
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
        showimage.setImageBitmap(bitmap);
    }


}