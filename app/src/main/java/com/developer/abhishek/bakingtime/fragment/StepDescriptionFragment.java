package com.developer.abhishek.bakingtime.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.abhishek.bakingtime.R;
import com.developer.abhishek.bakingtime.model.Ingredients;
import com.developer.abhishek.bakingtime.model.Steps;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDescriptionFragment extends Fragment {

    @BindView(R.id.descriptionHeadAtDA)
    TextView head;
    @BindView(R.id.descriptionTextAtDA)
    TextView text;
    @BindView(R.id.exoPlayerAtDA)
    ImageView imageView;

    private Steps steps;
    private List<Ingredients> ingredients;
    private boolean flag;

    public void setSteps(Steps steps) {
        this.steps = steps;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public StepDescriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_description, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(flag){
            //      INGREDIENTS
            imageView.setVisibility(View.GONE);
            head.setText("Ingredients : ");
            String str = "";
            for(int i=0;i<ingredients.size();i++){
                str += ingredients.get(i).getIngredient()+"   "+ingredients.get(i).getQuantity()+"  "+ingredients.get(i).getMeasure()+"\n";
            }
            text.setText(str);
        }else{
            //      STEPS
            imageView.setVisibility(View.VISIBLE);
            head.setText(steps.getShortDescription());
            text.setText(steps.getDescription());

        }
    }
}
