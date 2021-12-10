package com.example.grocery.listener;

import com.example.grocery.model.CartModel;

import java.util.List;

public interface ICartLoudListener {
    void onCartLoadSuccess(List<CartModel> cartModelList);
    void onCartLoadFailed(String message);
}
