package com.developer.abhishek.bakingtime;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;

import com.developer.abhishek.bakingtime.adapter.BakingListAdapter;
import com.developer.abhishek.bakingtime.listener.RecyclerItemClickListener;
import com.developer.abhishek.bakingtime.model.BakingListModel;
import com.developer.abhishek.bakingtime.network.FoodViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomePage extends AppCompatActivity {

    private final String RECYCLER_STATE_SAVED_KEY = "recycler_state";

    @BindView(R.id.recyclerViewAtHP)
    RecyclerView recyclerView;
    @BindView(R.id.progressBarAtHP)
    ProgressBar progressBar;

    private int NO_OF_IMAGE = 1;
    private List<BakingListModel> bakingListModels;
    private Parcelable parcelable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);

        checkOrientation();

        if(savedInstanceState != null){
            if(savedInstanceState.containsKey(RECYCLER_STATE_SAVED_KEY)){
                parcelable = ((Bundle) savedInstanceState).getParcelable(RECYCLER_STATE_SAVED_KEY);
            }
        }else{
           if(!networkStatus()){
               showError();
           }
        }
        loadBakingItem();

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(HomePage.this,DetailActivity.class);
                if(bakingListModels.get(position) != null && bakingListModels!= null){
                    intent.putExtra(DetailActivity.INTENT_KEY_FROM_HOME_PAGE,bakingListModels.get(position));
                    startActivity(intent);
                }
            }
        }));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECYCLER_STATE_SAVED_KEY,recyclerView.getLayoutManager().onSaveInstanceState());
    }

    private boolean networkStatus(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isAvailable() && connectivityManager.getActiveNetworkInfo().isConnected());
    }

    private void checkOrientation(){
        int configuration = this.getResources().getConfiguration().orientation;
        if(configuration == Configuration.ORIENTATION_PORTRAIT){
            NO_OF_IMAGE = 1;
        }else{
            NO_OF_IMAGE = 2;
        }
    }

    private void loadBakingItem(){
        progressBar.setVisibility(View.VISIBLE);
        FoodViewModel foodViewModel = ViewModelProviders.of(this).get(FoodViewModel.class);
        foodViewModel.getAllFoodItem().observe(this, new Observer<List<BakingListModel>>() {
            @Override
            public void onChanged(@Nullable List<BakingListModel> bakingListModels) {
                setList(bakingListModels);
            }
        });
    }

    private void setList(List<BakingListModel> bakingListModel){
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        this.bakingListModels = bakingListModel;

        int resId = R.anim.recycler_animation;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);
        recyclerView.setLayoutAnimation(animation);

        BakingListAdapter listAdapter = new BakingListAdapter(bakingListModel,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, NO_OF_IMAGE));
        recyclerView.setAdapter(listAdapter);
        if(parcelable != null){
            recyclerView.getLayoutManager().onRestoreInstanceState(parcelable);
        }
    }

    private void showError(){

    }
}
