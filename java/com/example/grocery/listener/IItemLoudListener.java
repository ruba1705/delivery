package com.example.grocery.listener;

import com.example.grocery.model.ItemModel;

import java.util.List;

public interface IItemLoudListener {
    void onItemLoadSuccess(List<ItemModel> itemModelList);
    void onItemLoadFailed(String message);
}
