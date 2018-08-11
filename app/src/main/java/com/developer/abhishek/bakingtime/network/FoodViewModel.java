package com.developer.abhishek.bakingtime.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.developer.abhishek.bakingtime.model.BakingListModel;

import java.util.List;

public class FoodViewModel extends ViewModel {

    private MutableLiveData<List<BakingListModel>> bakingModelMutableLiveData;
    private ApiRepository apiRepository = new ApiRepository();

    public LiveData<List<BakingListModel>> getAllFoodItem(){
        if(bakingModelMutableLiveData == null){
            bakingModelMutableLiveData = apiRepository.loadAllFood();
        }
        return bakingModelMutableLiveData;
    }
}
