package com.example.grocery.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grocery.Common.LoginSignup.TrackActivity;
import com.example.grocery.Common.LoginSignup.UserStartUpScreen;
import com.example.grocery.R;

public class AdminTrack extends AppCompatActivity {
    ImageView backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_track);
        backBtn=findViewById(R.id.trackBackBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminTrack.super.onBackPressed();
            }
        });


    }
    public void OrderPlaced(View view) {
        //startActivity(new Intent(MainActivity.this,TrackActivity.class));
        String orderStatus="0";
        Intent intent=new Intent(AdminTrack.this, TrackActivity.class);
        intent.putExtra("orderStatus",orderStatus);
        startActivity(intent);
    }

    public void OrderConfirmed(View view) {
        String orderStatus="1";
        Intent intent=new Intent(AdminTrack.this,TrackActivity.class);
        intent.putExtra("orderStatus",orderStatus);
        startActivity(intent);
    }

    public void OrderProcessed(View view) {
        String orderStatus="2";
        Intent intent=new Intent(AdminTrack.this,TrackActivity.class);
        intent.putExtra("orderStatus",orderStatus);
        startActivity(intent);
    }

    public void OrderPickup(View view) {
        String orderStatus="3";
        Intent intent=new Intent(AdminTrack.this,TrackActivity.class);
        intent.putExtra("orderStatus",orderStatus);
        startActivity(intent);
    }
    public void callUserStartup(View view){
        Intent intent = new Intent(getApplicationContext(), UserStartUpScreen.class);
        startActivity(intent);
    }
}