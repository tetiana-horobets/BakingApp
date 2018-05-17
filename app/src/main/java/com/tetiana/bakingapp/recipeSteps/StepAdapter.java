package com.tetiana.bakingapp.recipeSteps;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tetiana.bakingapp.R;
import com.tetiana.bakingapp.model.Step;

import java.util.List;


public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepHolder> {

    private List<Step> steps;
    private Context context;
    final private ListItemClickListener mOnClickListener;
    int clickedPosition;


    public StepAdapter(List<Step> steps, Context context, ListItemClickListener listener) {
        this.steps = steps;
        this.context = context;
        mOnClickListener = listener;
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    @Override
    public StepAdapter.StepHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.step_list_item, parent, false);
        return new StepAdapter.StepHolder(view);
    }

    @Override
    public void onBindViewHolder(StepAdapter.StepHolder holder, int position) {
        String number = steps.get(position).getId();
        String stepName = steps.get(position).getShortDescription();

        holder.stepNumber.setText(number);
        holder.stepText.setText(stepName);
    }

    @Override
    public int getItemCount() {
        return (steps == null) ? 0 : steps.size();
    }

    class StepHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView stepNumber;
        TextView stepText;

        StepHolder(View itemView) {
            super(itemView);
            stepNumber = itemView.findViewById(R.id.step_number);
            stepText = itemView.findViewById(R.id.step_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);

        }
    }

    public Integer getId() {
        return clickedPosition;
    }
}
