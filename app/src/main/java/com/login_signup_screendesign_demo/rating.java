package com.login_signup_screendesign_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class rating extends MainActivity {

float myrating =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        final RatingBar simpleRatingBar =  findViewById(R.id.ratingbar);
        Button submitButton =  findViewById(R.id.submitbutton);
        // perform click event on button


        simpleRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                int rating =(int) v;
                String message =null;

                myrating = ratingBar.getRating();
                switch (rating) {
                    case 1:
                        message = "Sorry to hear that!";
                        break;
                    case 2:
                        message = "Not fair";
                        break;
                    case 3:
                        message = "Good enough";
                        break;
                    case 4:
                        message = "Great! Thank you";
                        break;
                    case 5:
                        message = "You are the best";
                        break;
                }

Toast.makeText(rating.this ,message,Toast.LENGTH_SHORT).show();






            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Toast.makeText(rating.this ,  "Your rating is: " + myrating,Toast.LENGTH_SHORT).show();

            }
        });
    }
    }

