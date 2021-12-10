package com.example.grocery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.grocery.databinding.ActivityAdminsideviewBinding;
import com.google.firebase.database.DatabaseReference;

public class usertrack extends AppCompatActivity {
    ActivityAdminsideviewBinding binding;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_usertrack);
    }
}