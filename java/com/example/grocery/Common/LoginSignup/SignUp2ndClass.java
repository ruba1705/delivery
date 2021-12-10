package com.example.grocery.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.backup.FullBackupDataOutput;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocery.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Locale;

public class SignUp2ndClass extends AppCompatActivity {


    ImageView backBtn;
    Button next, login;
    TextView titleText, slideText;

    TextInputLayout country,state,city,address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up2nd_class);

        backBtn = findViewById(R.id.signup_back_button);
        next = findViewById(R.id.signup_next_button);
        login = findViewById(R.id.signup_login_button);
        titleText = findViewById(R.id.signup_title_text);
        slideText = findViewById(R.id.signup_slide_text);
        country=findViewById(R.id.countryEt);
        state=findViewById(R.id.stateEt);
        city=findViewById(R.id.cityEt);
        address=findViewById(R.id.addressEt);

        Intent intent=getIntent();
        String fname=intent.getStringExtra("FullName");
        String name=intent.getStringExtra("UserName");
        String mail=intent.getStringExtra("Email");
        String pwd=intent.getStringExtra("Password");

        next.setOnClickListener(new View.OnClickListener() {
            String c=country.getEditText().toString();
            String s=state.getEditText().toString();
            String cit=city.getEditText().toString();
            String ad=address.getEditText().toString();

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignUp2ndClass.this,SignUp3rdClass.class);
                intent.putExtra("country",c);
                intent.putExtra("state",s);
                intent.putExtra("city",cit);
                intent.putExtra("address",ad);
                intent.putExtra("FullName",fname);
                intent.putExtra("UserName",name);
                intent.putExtra("Email",mail);
                intent.putExtra("Password",pwd);
            }
        });
    }



    public void call3rdSignupScreen(View view) {
        //if (!validateCountry() | !validateAddress() | !validateCity() | !validateState()) {
         //   return;
       // }

        Intent intent = new Intent(getApplicationContext(), SignUp3rdClass.class);
        startActivity(intent);

        //Add Transition and call next activity
        /*Pair[] pairs = new Pair[5];
        pairs[0] = new Pair(backBtn, "transition_back_arrow_btn");
        pairs[1] = new Pair(next, "transition_next_btn");
        pairs[2] = new Pair(login, "transition_login_btn");
        pairs[3] = new Pair(titleText, "transition_title_text");
        pairs[4] = new Pair(slideText, "transition_slide_text");


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp2ndClass.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
*/

    }
    public void call1signup(View view){
        Intent intent = new Intent(getApplicationContext(), SignUp.class);
        startActivity(intent);
    }

    private boolean validateCountry() {
        String val = country.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            country.setError("Field can not be empty");
            return false;
        } else {
            country.setError(null);
            country.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateState() {
        String val = state.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            state.setError("Field can not be empty");
            return false;
        } else {
            state.setError(null);
            state.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateCity() {
        String val = city.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            city.setError("Field can not be empty");
            return false;
        } else {
            city.setError(null);
            city.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateAddress() {
        String val = address.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            address.setError("Field can not be empty");
            return false;
        } else {
            address.setError(null);
            address.setErrorEnabled(false);
            return true;
        }
    }
}