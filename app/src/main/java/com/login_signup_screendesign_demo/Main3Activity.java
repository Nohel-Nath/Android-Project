package com.login_signup_screendesign_demo;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

public class Main3Activity extends AppCompatActivity  {
    private EditText emailid, password;
    private  Button loginButton;
    private  TextView forgotPassword1,t1;
    private  CheckBox show_hide_password;


    private  ProgressBar progressbar;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        mAuth = FirebaseAuth.getInstance();

        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(this,renterpage.class));
            finish();
        }
        initViews();
        setListeners();

        progressbar.setVisibility(View.INVISIBLE);
        forgotPassword1 =findViewById(R.id.forgot_password);
        t1= findViewById(R.id.createAccount);

        forgotPassword1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),forget.class);
                startActivity(intent);
            }
        });

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),signupforrenter.class);
                startActivity(intent);

            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressbar.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.INVISIBLE);

                final String mail = emailid.getText().toString();
                final String Password = password.getText().toString();

                if (mail.isEmpty() || Password.isEmpty()) {
                    showMessage("Please Verify All Field");
                    loginButton.setVisibility(View.VISIBLE);
                    progressbar.setVisibility(View.INVISIBLE);
                } else {
                    signIn(mail, Password);
                }


            }
            private void signIn(String mail, String password) {


                mAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()) {

                            progressbar.setVisibility(View.INVISIBLE);
                            loginButton.setVisibility(View.VISIBLE);
                            updateUI();

                        } else {
                            showMessage(task.getException().getMessage());
                            loginButton.setVisibility(View.VISIBLE);
                            progressbar.setVisibility(View.INVISIBLE);
                        }


                    }
                });
            }


            private void updateUI() {
                Intent intent =new Intent(getApplicationContext(),renterpage.class);
                startActivity(intent);
                finish();

            }

            private void showMessage(String text) {

                Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();
            }


        })
        ;}







    private void initViews()
    {
        emailid =  findViewById(R.id.login_emailid);
        password =  findViewById(R.id.login_password);
        loginButton =  findViewById(R.id.loginBtn);

        show_hide_password =  findViewById(R.id.show_hide_password);

        progressbar =findViewById(R.id.progressbar);

    }

    private void setListeners() {



        show_hide_password
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                    public void onCheckedChanged(CompoundButton button,
                                                 boolean isChecked) {

                        // If it is checkec then show password else hide
                        // password
                        if (isChecked) {

                            show_hide_password.setText(R.string.hide_pwd);// change
                            // checkbox
                            // text

                            password.setInputType(InputType.TYPE_CLASS_TEXT);
                            password.setTransformationMethod(HideReturnsTransformationMethod
                                    .getInstance());// show password
                        } else {
                            show_hide_password.setText(R.string.show_pwd);// change
                            // checkbox
                            // text

                            password.setInputType(InputType.TYPE_CLASS_TEXT
                                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            password.setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());// hide password

                        }

                    }
                });
    }



}







