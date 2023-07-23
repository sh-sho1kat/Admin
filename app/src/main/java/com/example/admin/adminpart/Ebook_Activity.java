package com.example.admin.adminpart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.File;
import java.util.HashMap;

public class Ebook_Activity extends AppCompatActivity {

    private Button ChooseFile,uploadfile;
    private TextView showTitle;
    private EditText addtitle;
    private CardView Visibility;
    private final int REQ = 1;
    private Uri pdfData;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    String downloadurl="";
    String PdfName = "Ok";
    private ProgressDialog PB;
    View biew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook);

        ChooseFile = findViewById(R.id.chooseFileButton);
        showTitle = findViewById(R.id.showTitle);
        addtitle = findViewById(R.id.AddTitle);
        uploadfile = findViewById(R.id.uploadButton);
        Visibility = findViewById(R.id.viewvisibility);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        PB = new ProgressDialog(this);

        ChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                biew =view;
                openGallary();
            }
        });

        uploadfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(addtitle.getText().toString().isEmpty())
                {
                    addtitle.setError("Empty");
                    addtitle.requestFocus();
                }
                else if(pdfData==null)
                {
                    Toast.makeText(Ebook_Activity.this, "No File Selected", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    uploadpdf();
                }
            }
            private void uploaddata(String downloadurl) {
                databaseReference = databaseReference.child("Ebook");
                final String uniquekey = databaseReference.push().getKey();

                HashMap data = new HashMap();
                data.put("title",addtitle.getText().toString());
                data.put("pdfurl",downloadurl);

                databaseReference.child(uniquekey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        PB.dismiss();
                        Toast.makeText(Ebook_Activity.this, "Uploaded Successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        PB.dismiss();
                        Toast.makeText(Ebook_Activity.this, "Upload Failes!!!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            private void uploadpdf() {

                PB.setTitle("Please Wait...");
                PB.setMessage("Uploading...");
                PB.show();

                StorageReference reference = storageReference.child("pdf/"+System.currentTimeMillis()+"-"+PdfName);
                reference.putFile(pdfData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isComplete());
                        Uri uri = uriTask.getResult();
                        uploaddata(String.valueOf(uri));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Ebook_Activity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        PB.dismiss();
                    }

                });

            }
        });

    }

    private void openGallary()
    {
        Intent PickFile = new Intent();
        PickFile.setType("*/*");
        PickFile.setAction(Intent.ACTION_GET_CONTENT);
        startActivityIfNeeded(PickFile,REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ && resultCode == RESULT_OK)
        {
            pdfData =data.getData();

            if(pdfData.toString().startsWith("file://"))
            {
                PdfName = new File(pdfData.toString()).getName();
            }
            else
            {
                try {
                    Cursor cursor =null;
                    cursor = Ebook_Activity.this.getContentResolver().query(pdfData,null,null,null,null);
                    if(cursor!=null&& cursor.moveToFirst())
                    {
                        int id = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                        if (id != -1) {
                            PdfName = cursor.getString(id);
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            showTitle.setText(PdfName);
            Visibility.setVisibility(biew.VISIBLE);
        }
    }
}