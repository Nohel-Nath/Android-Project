package com.login_signup_screendesign_demo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profileforowner extends AppCompatActivity {


 TextView textView,t1,t2,t3,t4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileforowner);
         textView= findViewById(R.id.asd);
         t1 =findViewById(R.id.b);
         t2=findViewById(R.id.c);
         t3=findViewById(R.id.d);
         t4=findViewById(R.id.e);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("owner").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                //Student post = dataSnapshot.getValue(Student.class);
                //System.out.println(post);
                String textView_STR = dataSnapshot.child("fullname").getValue(String.class);
                textView.setText("Full Name: "+textView_STR);


                String text=dataSnapshot.child("emailId").getValue(String.class);
                t1.setText("Email Id: " +text);



                String text1=dataSnapshot.child("mobile").getValue(String.class);
                t2.setText("Mobile Number: " +text1);


                String text2=dataSnapshot.child("locat").getValue(String.class);
                t3.setText("Location: " +text2);

                String text3=dataSnapshot.child("pass").getValue(String.class);
                t4.setText("Password: " +text3);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}








