package com.example.grocery.model;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.grocery.Common.LoginSignup.LoginUser;
import com.example.grocery.R;
import com.example.grocery.User.UserDashboard;
import com.example.grocery.databinding.ActivityConfirmFinalOrder4Binding;
import com.example.grocery.placeorders;
import com.example.grocery.usersideview;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Random;

public class ConfirmFinalOrder4 extends AppCompatActivity {
    ActivityConfirmFinalOrder4Binding binding;
    String location;
    String rname;
    String ordered;
    String shipped;
    Random r;
    String intransit;
    String out_for_delivery;
    String checkpoint;
    FirebaseDatabase db;
    DatabaseReference reference;
    int min=100000;
    int max=999999;
    String output;
    Button placeorder;
    private Button mFireBaseBtn;
    private DatabaseReference mDatabase;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    TextView Tprice;
    EditText loc;
    EditText krish;
    EditText nam;
    TextView muruga;
TextView mid;
String uref;
    String tprice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityConfirmFinalOrder4Binding.inflate(getLayoutInflater());


        r = new Random();

        output = String.valueOf(r.nextInt((max - min) +1) + min);
      //  krish=findViewById(R.id.rname);
      //  krish.setText(output);
        //mid.setText(output);
        //mid.setText(value.getString("output"));
        setContentView(binding.getRoot());

        //setContentView(R.layout.activity_confirm_final_order4);
        Tprice=(TextView) findViewById(R.id.txtTotal);
        String[] quantity=getResources().getStringArray(R.array.quantity);
        Intent intent = getIntent();
        final String tcost = intent.getStringExtra("mykey");
        Tprice.setText(String.valueOf("Rs "+tcost));
       binding.placeOrderBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               fAuth = FirebaseAuth.getInstance();
               fStore = FirebaseFirestore.getInstance();

               userID = fAuth.getCurrentUser().getUid();
               location=binding.location.getText().toString();
               rname=output;
               checkpoint=binding.checkpoint.getText().toString();
               intransit=binding.intransit.getText().toString();
               out_for_delivery=binding.outForDelivery.getText().toString();
               shipped=binding.shipped.getText().toString();
               ordered=binding.ordered.getText().toString();
               uref=userID;
                tprice=tcost;
               if(!location.isEmpty()&&!rname.isEmpty() )
               {



                   placeorders orders=new placeorders(location, rname, ordered, shipped, intransit,out_for_delivery,checkpoint,uref,tprice);
                   db=FirebaseDatabase.getInstance();
                   reference=db.getReference("placeorders");
                 //  r = new Random();
                  // output = String.valueOf(r.nextInt((max - min) +1) + min);
                 //  reference.child(userID).child(output).setValue(orders).addOnCompleteListener(new OnCompleteListener<Void>() {
                   reference.child(output).setValue(orders).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           binding.location.setText("");
                          // binding.rname.setText("");
                           Toast.makeText(ConfirmFinalOrder4.this,"Order Placed Successfully ",Toast.LENGTH_LONG).show();
                           Cart();
                           startActivity(new Intent(getApplicationContext(), UserDashboard.class));
                       }
                   });
               }
               else
               {
                   Toast.makeText(ConfirmFinalOrder4.this,"Please enter delivery location.",Toast.LENGTH_LONG).show();
               }
           }

           private void Cart() {
               fAuth = FirebaseAuth.getInstance();
               fStore = FirebaseFirestore.getInstance();

               userID = fAuth.getCurrentUser().getUid();
               r = new Random();
               output = String.valueOf(r.nextInt((max - min) +1) + min);

               DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Cart");
               databaseReference.child(userID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {

                   }
               });
           }
       });


    }



}



















