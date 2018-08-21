package com.developer.abhishek.bakingtime.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import com.developer.abhishek.bakingtime.R;
import com.developer.abhishek.bakingtime.adapter.StepListAdapter;
import com.developer.abhishek.bakingtime.listener.RecyclerItemClickListener;
import com.developer.abhishek.bakingtime.model.Ingredients;
import com.developer.abhishek.bakingtime.model.Steps;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsFragment extends Fragment {

    private final String RECYCLER_STATE_SAVED_KEY = "recycler_state";
    private final String INGREDIENTS_STATE_SAVED_KEY = "ingredients_list";
    private final String STEPS_STATE_SAVED_KEY = "steps_list";

    @BindView(R.id.noOfIngredientTvAtDA)
    TextView noOfIngredients;
    @BindView(R.id.recyclerViewAtDA)
    RecyclerView recyclerView;
    @BindView(R.id.ingredientCardAtDA)
    CardView ingredientCard;

    private ArrayList<Steps> stepsList = new ArrayList<>();
    private ArrayList<Ingredients> ingredientsList = new ArrayList<>();
    private Parcelable parcelable;

    onStepSelectedListener onStepSelectedListener;

    public interface onStepSelectedListener{
        void onStepSelected(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            onStepSelectedListener = (onStepSelectedListener) context;
        }catch (Exception e){
            throw new ClassCastException(context.toString() + " not implemented onStepSelectedListener");
        }
    }

    public StepsFragment() {
        // Required empty public constructor
    }

    public void setStepsList(List<Steps> stepsLists) {
        stepsList.addAll(stepsLists);
    }

    public void setIngredientsList(List<Ingredients> ingredientsLists) {
        ingredientsList.addAll(ingredientsLists);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            if(savedInstanceState.containsKey(RECYCLER_STATE_SAVED_KEY)){
                parcelable = savedInstanceState.getParcelable(RECYCLER_STATE_SAVED_KEY);
            }
            if(savedInstanceState.containsKey(STEPS_STATE_SAVED_KEY)){
                stepsList = savedInstanceState.getParcelableArrayList(STEPS_STATE_SAVED_KEY);
            }
            if(savedInstanceState.containsKey(INGREDIENTS_STATE_SAVED_KEY)){
                ingredientsList = savedInstanceState.getParcelableArrayList(INGREDIENTS_STATE_SAVED_KEY);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECYCLER_STATE_SAVED_KEY,recyclerView.getLayoutManager().onSaveInstanceState());
        outState.putParcelableArrayList(INGREDIENTS_STATE_SAVED_KEY,ingredientsList);
        outState.putParcelableArrayList(STEPS_STATE_SAVED_KEY,stepsList);
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
            String ingredientNumber = String.format(getActivity().getResources().getString(R.string.ingredientNumberStr),ingredientsList.size());
            noOfIngredients.setText(ingredientNumber);
        }

        if(stepsList != null){
            int resId = R.anim.recycler_animation;
            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(), resId);
            recyclerView.setLayoutAnimation(animation);

            StepListAdapter stepListAdapter = new StepListAdapter(stepsList,getActivity());
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(stepListAdapter);
            if(parcelable != null){
                recyclerView.getLayoutManager().onRestoreInstanceState(parcelable);
            }
        }

        ingredientCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStepSelectedListener.onStepSelected(0);
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                onStepSelectedListener.onStepSelected(position+1);
            }
        }));
    }
}
