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

import com.example.grocery.databinding.ActivityAdminsideviewBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;

public class adminsideview extends AppCompatActivity {
    ActivityAdminsideviewBinding binding;
    DatabaseReference databaseReference;
    Button update,delete;
    ImageView backBtn;
    EditText cp;
    EditText it, Shipped, out;
TextView locan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityAdminsideviewBinding.inflate(getLayoutInflater());
        backBtn=findViewById(R.id.back_pressed);
        Intent intent = getIntent();
        final String name = intent.getStringExtra("mykey");
        setContentView(binding.getRoot());
        readData(name);
        update=findViewById(R.id.update);
        delete=findViewById(R.id.delete);

        binding.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cp=binding.check.getText().toString();
                String it=binding.intran.getText().toString();
                String Shipped=binding.Shipped.getText().toString();
                String out_for_delivery=binding.out.getText().toString();

                updateData(cp,it,Shipped,out_for_delivery,name);
            }
        });
        locan=findViewById(R.id.loc);
       /* tracklocation=findViewById(R.id.tracklocation);
        tracklocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String locn=locan.getText().toString() ;
                Intent intent = new Intent(adminsideview.this, track.class);
                intent.putExtra("mykey",locn );
                startActivity(intent);
            }
        });*/
        binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDate(name);
            }
        });
    }

    private void deleteDate(String name) {
        databaseReference= FirebaseDatabase.getInstance().getReference("placeorders");
        databaseReference.child(name).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(adminsideview.this,"Successfully Delivered",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(adminsideview.this,"Failed to delete",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateData(String cp, String it, String Shipped, String out_for_delivery, String name) {
        HashMap User=new HashMap();
        User.put("checkpoint",cp);
        User.put("intransit",it);
        User.put("shipped",Shipped);
        User.put("out_for_delivery",out_for_delivery);


        databaseReference= FirebaseDatabase.getInstance().getReference("placeorders");
        databaseReference.child(name).updateChildren(User).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(adminsideview.this,"Successfully Updated",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(adminsideview.this,"Failed to Update",Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(adminsideview.this,"Successfully read data",Toast.LENGTH_SHORT).show();
                            DataSnapshot dataSnapshot=task.getResult();
                            String location=String.valueOf(dataSnapshot.child("location").getValue());
                            String checkpoint=String.valueOf(dataSnapshot.child("checkpoint").getValue());
                            String ordered=String.valueOf(dataSnapshot.child("ordered").getValue());
                            String shipped=String.valueOf(dataSnapshot.child("shipped").getValue());
                            String intransit=String.valueOf(dataSnapshot.child("intransit").getValue());
                            String out_for_delivery=String.valueOf(dataSnapshot.child("out_for_delivery").getValue());
                            String tprice=String.valueOf(dataSnapshot.child("tprice").getValue());
                            binding.loc.setText(location);
                            binding.check.setText(checkpoint);
                            binding.order.setText(ordered);
                            binding.intran.setText(intransit);
                            binding.Shipped.setText(shipped);
                            binding.out.setText(out_for_delivery);
                            binding.tcosts.setText(tprice);

                        }
                        else
                        {
                            Toast.makeText(adminsideview.this,"User doesn't exists",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(adminsideview.this,"Failed to retrieve data",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }
