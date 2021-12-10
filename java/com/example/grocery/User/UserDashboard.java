package com.example.grocery.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocery.CartActivity;
import com.example.grocery.Common.LoginSignup.LoginUser;
import com.example.grocery.Common.LoginSignup.UserStartUpScreen;
import com.example.grocery.R;
import com.example.grocery.adapter.MyItemAdapter;
import com.example.grocery.eventbus.MyUpdateCartEvent;
import com.example.grocery.listener.ICartLoudListener;
import com.example.grocery.listener.IItemLoudListener;
import com.example.grocery.model.CartModel;
import com.example.grocery.model.ItemModel;
import com.example.grocery.usersidesearch;
import com.example.grocery.utils.SpaceItemDecoration;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nex3z.notificationbadge.NotificationBadge;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, IItemLoudListener, ICartLoudListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuIcon;
    //private Spinner spinner,spinner2,spinner3,spinner4,spinner5;
    FirebaseAuth fAuth;

    @BindView(R.id.recycler_drink)
    RecyclerView recyclerView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;
    @BindView(R.id.badge)
    NotificationBadge badge;
    @BindView(R.id.btnCart)
    FrameLayout btnCart;



    IItemLoudListener itemLoadListener;
    ICartLoudListener cartLoadListener;

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        if(EventBus.getDefault().hasSubscriberForEvent(MyUpdateCartEvent.class))
            EventBus.getDefault().removeStickyEvent(MyUpdateCartEvent.class);
        EventBus.getDefault().unregister(this);
        super.onStop();

    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onUpdateCart(MyUpdateCartEvent event)
    {
        countCartItem();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);
        init();
        loadItemFromFirebase();
        countCartItem();
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menuIcon = findViewById(R.id.menu_icon);
        /*spinner = findViewById(R.id.spin1);
        spinner2=findViewById(R.id.spin2);
        spinner3=findViewById(R.id.spin3);
        spinner4=findViewById(R.id.spin4);
        spinner5=findViewById(R.id.spin5);

        String[] quantity=getResources().getStringArray(R.array.quantity);
        ArrayAdapter adapter = new ArrayAdapter(this,
                 android.R.layout.simple_spinner_item,quantity);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner2.setAdapter(adapter);
        spinner3.setAdapter(adapter);
        spinner4.setAdapter(adapter);
        spinner5.setAdapter(adapter);*/



        navigationDrawer();


    }

    private void loadItemFromFirebase() {
        List<ItemModel> itemModels=new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference("Drink")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for (DataSnapshot itemSnapshot:snapshot.getChildren())
                            {
                                ItemModel itemModel=itemSnapshot.getValue(ItemModel.class);
                                itemModel.setKey(itemSnapshot.getKey());
                                itemModels.add(itemModel);
                            }
                            itemLoadListener.onItemLoadSuccess(itemModels);
                        }
                        else
                            itemLoadListener.onItemLoadFailed("Can't find items");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        itemLoadListener.onItemLoadFailed(error.getMessage());
                    }
                });
    }

    private void init(){
        ButterKnife.bind(this);

        itemLoadListener = this;
        cartLoadListener = this;

        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new SpaceItemDecoration());
        btnCart.setOnClickListener(view -> startActivity(new Intent(this, CartActivity.class)));
    }

    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_profile:
                startActivity(new Intent(getApplicationContext(), ProfilePage.class));
                break;
            case R.id.nav_home:
                startActivity(new Intent(getApplicationContext(),UserDashboard.class));
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), UserDashboard.class));
                finish();
                break;
            case R.id.nav_login:
                startActivity(new Intent(getApplicationContext(),LoginUser.class));
                break;
            case R.id.nav_track:
                startActivity(new Intent(getApplicationContext(), usersidesearch.class));
                break;

        }

        return true;
    }



    public void callUserRegisterPage(View view) {
        startActivity(new Intent(getApplicationContext(), UserStartUpScreen.class));
    }


    @Override
    public void onItemLoadSuccess(List<ItemModel> itemModelList) {

        MyItemAdapter adapter=new MyItemAdapter(UserDashboard.this,itemModelList,cartLoadListener);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemLoadFailed(String message) {
        Snackbar.make(drawer_layout,message,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onCartLoadSuccess(List<CartModel> cartModelList) {
        int cartSum=0;
        for(CartModel cartModel: cartModelList)
            cartSum += cartModel.getQuantity();
        badge.setNumber(cartSum);
    }

    @Override
    public void onCartLoadFailed(String message) {
        Snackbar.make(drawer_layout,message,Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        countCartItem();
    }

    private void countCartItem() {
        List<CartModel> cartModels=new ArrayList<>();
        FirebaseDatabase
                .getInstance().getReference("Cart")
                .child("UNIQUE_USER_ID")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot cartSnapshot:snapshot.getChildren())
                        {
                            CartModel cartModel = cartSnapshot.getValue(CartModel.class);
                            cartModel.setKey(cartSnapshot.getKey());
                            cartModels.add(cartModel);
                        }
                        cartLoadListener.onCartLoadSuccess(cartModels);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        cartLoadListener.onCartLoadFailed(error.getMessage());
                    }
                });
    }
}

























