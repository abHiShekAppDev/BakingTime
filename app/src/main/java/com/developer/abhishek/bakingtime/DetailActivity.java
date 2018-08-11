package com.developer.abhishek.bakingtime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.developer.abhishek.bakingtime.model.BakingListModel;

public class DetailActivity extends AppCompatActivity {

    public static final String INTENT_KEY_FROM_HOME_PAGE = "baking_list_model";

    private BakingListModel bakingListModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if(intent == null){
            closeOnError();
        }

        bakingListModel = intent.getParcelableExtra(INTENT_KEY_FROM_HOME_PAGE);
        if(bakingListModel == null){
            closeOnError();
        }


    }

    private void closeOnError(){

    }
}
