package com.example.grocery.User;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.grocery.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {
    public static final String TAG="TAG";
    EditText profileFullName,profileEmail,profilePhone,profileAddress;
    ImageView backBtn,profileImageView;
    TextView name;
    Button saveBtn;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        backBtn=findViewById(R.id.back_pressed);
        profileImageView=findViewById(R.id.profile_image);
        saveBtn=findViewById(R.id.editProfileBtn);

        Intent data=getIntent();
        final String fullName=data.getStringExtra("fullName");
        String email=data.getStringExtra("email");
        String phone=data.getStringExtra("phone");
        String address=data.getStringExtra("address");


        fAuth = FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        user= fAuth.getCurrentUser();
        name=findViewById(R.id.profile_name);
        profileFullName = findViewById(R.id.editName);
        profileEmail = findViewById(R.id.editemail);
        profilePhone  =findViewById(R.id.editNumber);
        profileAddress = findViewById(R.id.editAddress);




        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditProfile.super.onBackPressed();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(profileFullName.getText().toString().isEmpty() || profileEmail.getText().toString().isEmpty() || profilePhone.getText().toString().isEmpty() ||profileAddress.getText().toString().isEmpty())
                {
                    Toast.makeText(EditProfile.this,"One or Many fields are empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                final String email=profileEmail.getText().toString();
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        DocumentReference docRef=fStore.collection("users").document(user.getUid());
                        Map<String,Object> edited = new HashMap<>();
                        edited.put("email",email);
                        edited.put("fname",profileFullName.getText().toString());
                        edited.put("phone",profilePhone.getText().toString());
                        edited.put("address",profileAddress.getText().toString());
                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),ProfilePage.class));
                                finish();
                            }
                        });
                        Toast.makeText(EditProfile.this, "Updated", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        profileEmail.setText(email);
        profileFullName.setText(fullName);
        profilePhone.setText(phone);
        profileAddress.setText(address);
        Log.d(TAG,"onCreate: "+fullName + " " + email + " " + phone + " "+ address);
    }
}

































