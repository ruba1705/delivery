package com.example.grocery.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grocery.R;
import com.example.grocery.User.UserDashboard;
import com.example.grocery.eventbus.MyUpdateCartEvent;
import com.example.grocery.listener.ICartLoudListener;
import com.example.grocery.listener.IRecyclerViewClickListener;
import com.example.grocery.model.CartModel;
import com.example.grocery.model.ItemModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.grpc.Context;

public class MyItemAdapter extends RecyclerView.Adapter<MyItemAdapter.MyItemViewHolder> {


    private UserDashboard context;
    private List<ItemModel> itemModelList;
    private ICartLoudListener iCartLoudListener;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    public MyItemAdapter(UserDashboard context, List<ItemModel> itemModelList, ICartLoudListener iCartLoudListener) {
        this.context = context;
        this.itemModelList = itemModelList;
        this.iCartLoudListener = iCartLoudListener;
    }

    @NonNull
    @Override
    public MyItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MyItemViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_item_item,parent,false));

    }


    @Override
    public void onBindViewHolder(@NonNull MyItemViewHolder holder, int position) {
        Glide.with(context)
                .load(itemModelList.get(position).getImage())
                .into(holder.imageView);
        holder.txtPrice.setText(new StringBuilder("Rs ").append(itemModelList.get(position).getPrice()));
        holder.txtName.setText(new StringBuilder().append(itemModelList.get(position).getName()));

        holder.setListener((view, adapterPosition) -> {
            addToCart(itemModelList.get(position));
        });
    }

    private void addToCart(ItemModel itemModel) {
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userID = fAuth.getCurrentUser().getUid();
        DatabaseReference userCart = FirebaseDatabase
                .getInstance()
                .getReference("Cart")
                .child(userID);

        userCart.child(itemModel.getKey())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            CartModel cartModel = snapshot.getValue(CartModel.class);
                            cartModel.setQuantity(cartModel.getQuantity()+1);
                            Map<String,Object> updateData = new HashMap<>();
                            updateData.put("quantity",cartModel.getQuantity());
                            updateData.put("totalPrice",cartModel.getQuantity()*Float.parseFloat(cartModel.getPrice()));

                            userCart.child(itemModel.getKey())
                                    .updateChildren(updateData)
                                    .addOnSuccessListener(unused -> {
                                        iCartLoudListener.onCartLoadFailed("Add to Cart Success");
                                    })
                                    .addOnFailureListener(e -> iCartLoudListener.onCartLoadFailed(e.getMessage()));
                        }
                        else{
                            CartModel cartModel=new CartModel();
                            cartModel.setName(itemModel.getName());
                            cartModel.setImage(itemModel.getImage());
                            cartModel.setKey(itemModel.getKey());
                            cartModel.setPrice(itemModel.getPrice());
                            cartModel.setQuantity(1);
                            cartModel.setTotalPrice(Float.parseFloat(itemModel.getPrice()));
                            userCart.child(itemModel.getKey())
                                    .setValue(itemModel)
                                    .addOnSuccessListener(unused -> {
                                        iCartLoudListener.onCartLoadFailed("Add to Cart Success");
                                    })
                                    .addOnFailureListener(e -> iCartLoudListener.onCartLoadFailed(e.getMessage()));

                        }
                        EventBus.getDefault().postSticky((new MyUpdateCartEvent()));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        iCartLoudListener.onCartLoadFailed(error.getMessage());
                    }
                });
    }

    @Override
    public int getItemCount() {
        return itemModelList.size();
    }

    public class MyItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtPrice)
        TextView txtPrice;

        IRecyclerViewClickListener listener;

        public void setListener(IRecyclerViewClickListener listener){
            this.listener = listener;
        }

        private Unbinder unbinder;

        public MyItemViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder= ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onRecyclerClick(view,getAdapterPosition());
        }
    }

}

















