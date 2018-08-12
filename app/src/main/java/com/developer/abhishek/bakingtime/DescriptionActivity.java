package com.developer.abhishek.bakingtime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.developer.abhishek.bakingtime.fragment.StepDescriptionFragment;
import com.developer.abhishek.bakingtime.model.BakingListModel;

public class DescriptionActivity extends AppCompatActivity {

    public static final String INTENT_KEY_POSITION = "clicked_position";
    public static final String INTENT_KEY_FROM_DETAIL_ACTIVITY = "baking_list_model";

    private int position = -1;
    private BakingListModel bakingListModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        Intent intent = getIntent();
        if(intent == null){
            closeOnError();
        }

        position = Integer.parseInt(intent.getStringExtra(INTENT_KEY_POSITION));
        bakingListModel = intent.getParcelableExtra(INTENT_KEY_FROM_DETAIL_ACTIVITY);
        if(position == -1 || bakingListModel == null){
            closeOnError();
        }

        if(position == 0){
            StepDescriptionFragment stepDescriptionFragment = new StepDescriptionFragment();
            stepDescriptionFragment.setFlag(true);
            stepDescriptionFragment.setIngredients(bakingListModel.getIngredients());
            getSupportFragmentManager().beginTransaction().add(R.id.frameLayoutAtDESA,stepDescriptionFragment).commit();
        }else{
            StepDescriptionFragment stepDescriptionFragment = new StepDescriptionFragment();
            stepDescriptionFragment.setFlag(false);
            stepDescriptionFragment.setSteps(bakingListModel.getSteps().get(position-1));
            getSupportFragmentManager().beginTransaction().add(R.id.frameLayoutAtDESA,stepDescriptionFragment).commit();
        }
    }

    private void closeOnError(){
        finish();
    }
}
