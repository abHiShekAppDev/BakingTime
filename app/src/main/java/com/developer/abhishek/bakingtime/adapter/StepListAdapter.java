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

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.CustomStepListAdapter>{

    private final ArrayList<Steps> stepsList;
    private final Context context;

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
            int stepCount = position+1;
            String stepNumber = String.format(context.getResources().getString(R.string.stepNumberStr),stepCount);

            holder.stepHeading.setText(stepsList.get(position).getShortDescription());
            holder.stepNo.setText(stepNumber);
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

        private CustomStepListAdapter(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
