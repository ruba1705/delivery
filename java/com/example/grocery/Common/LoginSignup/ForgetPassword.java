package com.example.grocery.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.grocery.R;

public class ForgetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
    }
    public void verifyPhoneNumber(View view){
        Intent intent = new Intent(getApplicationContext(), MakeSelection.class);
        startActivity(intent);
    }
    public  void callBackScreenFromForgetPassword(View view){
        Intent intent = new Intent(getApplicationContext(), LoginUser.class);
        startActivity(intent);
    }
}