package com.login_signup_screendesign_demo;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class housedetailsforowner extends AppCompatActivity {
    private TextView tvPostTitle;
    private TextView tvPostBy;

    private TextView tvLocation;
    private ImageView ivPostImage;
    private TextView tvPostHousePrice;
    private TextView tvPostPeriod;
    private TextView tvAddress;
    private TextView tvPostDescription;
    private TextView tvNumOfBeds;
    private TextView tvNumOfBaths;
    private TextView tvContact;
    private TextView tvEmail;



    DatabaseReference myRef;


    String address="";
    FirebaseAuth auth;

    FirebaseUser House;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_housedetailsforowner);

        tvPostTitle = findViewById(R.id.tvPostTitle);
        tvPostBy = findViewById(R.id.tvPostBy);
        tvLocation = findViewById(R.id.tvLocation);
        ivPostImage = findViewById(R.id.ivPostImage);
        tvPostHousePrice = findViewById(R.id.tvPostHousePrice);
        tvAddress = findViewById(R.id.tvAddress);
        tvPostPeriod = findViewById(R.id.tvPostPeriod);
        tvPostDescription = findViewById(R.id.tvPostDescription);
        tvNumOfBeds = findViewById(R.id.tvNumOfBeds);
        tvNumOfBaths = findViewById(R.id.tvNumOfBaths);
        tvContact = findViewById(R.id.tvContact);

        auth = FirebaseAuth.getInstance();
        House = auth.getCurrentUser();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        Bundle bundle = getIntent().getExtras();
        if(bundle!= null){
            address =  bundle.getString("address");
        }


        assert address != null;
        myRef.child("House").child(address).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String title1 = dataSnapshot.child("title").getValue(String.class);
                tvPostTitle.setText("House Title: " + title1);

                String textView_STR = dataSnapshot.child("userName").getValue(String.class);
                tvPostBy.setText("Post By: " + textView_STR);

                String textView_STR1 = dataSnapshot.child("location").getValue(String.class);
                tvLocation.setText("Location: " + textView_STR1);


                FirebaseStorage firebaseStorage =FirebaseStorage.getInstance();
                StorageReference storageReference = firebaseStorage.getReference("uploads");
                storageReference.child(dataSnapshot.child("imageuri").getValue(String.class)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).fit().centerCrop().into(ivPostImage);
                    }
                });
//                String image = dataSnapshot.child("imageuri").getValue(String.class);
//                ivPostImage.setTextAlignment(Integer.parseInt("Image: " + image));

                String price = dataSnapshot.child("fee").getValue(String.class);
                tvPostHousePrice.setText("Fee: " + price);

                final String address = dataSnapshot.child("address").getValue(String.class);
                tvAddress.setText("Address: " + address);

                String period = dataSnapshot.child("period").getValue(String.class);
                tvPostPeriod.setText("Period: " + period);

                int numOfBaths = dataSnapshot.child("numOfBaths").getValue(Integer.class);
                tvNumOfBaths.setText(String.valueOf(numOfBaths));

                int numOfBeds = dataSnapshot.child("numOfBeds").getValue(Integer.class);
                tvNumOfBeds.setText(String.valueOf(numOfBeds));

                if (numOfBeds <= 1) {
                    tvNumOfBeds.setText(numOfBeds + " Bed");
                } else {
                    tvNumOfBeds.setText(numOfBeds + " Beds");
                }

                if (numOfBaths <= 1) {
                    tvNumOfBaths.setText(numOfBaths + " Bath");
                } else {
                    tvNumOfBaths.setText(numOfBaths + " Baths");
                }


                final String contact = dataSnapshot.child("contact").getValue(String.class);
                tvContact.setText("Contact: " + contact);

                String des = dataSnapshot.child("description").getValue(String.class);
                tvPostDescription.setText("Description: " + des);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void rent(View view) {
        DatabaseReference Ref = FirebaseDatabase.getInstance().getReference("House/"+address);
        Ref.child("available").setValue("no");
    }
    }

