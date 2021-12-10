package com.example.grocery.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.grocery.R;
import com.google.android.material.textfield.TextInputLayout;

public class SignUp extends AppCompatActivity {

    ImageView backBtn;
    Button next, login;
    TextView titleText, slideText;
    TextInputLayout fullname,username,email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_sign_up);
        backBtn = findViewById(R.id.signup_back_button);
        next = findViewById(R.id.signup_next_button);
        login = findViewById(R.id.signup_login_button);
        titleText = findViewById(R.id.signup_title_text);
        slideText = findViewById(R.id.signup_slide_text);

        fullname=findViewById(R.id.signup_fullname);
        username=findViewById(R.id.signup_email);
        email=findViewById(R.id.signup_email);
        password=findViewById(R.id.signup_password);

        /*next.setOnClickListener(new View.OnClickListener() {
            String fname=fullname.getEditText().toString();
            String name=username.getEditText().toString();
            String mail=email.getEditText().toString();
            String pwd=password.getEditText().toString();
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignUp.this,SignUp2ndClass.class);
                intent.putExtra("FullName",fname);
                intent.putExtra("UserName",name);
                intent.putExtra("Email",mail);
                intent.putExtra("Password",pwd);
            }
        });*/

    }
    public void callNextSignupScreen(View view){
        if (!validateFullName() | !validateUsername() | !validateEmail() | !validatePassword()) {
            startActivity(new Intent(getApplicationContext(), SignUp2ndClass.class));
            finish();
        }


        //Intent intent = new Intent(getApplicationContext(), SignUp2ndClass.class);
        //Add Shared Animation
        /*Pair[] pairs = new Pair[5];
        pairs[0] = new Pair(backBtn, "transition_back_arrow_btn");
        pairs[1] = new Pair(next, "transition_next_btn");
        pairs[2] = new Pair(login, "transition_login_btn");
        pairs[3] = new Pair(titleText, "transition_title_text");
        pairs[4] = new Pair(slideText, "transition_slide_text");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this, pairs);
            startActivity(intent, options.toBundle());
        } else {*/
            //startActivity(intent);
        //}
    }

    public void callUserDashBoard(View view){
        startActivity(new Intent(getApplicationContext(), UserStartUpScreen.class));
        finish();
    }
    public void callLoginFromSignUp(View view) {
        startActivity(new Intent(getApplicationContext(), LoginUser.class));
        finish();
    }

    private boolean validateFullName() {
        String val = fullname.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            fullname.setError("Field can not be empty");
            return false;
        } else {
            fullname.setError(null);
            fullname.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUsername() {
        String val = username.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            username.setError("Field can not be empty");
            return false;
        } else if (val.length() > 20) {
            username.setError("Username is too large!");
            return false;
        } else if (!val.matches(checkspaces)) {
            username.setError("No White spaces are allowed!");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            email.setError("Invalid Email!");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            password.setError("Field can not be empty");
            return false;
        }  else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }


}