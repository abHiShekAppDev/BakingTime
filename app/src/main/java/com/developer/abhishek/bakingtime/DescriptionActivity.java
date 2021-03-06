package com.developer.abhishek.bakingtime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.developer.abhishek.bakingtime.fragment.StepDescriptionFragment;
import com.developer.abhishek.bakingtime.model.BakingListModel;

public class DescriptionActivity extends AppCompatActivity {

    public static final String INTENT_KEY_POSITION = "clicked_position";
    public static final String INTENT_KEY_FROM_DETAIL_ACTIVITY = "baking_list_model";

    private int position = -1;
    private BakingListModel bakingListModel;

    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        if(intent == null){
            closeOnError();
        }

        position = Integer.parseInt(intent.getStringExtra(INTENT_KEY_POSITION));
        bakingListModel = intent.getParcelableExtra(INTENT_KEY_FROM_DETAIL_ACTIVITY);
        if(position == -1 || bakingListModel == null){
            closeOnError();
        }

        getSupportActionBar().setTitle(bakingListModel.getName());

        if(savedInstanceState == null){
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
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void closeOnError(){
        finish();
        if(toast != null){
            toast.cancel();
        }
        toast = Toast.makeText(DescriptionActivity.this,getResources().getString(R.string.tryAgainError),Toast.LENGTH_SHORT);
        toast.show();
    }
}
