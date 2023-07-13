package com.example.admin.adminpart.students;

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
import android.widget.RadioButton;
import android.widget.TextView;
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

public class Update_Student extends AppCompatActivity {

    private ImageView StudentImage;
    private TextView StudentID;

    private EditText Name,Phone,Email,Address;
    private RadioButton Male,Female,Other;
    private Button updateStudentButton,deleteStudentButton;

    String id,name,department,phone,email,address,gender,image;

    private final int REQ = 1;
    private Bitmap bitmap = null;
    private DatabaseReference reference,ref;
    private StorageReference storageReference;
    String downloadurl="";
    private ProgressDialog PB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);

        id= getIntent().getStringExtra("id");
        name= getIntent().getStringExtra("name");
        department= getIntent().getStringExtra("department");
        phone= getIntent().getStringExtra("phone");
        email= getIntent().getStringExtra("email");
        gender= getIntent().getStringExtra("gender");
        address= getIntent().getStringExtra("address");
        image= getIntent().getStringExtra("image");

        StudentID = findViewById(R.id.StudentID);
        Name = findViewById(R.id.StudentName);
        Phone = findViewById(R.id.StudentPhone);
        Email = findViewById(R.id.StudentEmail);
        Address = findViewById(R.id.StudentAddress);
        StudentImage = findViewById(R.id.StudentImage);
        Male = findViewById(R.id.radioButtonMale);
        Female = findViewById(R.id.radioButtonFemale);
        Other = findViewById(R.id.radioButtonOther);
        updateStudentButton= findViewById(R.id.updateStudentButton);
        deleteStudentButton= findViewById(R.id.deleteStudentButton);

        PB= new ProgressDialog(this);

        reference = FirebaseDatabase.getInstance().getReference().child("Students");
        storageReference = FirebaseStorage.getInstance().getReference().child("Students");

        Picasso.get().load(image).into(StudentImage);
        StudentID.setText(id);
        Name.setText(name);
        Phone.setText(phone);
        Email.setText(email);
        Address.setText(address);
        if(gender.equals("Male"))
        {
            Male.setChecked(true);
        }
        else if(gender.equals("Female"))
        {
            Female.setChecked(true);
        }
        else
        {
            Other.setChecked(true);
        }

        StudentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallary();
            }
        });

        updateStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkvalidation();
            }

            private void checkvalidation() {
                name = Name.getText().toString();
                phone = Phone.getText().toString();
                email = Email.getText().toString();
                address = Address.getText().toString();
                if(name.isEmpty())
                {
                    Name.setError("Empty");
                    Name.requestFocus();
                }
                else if(phone.isEmpty())
                {
                    Phone.setError("Empty");
                    Phone.requestFocus();
                }
                else if(email.isEmpty())
                {
                    Email.setError("Empty");
                    Email.requestFocus();
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
                final String uniquekey = id;
                StudentData studentData = new StudentData(id,name,department,phone,email,gender,address,downloadurl,uniquekey);

                reference.child(uniquekey).setValue(studentData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Update_Student.this, "Student Added Successfully", Toast.LENGTH_SHORT).show();
                        PB.dismiss();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Update_Student.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
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
                filepath =  storageReference.child(finalimg+"jpg");
                final UploadTask uploadTask = filepath.putBytes(finalimg);
                uploadTask.addOnCompleteListener(Update_Student.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                            Toast.makeText(Update_Student.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            PB.dismiss();
                        }
                    }
                });
            }
        });
        deleteStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child(id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Update_Student.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Update_Student.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
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
        StudentImage.setImageBitmap(bitmap);
    }
}