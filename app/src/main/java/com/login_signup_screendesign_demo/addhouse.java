package com.login_signup_screendesign_demo;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import model.Rent;

public class addhouse extends AppCompatActivity implements View.OnClickListener {

    private Uri imageuri;
    String img_url;

    private final int PICK_IMAGE_REQUEST = 1;
    private ProgressBar mProgressBar;
    private StorageReference mStorageRef;


    private EditText etTitle;
    private EditText etLocation;
    private EditText etRentFee;
    private Spinner spinnerPeriod;
    private EditText etAddress;
    private EditText etDescription;
    private EditText etNumOfBeds;
    private EditText etNumOfBaths;
    private EditText echoose;
    private Button euplaod;
    private EditText etContactName;
    private EditText etContactNumber;
    private EditText etContactEmail;
    private Button btnPost;
    private ImageView mImageView;
    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addhouse);
        etTitle = findViewById(R.id.etTitle);
        etLocation = findViewById(R.id.etLocation);
        etRentFee = findViewById(R.id.etRentFee);
        spinnerPeriod = findViewById(R.id.spinnerPeriod);
        etAddress = findViewById(R.id.etAddress);
        etDescription = findViewById(R.id.etDescription);
        etNumOfBeds = findViewById(R.id.etNumOfBeds);
        etNumOfBaths = findViewById(R.id.etNumOfBaths);
        echoose = findViewById(R.id.choose);
        euplaod = findViewById(R.id.upload);
        mImageView = findViewById(R.id.image_view);
        etContactName = findViewById(R.id.etContactName);
        etContactNumber = findViewById(R.id.etContactNumber);
        etContactEmail = findViewById(R.id.etContactEmail);
        etContactEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        btnPost = findViewById(R.id.btnPost);
        mProgressBar = findViewById(R.id.progress_bar);
        btnPost.setOnClickListener(this);
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        echoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose();
            }
        });

        euplaod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });

    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void upload() {
        if (imageuri != null) {
            img_url = String.valueOf(System.currentTimeMillis());
            final StorageReference fileReference = mStorageRef.child( img_url);

            mUploadTask  = fileReference.putFile(imageuri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);

                            Toast.makeText(addhouse.this, "Upload successful", Toast.LENGTH_LONG).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(addhouse.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }







    private void choose() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageuri = data.getData();

            Picasso.get().load(imageuri).into(mImageView);
        }
    }
    @Override
    public void onClick (View v){


        switch (v.getId()) {
            case R.id.btnPost:
                checkvalidation();
                break;

        }
    }

    private void checkvalidation () {
        final String adTitle = etTitle.getText().toString().trim();
        final String adLocation = etLocation.getText().toString().trim();
        final String rentFee = etRentFee.getText().toString().trim();
        final String periodOfTime;
        if (spinnerPeriod.getSelectedItem().toString().equals("Monthly")) {
            periodOfTime = "month";
        } else {
            periodOfTime = "year";
        }
        final String address = etAddress.getText().toString().trim();
        final String description = etDescription.getText().toString().trim();
        final String beds = etNumOfBeds.getText().toString().trim();
        final String baths = etNumOfBaths.getText().toString().trim();

        final String contactName = etContactName.getText().toString().trim();
        final String contactNumber = etContactNumber.getText().toString().trim();
        final String contactEmail = etContactEmail.getText().toString().trim();



        if (adTitle.equals("") || adLocation.equals("") || rentFee.equals("") || address.equals("") ||
                description.equals("") || beds.equals("") || baths.equals("") || contactName.equals("") ||
                contactNumber.equals("") || contactEmail.equals("") || imageuri.equals("")) {
            Toast.makeText(addhouse.this, "Fill up all fields!", Toast.LENGTH_SHORT).show();
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("House");


        Rent rent = new Rent(adTitle, adLocation, address, rentFee, periodOfTime, description, Integer.parseInt(beds), Integer.parseInt(baths), contactName, contactNumber, contactEmail, img_url,"yes");
        myRef.child(address).setValue(rent);
        new AlertDialog.Builder(addhouse.this).setMessage("Your ad has posted!").show();
        Intent intent = new Intent(getApplicationContext(),ownerpage.class);
        startActivity(intent);

    }
}
