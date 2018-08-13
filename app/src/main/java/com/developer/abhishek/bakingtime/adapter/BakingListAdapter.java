package com.developer.abhishek.bakingtime.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.abhishek.bakingtime.R;
import com.developer.abhishek.bakingtime.model.BakingListModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BakingListAdapter  extends RecyclerView.Adapter<BakingListAdapter.BakingListCustomAdapter>{

    private List<BakingListModel> bakingListList;
    private Context context;

    public BakingListAdapter(List<BakingListModel> bakingListList, Context context) {
        this.bakingListList = bakingListList;
        this.context = context;
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
            holder.serving.setText("For "+bakingListList.get(position).getServings()+" people");
            holder.noOfIngredients.setText(String.valueOf(bakingListList.get(position).getIngredients().size()));
            holder.steps.setText(String.valueOf(bakingListList.get(position).getSteps().size()));

            //  Using a image instead of place holder
            // TODO Adding vector image
            int myImagePos = position%4;
            if(myImagePos == 0){
                Picasso.get().load(R.drawable.pie).into(holder.recipeImage);
            }else if(myImagePos == 1){
                Picasso.get().load(R.drawable.brownies).into(holder.recipeImage);
            }else if(myImagePos == 2){
                Picasso.get().load(R.drawable.yellowcake).into(holder.recipeImage);
            }else if(myImagePos == 3){
                Picasso.get().load(R.drawable.cheesecake).into(holder.recipeImage);
            }
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
        @BindView(R.id.bakingListIvAtHP)
        ImageView recipeImage;

        public BakingListCustomAdapter(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
