package com.developer.abhishek.bakingtime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.developer.abhishek.bakingtime.fragment.StepDescriptionFragment;
import com.developer.abhishek.bakingtime.fragment.StepsFragment;
import com.developer.abhishek.bakingtime.model.BakingListModel;

public class DetailActivity extends AppCompatActivity implements StepsFragment.onStepSelectedListener{

    public static final String INTENT_KEY_FROM_HOME_PAGE = "baking_list_model";

    private BakingListModel bakingListModel;
    private boolean twoPaneLayout = false;

    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        if(intent == null){
            closeOnError();
        }

        bakingListModel = intent.getParcelableExtra(INTENT_KEY_FROM_HOME_PAGE);
        if(bakingListModel == null){
            closeOnError();
        }

        getSupportActionBar().setTitle(bakingListModel.getName());

        if(findViewById(R.id.stepListFrameLayoutAtDA) == null){
            twoPaneLayout = false;
            if(savedInstanceState == null){
                StepsFragment stepsFragment = new StepsFragment();
                stepsFragment.setIngredientsList(bakingListModel.getIngredients());
                stepsFragment.setStepsList(bakingListModel.getSteps());
                getSupportFragmentManager().beginTransaction().add(R.id.frameLayoutAtDA,stepsFragment).commit();
            }
        }else{
            twoPaneLayout = true;
            if(savedInstanceState == null){
                StepsFragment stepsFragment = new StepsFragment();
                stepsFragment.setIngredientsList(bakingListModel.getIngredients());
                stepsFragment.setStepsList(bakingListModel.getSteps());
                getSupportFragmentManager().beginTransaction().add(R.id.stepListFrameLayoutAtDA,stepsFragment).commit();

                StepDescriptionFragment stepDescriptionFragment = new StepDescriptionFragment();
                stepDescriptionFragment.setFlag(true);
                stepDescriptionFragment.setIngredients(bakingListModel.getIngredients());
                stepDescriptionFragment.setTwoPane(twoPaneLayout);
                getSupportFragmentManager().beginTransaction().add(R.id.stepDescriptionFrameLayoutAtDA,stepDescriptionFragment).commit();
            }
        }
    }

    @Override
    public void onStepSelected(int position) {
        if(twoPaneLayout){
            if(position == 0){
                StepDescriptionFragment stepDescriptionFragment = new StepDescriptionFragment();
                stepDescriptionFragment.setFlag(true);
                stepDescriptionFragment.setIngredients(bakingListModel.getIngredients());
                stepDescriptionFragment.setTwoPane(twoPaneLayout);
                getSupportFragmentManager().beginTransaction().replace(R.id.stepDescriptionFrameLayoutAtDA,stepDescriptionFragment).commit();
            }else{
                StepDescriptionFragment stepDescriptionFragment = new StepDescriptionFragment();
                stepDescriptionFragment.setFlag(false);
                stepDescriptionFragment.setSteps(bakingListModel.getSteps().get(position-1));
                stepDescriptionFragment.setTwoPane(twoPaneLayout);
                getSupportFragmentManager().beginTransaction().replace(R.id.stepDescriptionFrameLayoutAtDA,stepDescriptionFragment).commit();
            }
        }else{
            Intent intent = new Intent(DetailActivity.this,DescriptionActivity.class);
            intent.putExtra(DescriptionActivity.INTENT_KEY_POSITION,String.valueOf(position));
            intent.putExtra(DescriptionActivity.INTENT_KEY_FROM_DETAIL_ACTIVITY,bakingListModel);
            startActivity(intent);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void closeOnError(){
        if(toast != null){
            toast.cancel();
        }
        toast = Toast.makeText(DetailActivity.this,getResources().getString(R.string.tryAgainError),Toast.LENGTH_SHORT);
        toast.show();
        finish();
    }
}
