package com.yuanbaogo.mine.order.base;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BaseRefundViewModel extends ViewModel {

    private static final String TAG = "BaseOrderViewModel";

    private final MutableLiveData<String> _searchText = new MutableLiveData<>("");
    public LiveData<String> searchText = _searchText;

    public void setSearchText(String search) {
        if (search.equals(_searchText.getValue())) {
            return;
        }
        _searchText.postValue(search);
        Log.d(TAG, "setSearchText: " + search);
    }

}