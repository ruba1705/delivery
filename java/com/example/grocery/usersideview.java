package com.example.grocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocery.User.UserDashboard;
import com.example.grocery.databinding.ActivityAdminsideviewBinding;
import com.example.grocery.databinding.ActivityUsersidesearchBinding;
import com.example.grocery.databinding.ActivityUsersideviewBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class usersideview extends AppCompatActivity {
    ActivityUsersideviewBinding binding;
    DatabaseReference databaseReference;
    Button update,delete;
    TextView cp,it, Shipped, out,location,userViewName;
    ImageView backBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsersideviewBinding.inflate(getLayoutInflater());
        Intent intent = getIntent();
        final String name = intent.getStringExtra("mykey");
        setContentView(binding.getRoot());
        backBtn=findViewById(R.id.back_pressed);
        userViewName=findViewById(R.id.userViewName);
        delete=findViewById(R.id.delete);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
      //  spinner = findViewById(R.id.Rname1);
        userID = fAuth.getCurrentUser().getUid();
        readData(name);
        binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDate(name);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usersideview.super.onBackPressed();
            }
        });
    }

    private void deleteDate(String name)
    {
        databaseReference= FirebaseDatabase.getInstance().getReference("placeorders");
        databaseReference.child(name).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(usersideview.this,"Successfully canceled",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(usersideview.this,"Failed to delete",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void readData(String name) {
        databaseReference= FirebaseDatabase.getInstance().getReference("placeorders");
        databaseReference.child(name).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful())
                {
                    if(task.getResult().exists())
                    {
                        Toast.makeText(usersideview.this,"Successfully read data",Toast.LENGTH_SHORT).show();
                        DataSnapshot dataSnapshot=task.getResult();
                        String location=String.valueOf(dataSnapshot.child("location").getValue());
                        String checkpoint=String.valueOf(dataSnapshot.child("checkpoint").getValue());
                        String ordered=String.valueOf(dataSnapshot.child("ordered").getValue());
                        String shipped=String.valueOf(dataSnapshot.child("shipped").getValue());
                        String intransit=String.valueOf(dataSnapshot.child("intransit").getValue());
                        String out_for_delivery=String.valueOf(dataSnapshot.child("out_for_delivery").getValue());
                        String tcost=String.valueOf(dataSnapshot.child("tprice").getValue());
                        binding.textView6.setText(checkpoint);
                        binding.textView7.setText(ordered);
                        binding.textView8.setText(intransit);
                        binding.textView9.setText(shipped);
                        binding.textView10.setText(out_for_delivery);
                        binding.tcosts.setText(tcost);
                    }
                    else
                    {
                        Toast.makeText(usersideview.this,"No orders",Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(usersideview.this, UserDashboard.class);
                        startActivity(intent);
                    }
                }
                else
                {
                    Toast.makeText(usersideview.this,"Failed to retrieve data",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}