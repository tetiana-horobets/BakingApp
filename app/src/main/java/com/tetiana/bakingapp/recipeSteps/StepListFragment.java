package com.tetiana.bakingapp.recipeSteps;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tetiana.bakingapp.R;
import com.tetiana.bakingapp.ReadData;
import com.tetiana.bakingapp.model.Step;

import java.io.IOException;
import java.util.ArrayList;

public class StepListFragment extends Fragment implements StepAdapter.ListItemClickListener {

    ArrayList<Step> steps = new ArrayList<>();
    private StepAdapter stepAdapter;

    private StepChangeHandler stepChangeHandler = new StepChangeHandler() {
        @Override
        public void onStepChanged(int stepId) {}
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            ReadData readData = new ReadData(getActivity().getApplicationContext());
            steps = readData.getStepList(steps);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stepAdapter = new StepAdapter(steps, getActivity().getApplicationContext(), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_steps_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView mRecyclerView = view.findViewById(R.id.rv_step_list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(),  1));
        mRecyclerView.setAdapter(stepAdapter);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        int id = stepAdapter.getId();
//            Intent intent = new Intent(getActivity(), StepDetailFragment.class);
//            intent.putExtra("stepID", id);
//            startActivity(intent);
        stepChangeHandler.onStepChanged(id);

    }

    public interface StepChangeHandler {
        void onStepChanged(int stepId);
    }

    public void setStepChangeHandler(StepChangeHandler stepChangeHandler) {
        this.stepChangeHandler = stepChangeHandler;
    }
}
