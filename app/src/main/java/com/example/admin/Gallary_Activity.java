package com.example.admin;

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
import android.widget.Spinner;
import android.widget.Toast;

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

public class Gallary_Activity extends AppCompatActivity {

    private Button ChooseFile,uploadImage;
    private ImageView showimage;
    private Spinner immageCatagory;
    private String catagory;
    private final int REQ = 1;
    private Bitmap bitmap;
    private DatabaseReference reference;
    private StorageReference storageReference;
    String downloadurl="";
    private ProgressDialog PB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallary);
        ChooseFile = findViewById(R.id.chooseFileButton);
        uploadImage = findViewById(R.id.uploadButton);
        immageCatagory = findViewById(R.id.SelectCatagory);
        showimage = findViewById(R.id.showImage);

        reference = FirebaseDatabase.getInstance().getReference().child("Gallary");
        storageReference = FirebaseStorage.getInstance().getReference().child("Gallary");

        PB = new ProgressDialog(this);

        String[] items = new String[]{"Select Catagory","Independence Day","Victory Day","Intra University Competitions","Convocation","Others"};
        immageCatagory.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,items));

        immageCatagory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                catagory = immageCatagory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                    catagory = "Select Catagory";
            }
        });


        ChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallary();
            }
        });
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bitmap==null)
                {
                    Toast.makeText(Gallary_Activity.this, "Please Select Image", Toast.LENGTH_SHORT).show();
                }
                else if(catagory.equals("Select Catagory"))
                {
                    Toast.makeText(Gallary_Activity.this, "Please Select a Catagory", Toast.LENGTH_LONG).show();
                }
                else
                {
                    uploadImage();
                }
            }


            private void uploaddata() {
                reference = reference.child(catagory);
                final String uniqueKey = reference.push().getKey();

                reference.child(uniqueKey).setValue(downloadurl).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        PB.dismiss();
                        Toast.makeText(Gallary_Activity.this, "Image Uploaded!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        PB.dismiss();
                        Toast.makeText(Gallary_Activity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
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
                uploadTask.addOnCompleteListener(Gallary_Activity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                            Toast.makeText(Gallary_Activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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