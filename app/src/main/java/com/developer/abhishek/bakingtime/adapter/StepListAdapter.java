package com.developer.abhishek.bakingtime.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developer.abhishek.bakingtime.R;
import com.developer.abhishek.bakingtime.model.Steps;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.CustomStepListAdapter>{

    private ArrayList<Steps> stepsList;
    private Context context;

    public StepListAdapter(ArrayList<Steps> stepsList, Context context) {
        this.stepsList = stepsList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomStepListAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_list_item,parent,false);
        return new CustomStepListAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomStepListAdapter holder, int position) {
        if(position<stepsList.size()){
            holder.stepHeading.setText(stepsList.get(position).getShortDescription());
            int pos = position+1;
            holder.stepNo.setText("STEP NUMBER -> "+String.valueOf(pos));
        }
    }

    @Override
    public int getItemCount() {
        return stepsList.size();
    }

    public class CustomStepListAdapter extends RecyclerView.ViewHolder{

        @BindView(R.id.stepSubDescriptionAtDA)
        TextView stepHeading;
        @BindView(R.id.stepNoAtDA)
        TextView stepNo;

        public CustomStepListAdapter(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
