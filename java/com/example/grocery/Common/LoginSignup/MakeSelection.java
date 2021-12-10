package com.example.grocery.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.grocery.R;

public class MakeSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_selection);
    }
    public void callOTPScreenFromMakeSelection(View view){
        Intent intent = new Intent(getApplicationContext(), SetNewPassword.class);

        startActivity(intent);
    }
    public void callBackScreenFromMakeSelection(View view){
        Intent intent = new Intent(getApplicationContext(), ForgetPassword.class);

        startActivity(intent);
    }
}