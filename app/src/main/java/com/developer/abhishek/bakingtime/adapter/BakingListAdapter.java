package com.developer.abhishek.bakingtime.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developer.abhishek.bakingtime.R;
import com.developer.abhishek.bakingtime.model.BakingListModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BakingListAdapter  extends RecyclerView.Adapter<BakingListAdapter.BakingListCustomAdapter>{

    private final List<BakingListModel> bakingListList;

    public BakingListAdapter(List<BakingListModel> bakingListList) {
        this.bakingListList = bakingListList;
    }

    @NonNull
    @Override
    public BakingListCustomAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.baking_list_activity_item,parent,false);
        return new BakingListCustomAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BakingListCustomAdapter holder, int position) {
        if(position<bakingListList.size()){
            holder.recipeName.setText(bakingListList.get(position).getName());
            holder.serving.setText(String.valueOf(bakingListList.get(position).getServings()));
            holder.noOfIngredients.setText(String.valueOf(bakingListList.get(position).getIngredients().size()));
            holder.steps.setText(String.valueOf(bakingListList.get(position).getSteps().size()));
        }
    }

    @Override
    public int getItemCount() {
        return bakingListList.size();
    }

    public class BakingListCustomAdapter extends RecyclerView.ViewHolder{

        @BindView(R.id.recipeNameTvAtHP)
        TextView recipeName;
        @BindView(R.id.servingTvAtHP)
        TextView serving;
        @BindView(R.id.noOfStepsTvAtHP)
        TextView steps;
        @BindView(R.id.noOfIngredientTvAtHP)
        TextView noOfIngredients;

        private BakingListCustomAdapter(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
