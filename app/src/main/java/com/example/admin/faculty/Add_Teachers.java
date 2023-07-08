package com.example.admin.faculty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Add_Teachers extends AppCompatActivity {

    private ImageView teacherImage;
    private EditText teacherName,teacherDegree,teacherEmail;
    private Spinner selectcatagory;
    private Button addTeacher;

    private String catagory="Select Catagory";
    private final int REQ = 1;
    private Bitmap bitmap = null;
    private DatabaseReference reference,ref;
    private StorageReference storageReference;
    String downloadurl="";
    private ProgressDialog PB;
    private String name="",degree="",email="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teachers);
        teacherImage = findViewById(R.id.teacher_image);
        teacherName = findViewById(R.id.teacher_name);
        teacherDegree = findViewById(R.id.teacher_degree);
        teacherEmail = findViewById(R.id.teacher_email);
        selectcatagory = findViewById(R.id.SelectCatagory);
        addTeacher = findViewById(R.id.addTeacherButton);

        PB = new ProgressDialog(this);

        reference = FirebaseDatabase.getInstance().getReference().child("Faculty");
        storageReference = FirebaseStorage.getInstance().getReference().child("Faculty");

        String[] items = new String[]{"Select Catagory","Vice Chancellor","Faculty of Electrical and Computer Engineering","Faculty of Civil Engineering","Faculty of Mechanical Engineering","Faculty of Humanities"};
        selectcatagory.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,items));

        selectcatagory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                catagory = selectcatagory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                catagory = "Select Catagory";
            }
        });

        teacherImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallary();
            }
        });
        addTeacher.setOnClickListener(new View.OnClickListener() {
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
                else if(catagory.equals("Select Catagory"))
                {
                    Toast.makeText(Add_Teachers.this, "Please Select Catagory", Toast.LENGTH_SHORT).show();
                }
                else if(bitmap==null)
                {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.teacher_avatar);
                    uploadImage();
                }
                else
                {
                    uploadImage();
                }
            }

            private void uploadData() {
                ref = reference.child(catagory);
                final String uniquekey = reference.push().getKey();

                TeacherData teacherData = new TeacherData(name,degree,email,downloadurl,uniquekey);

                ref.child(uniquekey).setValue(teacherData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Add_Teachers.this, "Teacher Added Successfully", Toast.LENGTH_SHORT).show();
                        PB.dismiss();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Add_Teachers.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        PB.dismiss();
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
                filepath =  storageReference.child(catagory).child(finalimg+"jpg");
                final UploadTask uploadTask = filepath.putBytes(finalimg);
                uploadTask.addOnCompleteListener(Add_Teachers.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                            uploadData();
                                        }
                                    });
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(Add_Teachers.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            PB.dismiss();
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
        teacherImage.setImageBitmap(bitmap);
    }

}