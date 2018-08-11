package com.developer.abhishek.bakingtime.network;

import android.arch.lifecycle.MutableLiveData;

import com.developer.abhishek.bakingtime.model.BakingListModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiRepository {

    private ApiInterface apiInterface = BuildUrl.getRetrofit().create(ApiInterface.class);

    public MutableLiveData<List<BakingListModel>> loadAllFood(){
        final MutableLiveData<List<BakingListModel>> bakingModelMutableLiveData = new MutableLiveData<>();
        apiInterface.getAllFood().enqueue(new Callback<List<BakingListModel>>() {
            @Override
            public void onResponse(Call<List<BakingListModel>> call, Response<List<BakingListModel>> response) {
                bakingModelMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<BakingListModel>> call, Throwable t) {

            }
        });
        return bakingModelMutableLiveData;
    }
}
