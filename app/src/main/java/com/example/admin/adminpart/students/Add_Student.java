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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class Add_Student extends AppCompatActivity {

    private ImageView StudentImage;
    private EditText StudentID,StudentName,StudentPhone,StudentEmail,StudentAddress;
    private Spinner StudentDepartment;
    private Button addStudentButton;
    private RadioGroup radioGroup;
    private RadioButton radioButton,male,female,other;
    private int radioID;

    private final int REQ = 1;
    private Bitmap bitmap = null;
    private DatabaseReference reference,ref;
    private StorageReference storageReference;
    String downloadurl="";
    private ProgressDialog PB;
    private String id="", name="",department="",phone="",email="",address="",gender="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        StudentImage = findViewById(R.id.StudentImage);
        StudentID = findViewById(R.id.StudentID);
        StudentName = findViewById(R.id.StudentName);
        StudentPhone = findViewById(R.id.StudentPhone);
        StudentEmail = findViewById(R.id.StudentEmail);
        StudentAddress = findViewById(R.id.StudentAddress);
        addStudentButton = findViewById(R.id.addStudentButton);
        radioGroup = findViewById(R.id.radioGroupGender);
        male =findViewById(R.id.radioButtonMale);
        female =findViewById(R.id.radioButtonFemale);
        other =findViewById(R.id.radioButtonOther);
        StudentDepartment =findViewById(R.id.StudentDepartment);

        PB = new ProgressDialog(this);

        reference = FirebaseDatabase.getInstance().getReference().child("Students");
        storageReference = FirebaseStorage.getInstance().getReference().child("Students");

        String[] items = new String[]{"Select Catagory","Electrical and Electronic Engineering","Computer Science and Engineering","Civil Engineering","Mechanical Engineering","Electrical and Computer Engineering","Electronic and Telecommunication Engineering","Industrial and Production Engineering","Architecture"};
        StudentDepartment.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,items));

        StudentDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                department = StudentDepartment.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                department = "Select Catagory";
            }
        });


        StudentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallary();
            }
        });

        addStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(male.isChecked())
                {
                    gender="Male";
                }
                else if(female.isChecked())
                {
                    gender="Female";
                }
                else if(other.isChecked())
                {
                    gender="Other";
                }
                id = StudentID.getText().toString();
                name = StudentName.getText().toString();
                phone = StudentPhone.getText().toString();
                email = StudentEmail.getText().toString();
                address = StudentAddress.getText().toString();
                checkvalidation();
            }

            private void checkvalidation() {
                if(id.isEmpty())
                {
                    StudentID.setError("Error");
                    StudentID.requestFocus();
                }
                else if(name.isEmpty())
                {
                    StudentName.setError("Error");
                    StudentName.requestFocus();
                }
                else if(department.equals("Select Catagory"))
                {
                    Toast.makeText(Add_Student.this, "Please Select Department", Toast.LENGTH_SHORT).show();
                }
                else if(phone.isEmpty())
                {
                    StudentPhone.setError("Error");
                    StudentPhone.requestFocus();
                }
                else if(email.isEmpty())
                {
                    StudentEmail.setError("Error");
                    StudentEmail.requestFocus();
                }
                else if(gender.isEmpty())
                {
                    Toast.makeText(Add_Student.this, "Please Select Gender", Toast.LENGTH_SHORT).show();
                }
                else if(address.isEmpty())
                {
                    StudentAddress.setError("Error");
                    StudentAddress.requestFocus();
                }
                else if(bitmap==null)
                {
                    Toast.makeText(Add_Student.this, "Please Select Image", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    uploadImage();
                }
            }

            private void uploadData() {
                final String uniquekey = id;
                StudentData studentData = new StudentData(id,name,department,phone,email,gender,address,downloadurl,uniquekey);

                reference.child(uniquekey).setValue(studentData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Add_Student.this, "Student Added Successfully", Toast.LENGTH_SHORT).show();
                        PB.dismiss();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Add_Student.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
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
                filepath =  storageReference.child(finalimg+"jpg");
                final UploadTask uploadTask = filepath.putBytes(finalimg);
                uploadTask.addOnCompleteListener(Add_Student.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                            Toast.makeText(Add_Student.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
        StudentImage.setImageBitmap(bitmap);
    }
}