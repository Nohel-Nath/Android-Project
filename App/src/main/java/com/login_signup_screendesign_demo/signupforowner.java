package com.login_signup_screendesign_demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signupforowner extends AppCompatActivity  implements View.OnClickListener{

    private  EditText fullName, emailid, mobileNumber, location, password, confirmPassword;
    private  TextView login;
    private  Button signUpButton;
    private  CheckBox terms_conditions;
    private  ProgressBar progressbar;
    private FirebaseAuth mAuth;



    String namepattern ="[a-zA-Z0-9 ]+";
    String emailpattern ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
    String mobilepattern ="[0-9]{11}";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupforowner);

        mAuth = FirebaseAuth.getInstance();

        initViews();
        setListeners();

    }


    private void initViews() {
        fullName = findViewById(R.id.fullName);
        emailid = findViewById(R.id.userEmailId);
        mobileNumber = findViewById(R.id.mobileNumber);
        location = findViewById(R.id.location);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);

        signUpButton = findViewById(R.id.signUpBtn);
        login = findViewById(R.id.already_user);
        terms_conditions = findViewById(R.id.terms_conditions);
        progressbar = findViewById(R.id.progressbar);

    }





    private void setListeners() {
        signUpButton.setOnClickListener( this);
        login.setOnClickListener( this);


    }





    private void checkvalidation() {
        final String fullname = fullName.getText().toString();
        final String EmailId = emailid.getText().toString();
        final String mobile = mobileNumber.getText().toString();
        final String locat = location.getText().toString();
        final String pass = password.getText().toString();
        final String conf = confirmPassword.getText().toString();
        final String any=EmailId.replace(".","");





        if (fullname.isEmpty()) {
            fullName.setError("Enter your name");
            fullName.requestFocus();
        }
        if (!fullName.getText().toString().matches(namepattern) ) {
            fullName.setError("Enter valid name");
            fullName.requestFocus();
        }


        if (EmailId.isEmpty()) {
            emailid.setError("Enter your emailId");
            emailid.requestFocus();

        }

        if (!emailid.getText().toString().matches(emailpattern)) {
            emailid.setError("Enter valid emailId");
            emailid.requestFocus();
        }
        if (mobile.isEmpty()) {
            mobileNumber.setError("Enter mobile number");
            mobileNumber.requestFocus();
        }
        if (!mobileNumber.getText().toString().matches(mobilepattern)) {
            mobileNumber.setError("Enter 11 digit valid mobile number");
            mobileNumber.requestFocus();
        }

        if (locat.isEmpty()) {
            location.setError("Enter your location");
            location.requestFocus();
        }
        if (pass.isEmpty()) {
            password.setError("Enter your password");
            password.requestFocus();
        }
        if (!password.getText().toString().matches(PASSWORD_PATTERN) && password.length() < 8) {
            password.setError("Enter 8 digit valid password");

        }
        if (conf.isEmpty()) {
            confirmPassword.setError("Enter confirm password");
            confirmPassword.requestFocus();
        }

        if (!conf.equals(pass))
            confirmPassword.setError("Both password does not match");
        confirmPassword.requestFocus();

        if(!terms_conditions.isChecked())
        {
            terms_conditions.setError("You have not accepted our terms and conditions");
            terms_conditions.requestFocus();
        }

        progressbar.setVisibility(View.VISIBLE);

        Log.d("baba", "onComplete: check");

       mAuth.createUserWithEmailAndPassword(emailid.getText().toString().trim(),password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               progressbar.setVisibility(View.GONE);

               if (!task.isSuccessful()) {
                  //
                   // Log.e("login", "onComplete: Failed=" + Objects.requireNonNull(task.getException()).getMessage());
                   Log.d("sdasda", "onComplete: failed "+task.getException());
               }


               if (task.isSuccessful()) {

                   FirebaseDatabase database = FirebaseDatabase.getInstance();
                   DatabaseReference myRef = database.getReference("owner");

                   Student student =new Student(fullname,EmailId,mobile, locat, pass,  conf);
                   myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(student);
                   Intent intent = new Intent(getApplicationContext(), ownerpage.class);
                   //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   startActivity(intent);
                   finish();

               } else {
                   if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                       Toast.makeText(getApplicationContext(), "You have already registered", Toast.LENGTH_SHORT).show();
                   } else {
                       // If sign in fails, display a message to the user.
                       Toast.makeText(getApplicationContext(), "Register is not successful", Toast.LENGTH_SHORT).show();
                   }

                   // ...
               }
           }

       });



       /* mAuth.createUserWithEmailAndPassword(emailId, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressbar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            finish();
                            Intent intent =new Intent(getApplicationContext(),newactivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException)
                            {
                                Toast.makeText(getApplicationContext(),"You have already registered",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Error: "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }

                        }

                        // ...
                    }
                });
                */


    }


    @Override
    public void onClick(View v) {


        switch (v.getId())
        {
            case R.id.signUpBtn:
                checkvalidation();
                break;

            case R.id.already_user:
                Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                break;


        }

    }
}

