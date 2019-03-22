package com.hylux.calisthenics3;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;

import static com.hylux.calisthenics3.DatabaseInterface.currentRoutine;

public class RoutineAdapter extends ItemAdapter {

    private RoutineAdapter self;

    private int defaultReps = 10;

    private HashMap<String, Integer> set;

    private int spawnedIndex;

    private boolean hasConfigView = false;

    public RoutineAdapter(Fragment parentFragment, HashMap<String, Exercise> dataSet) {
        super(parentFragment, dataSet);
        Log.d("ROUTINE_ADAPTER", dataSet.toString());
        Log.d("ROUTINE_KEY_LIST", getKeyList().toString());
        self = this;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder itemViewHolder, int i) {
        final int index = i;
        final String key = getKeyList().get(i);
        itemViewHolder.textView.setText(getDataSet().get(key).getName());
        itemViewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spawnedIndex = itemViewHolder.getAdapterPosition();
                Log.d("POSITION", String.valueOf(spawnedIndex));
                /*set = new HashMap<>();
                set.put(key, defaultReps + 10);
                Log.d("RECYCLER_VIEW", "CLICKED " + key);
                currentRoutine.set(index, set);
                currentRoutine.set(index, set);*/

                FrameLayout parentView = (FrameLayout) getParentFragment().getView();

                RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(8, 8, 8, 8);

                Log.d("HASCONFIGVIEW", String.valueOf(hasConfigView));
                if (!hasConfigView) {
                    View configSetView = View.inflate(getParentFragment().getContext(), R.layout.view_config_set, (ViewGroup) itemViewHolder.itemView);
                    //configSetView.setLayoutParams(params);
                    new ConfigSetController(self, configSetView, key, spawnedIndex);
                    hasConfigView = true;
                }
            }
        });
    }

    public void addExercise(String id) {
        getDataSet().put(id, DatabaseInterface.exerciseList.get(id));
        getKeyList().add(id);
        HashMap<String, Integer> set = new HashMap<>();
        set.put(id, defaultReps);
        Log.d("ROUTINE_SET", set.toString());
        currentRoutine.add(set);
        Log.d("ROUTINE", currentRoutine.toString());
        Log.d("ROUTINE", getDataSet().toString());
        Log.d("ROUTINE", getKeyList().toString());
        Log.d("ROUTINE", String.valueOf(this.getItemCount()));
        notifyDataSetChanged();
    }

    public void removeExercise(int updatedIndex) {
        getKeyList().remove(updatedIndex);
        currentRoutine.remove(updatedIndex);
        Log.d("ROUTINE", currentRoutine.toString());
        Log.d("ROUTINE", getDataSet().toString());
        Log.d("ROUTINE", getKeyList().toString());
        Log.d("ROUTINE", String.valueOf(this.getItemCount()));
        notifyItemRemoved(updatedIndex);
    }

    public void addRoutine(String currentId, String name) {
        getOnUpdateListener().onRoutineAdded(currentId, name, currentRoutine);
        currentRoutine.clear();
        Log.d("ROUTINE", currentRoutine.toString());
    }

    public ArrayList<HashMap<String, Integer>> getCurrentRoutine() {
        return currentRoutine;
    }

    public HashMap<String, Integer> getSet() {
        return set;
    }

    public void setSet(HashMap<String, Integer> set) {
        this.set = set;
    }

    public boolean isHasConfigView() {
        return hasConfigView;
    }

    public void setHasConfigView(boolean hasConfigView) {
        this.hasConfigView = hasConfigView;
    }
}
