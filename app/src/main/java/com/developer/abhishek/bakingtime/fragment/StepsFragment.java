package com.developer.abhishek.bakingtime.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developer.abhishek.bakingtime.DetailActivity;
import com.developer.abhishek.bakingtime.HomePage;
import com.developer.abhishek.bakingtime.R;
import com.developer.abhishek.bakingtime.adapter.StepListAdapter;
import com.developer.abhishek.bakingtime.listener.RecyclerItemClickListener;
import com.developer.abhishek.bakingtime.model.Ingredients;
import com.developer.abhishek.bakingtime.model.Steps;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsFragment extends Fragment {

    private List<Steps> stepsList;
    private List<Ingredients> ingredientsList;

    @BindView(R.id.noOfIngredientTvAtDA)
    TextView noOfIngredients;
    @BindView(R.id.recyclerViewAtDA)
    RecyclerView recyclerView;
    @BindView(R.id.ingredientCardAtDA)
    CardView ingredientCard;

    public void setStepsList(List<Steps> stepsList) {
        this.stepsList = stepsList;
    }

    public void setIngredientsList(List<Ingredients> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public StepsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_steps, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(ingredientsList != null){
            noOfIngredients.setText(String.valueOf(ingredientsList.size()));
        }

        ingredientCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        }));

       if(stepsList != null){
           StepListAdapter stepListAdapter = new StepListAdapter(stepsList,getActivity());
           recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
           recyclerView.setHasFixedSize(true);
           recyclerView.setAdapter(stepListAdapter);
       }
    }
}
