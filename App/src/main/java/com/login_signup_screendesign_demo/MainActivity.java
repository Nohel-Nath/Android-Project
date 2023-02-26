package com.login_signup_screendesign_demo;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	private CheckBox c1, c2;


	private AppBarConfiguration mAppBarConfiguration;
	private final int REQ_MSG_READ=1;





	@Override
	protected void onCreate(Bundle savedInstanceState) {


		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initViews();
		setListeners();


	}

	private void initViews() {

		c1 = findViewById(R.id.ow);
		c2 = findViewById(R.id.re);


	}

	private void setListeners() {
		c1.setOnClickListener( this);
		c2.setOnClickListener( this);

	}
	public void onClick( View v) {
		switch (v.getId()) {
			case R.id.ow:
				if (c1.isChecked()) {
					finish();
					Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
					startActivity(intent);
				}
				break;


			case R.id.re:
				if (c2.isChecked()) {
					finish();
					Intent intent = new Intent(getApplicationContext(), Main3Activity.class);
					startActivity(intent);
				}
				break;




		}
	}
	}

















