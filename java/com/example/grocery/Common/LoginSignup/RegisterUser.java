package com.example.grocery.Common.LoginSignup;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.grocery.R;
import com.example.grocery.User.UserDashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;
import java.util.Map;

public class RegisterUser extends AppCompatActivity {
    TextInputLayout fullname,country,state,city,address,email,password,cpassword,phone;
    CountryCodePicker countryCodePicker;
    FirebaseAuth fAuth;
    Button registerBtn,loginBtn;
    FirebaseFirestore fstore;
    String userId;
    public static final String TAG="TAG";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String Pattern1 = "[a-zA-Z]{3,}";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_user);

        fullname=findViewById(R.id.signup_fullname);
        country=findViewById(R.id.countryEt);
        state=findViewById(R.id.stateEt);
        city=findViewById(R.id.cityEt);
        address=findViewById(R.id.addressEt);
        email=findViewById(R.id.signup_email);
        password=findViewById(R.id.signup_password);
        cpassword=findViewById(R.id.signup_Cpassword);
        countryCodePicker=findViewById(R.id.country_code_picker);
        loginBtn=findViewById(R.id.signup_login_button);
        registerBtn=findViewById(R.id.signup_next_button);
        phone=findViewById(R.id.signup_phone_number);

        fAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();

        if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),UserDashboard.class));
            finish();
        }

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _email=email.getEditText().getText().toString().trim();
                String _password=password.getEditText().getText().toString().trim();
                String _cpassword=cpassword.getEditText().getText().toString().trim();
                String _fullName=fullname.getEditText().getText().toString().trim();
                String _country=country.getEditText().getText().toString().trim();
                String _state=state.getEditText().getText().toString().trim();
                String _city=city.getEditText().getText().toString().trim();
                String _address=address.getEditText().getText().toString().trim();
                String _phone=phone.getEditText().getText().toString().trim();

                if(_email.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Enter email address",Toast.LENGTH_SHORT).show();
                }else {
                    if (_email.matches(emailPattern)) {
                        Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
                    }
                }
                if(_fullName.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter Full Name",Toast.LENGTH_SHORT).show();
                }else {
                    if (_fullName.matches(Pattern1)) {
                        Toast.makeText(getApplicationContext(),"valid Full name",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),"Invalid Full name", Toast.LENGTH_SHORT).show();
                    }
                }
                if(_country.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter Country",Toast.LENGTH_SHORT).show();
                }else {
                    if (_country.matches(Pattern1)) {
                        Toast.makeText(getApplicationContext(),"valid Country",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),"Invalid Country", Toast.LENGTH_SHORT).show();
                    }
                }
                if(_city.isEmpty()){

                    Toast.makeText(getApplicationContext(),"Enter City",Toast.LENGTH_SHORT).show();
                }else {
                    if (_city.matches(Pattern1)) {
                        Toast.makeText(getApplicationContext(),"valid City",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),"Invalid City", Toast.LENGTH_SHORT).show();
                    }
                }
                if(_state.isEmpty()){

                    Toast.makeText(getApplicationContext(),"Enter State",Toast.LENGTH_SHORT).show();
                }else {
                    if (_state.matches(Pattern1)) {
                        Toast.makeText(getApplicationContext(),"valid State",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),"Invalid State", Toast.LENGTH_SHORT).show();
                    }
                }

                if(TextUtils.isEmpty(_password)){
                    password.setError("Password is required");
                    return;
                }
                if(_password.length()<6){
                    password.setError("Password must be >=6 characters");
                    return;
                }
                if(_password.isEmpty() || _cpassword.isEmpty()){

                    Toast.makeText(getApplicationContext(),"Enter Password",Toast.LENGTH_SHORT).show();
                }
                    else if (!_password.equals(_cpassword))
                    {
                        Toast.makeText(RegisterUser.this, "Password do not match ,Please enter same password !", Toast.LENGTH_SHORT).show();
                    }

                fAuth.createUserWithEmailAndPassword(_email,_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterUser.this,"User Created..",Toast.LENGTH_SHORT).show();
                            userId = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fstore.collection("users").document(userId);
                            Map<String,Object> user=new HashMap<>();
                            user.put("fname",_fullName);
                            user.put("email",_email);
                            user.put("country",_country);
                            user.put("state",_state);
                            user.put("city",_city);
                            user.put("address",_address);
                            user.put("phone",_phone);
                            user.put("password",_password);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG,"onSuccess: user profile is created for "+userId);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,"onFailure: "+e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), LoginUser.class));
                        }
                        else{
                          Toast.makeText(RegisterUser.this,"Error ! "+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });




    }
    public void callUserDashBoard(View view){
        startActivity(new Intent(getApplicationContext(), UserStartUpScreen.class));
        finish();
    }
    public void callLoginFromSignUp(View view) {
        startActivity(new Intent(getApplicationContext(), LoginUser.class));
        finish();
    }
    public void callNextSignupScreen(View view){

      /*  String _getUserEnteredPhoneNumber = phone.getEditText().getText().toString().trim();
        String _phoneNo="+"+countryCodePicker.getFullNumber()+_getUserEnteredPhoneNumber;
        String _fullname=fullname.getEditText().getText().toString().trim();
        String _country=country.getEditText().getText().toString().trim();
        String _state=state.getEditText().getText().toString().trim();
        String _city=city.getEditText().getText().toString().trim();
        String _address=address.getEditText().getText().toString().trim();
        String _email=email.getEditText().getText().toString().trim();
        String _password=password.getEditText().getText().toString().trim();
        String _cpassword=cpassword.getEditText().getText().toString().trim();*/

        //Intent intent=new Intent(getApplicationContext(),VerifyOTP.class);



        /*intent.putExtra("fullname",_fullname);
        intent.putExtra("phoneNo",_phoneNo);
        intent.putExtra("password",_password);
        intent.putExtra("cpassword",_cpassword);
        intent.putExtra("country",_country);
        intent.putExtra("state",_state);
        intent.putExtra("address",_address);
        intent.putExtra("email",_email);*/
        //storeNewUsersData();
    }

    /*private void storeNewUsersData() {

        FirebaseDatabase rootNode=FirebaseDatabase.getInstance();
        DatabaseReference reference=rootNode.getReference("Users");
        reference.setValue("First record!");
    }*/
    /*public void gpsfunc(View view){
        if (checkLocationPermission()) {
            detectLocation();
        } else {
            requestLocationPermission();
        }
    }*/





}
























