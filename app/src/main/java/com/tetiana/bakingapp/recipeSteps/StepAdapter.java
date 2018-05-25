package com.tetiana.bakingapp.recipeSteps;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tetiana.bakingapp.R;
import com.tetiana.bakingapp.model.Recipe;
import com.tetiana.bakingapp.model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepHolder> {

    private List<Step> recipe;
    private Context context;
    final private ListItemClickListener mOnClickListener;
    private int clickedPosition;

    StepAdapter(List<Step> recipe, Context context, ListItemClickListener listener) {
        this.recipe = recipe;
        this.context = context;
        mOnClickListener = listener;
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    @NonNull
    @Override
    public StepAdapter.StepHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.step_list_item, parent, false);
        return new StepAdapter.StepHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepAdapter.StepHolder holder, int position) {
        String number = recipe.get(position).getId();
        String stepName = recipe.get(position).getShortDescription();
        holder.stepNumber.setText(number);
        holder.stepText.setText(stepName);
    }

    @Override
    public int getItemCount() {
        return (recipe == null) ? 0 : recipe.size();
    }

    class StepHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.step_number)
        TextView stepNumber;

        @BindView(R.id.step_text)
        TextView stepText;

        StepHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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
