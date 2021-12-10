package com.example.grocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.grocery.model.ConfirmFinalOrder4;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class adminsidesearch extends AppCompatActivity {
    EditText name;
    Button search;
    Spinner spinner;
    List<String> spin_name;
    ImageView backBtn;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminsidesearch);
        //name = findViewById(R.id.Rname);
        search = findViewById(R.id.search);
        spinner = findViewById(R.id.Rname1);
        backBtn=findViewById(R.id.back_pressed);

        databaseReference= FirebaseDatabase.getInstance().getReference();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminsidesearch.super.onBackPressed();
            }
        });
        spin_name=new ArrayList<>();
        databaseReference.child("placeorders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot childsnapshot:snapshot.getChildren()) {
                    String spinnerName = childsnapshot.child("oid").getValue(String.class);
                    spin_name.add(spinnerName);
                }
                ArrayAdapter<String>arrayAdapter =new ArrayAdapter<>(adminsidesearch.this, android.R.layout.simple_spinner_item,spin_name);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                spinner.setAdapter(arrayAdapter);

                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent= new Intent(adminsidesearch.this,adminsideview.class);
                        intent.putExtra("mykey",String.valueOf(spinner.getSelectedItem()));
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        /*search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rname = name.getText().toString();
                // txtTotal.setText("Total Price = $" + String.valueOf(overTotalPrice));
                Intent intent = new Intent(adminsidesearch.this, adminsideview.class);
                intent.putExtra("mykey", rname);
                startActivity(intent);
            }
        });*/


    }
}
























