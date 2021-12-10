package com.example.grocery.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.grocery.R;
import com.google.android.material.textfield.TextInputLayout;

public class SignUp3rdClass extends AppCompatActivity {

    TextInputLayout phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up3rd_class);
        phoneNumber=findViewById(R.id.signup_phone_number);

        Intent intent=getIntent();
        String fname=intent.getStringExtra("FullName");
        String name=intent.getStringExtra("UserName");
        String mail=intent.getStringExtra("Email");
        String pwd=intent.getStringExtra("Password");
        String c=intent.getStringExtra("country");
        String s=intent.getStringExtra("state");
        String cit=intent.getStringExtra("city");
        String ad=intent.getStringExtra("address");
    }
    public void callVerifyOTPScreen(View view){
        //if (!validatePhoneNumber() ) {
          //  return;
        //}
        Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);

        startActivity(intent);
    }
    public void call2ndsignup(View view){
        Intent intent = new Intent(getApplicationContext(), SignUp2ndClass.class);

        startActivity(intent);
    }
    private boolean validatePhoneNumber() {
        String val = phoneNumber.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";
        if (val.isEmpty()) {
            phoneNumber.setError("Enter valid phone number");
            return false;
        } else if (!val.matches(checkspaces)) {
            phoneNumber.setError("No White spaces are allowed!");
            return false;
        } else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }
    }
}