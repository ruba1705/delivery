package com.example.grocery.User;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.grocery.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ProfilePage extends AppCompatActivity {
    ImageView backBtn;
    Button updateBtn;
    public static  final String TAG="TAG";
    TextView profileFullName,profileEmail,profilePhone,profileAddress,profileName;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        backBtn=findViewById(R.id.back_pressed);
        updateBtn=findViewById(R.id.profileUpdate);
        profileFullName = findViewById(R.id.editName);
        profileEmail = findViewById(R.id.editemail);
        profilePhone  =findViewById(R.id.editNumber);
        profileAddress = findViewById(R.id.editAddress);
        profileName=findViewById(R.id.profile_name);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userID = fAuth.getCurrentUser().getUid();


        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.exists()) {
                    profileFullName.setText(value.getString("fname"));
                    profileEmail.setText(value.getString("email"));
                    profilePhone.setText(value.getString("phone"));
                    profileAddress.setText(value.getString("address"));
                    profileName.setText(value.getString("fname"));
                }else{
                    Log.d("tag","onEvent:Document do not exists");
                }


            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfilePage.super.onBackPressed();
            }
        });



        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(view.getContext(),EditProfile.class);
                i.putExtra("fullName",profileFullName.getText().toString());
                i.putExtra("email",profileEmail.getText().toString());
                i.putExtra("phone",profilePhone.getText().toString());
                i.putExtra("address",profileAddress.getText().toString());
                startActivity(i);

            }
        });
    }


























}