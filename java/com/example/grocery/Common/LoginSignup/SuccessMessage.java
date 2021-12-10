package com.example.grocery.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.grocery.R;

public class SuccessMessage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_message);
    }
    public void backToLogin(View view){
        Intent intent = new Intent(getApplicationContext(), LoginUser.class);
        startActivity(intent);
    }
}