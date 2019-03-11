package com.hylux.calisthenics3;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

public class RoutineAdapter extends ItemAdapter {

    private int defaultReps = 10;
    private ArrayList<HashMap<String,Integer>> currentRoutine;

    public RoutineAdapter(HashMap<String, Exercise> dataSet) {
        super(dataSet);
        currentRoutine = new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        final int index = i;
        final String key = getKeyList().get(i);
        itemViewHolder.textView.setText(getDataSet().get(key).getName());
        itemViewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Integer> set = new HashMap<>();
                set.put(key, defaultReps + 10);
                Log.d("RECYCLER_VIEW", "CLICKED " + key);
                currentRoutine.set(index, set);
                currentRoutine.set(index, set);
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

    public void addRoutine(String currentId, String name) {
        getOnUpdateListener().onRoutineAdded(currentId, name, currentRoutine);
    }

    public ArrayList<HashMap<String, Integer>> getCurrentRoutine() {
        return currentRoutine;
    }

    public void setCurrentRoutine(ArrayList<HashMap<String, Integer>> currentRoutine) {
        this.currentRoutine = currentRoutine;
    }
}
