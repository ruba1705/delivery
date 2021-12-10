package com.example.grocery.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.grocery.R;

public class SetNewPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);
    }
    public void goToHomeFromSetNewPassword(View view){
        Intent intent = new Intent(getApplicationContext(), LoginUser.class);
        startActivity(intent);
    }
    public void setNewPasswordBtn(View view){
        Intent intent = new Intent(getApplicationContext(), SuccessMessage.class);
        startActivity(intent);
    }
}