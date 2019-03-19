package com.hylux.calisthenics3;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import java.util.HashMap;

public class ExerciseAdapter extends ItemAdapter {

    public ExerciseAdapter(Fragment parentFragment, HashMap<String, Exercise> dataSet) {
        super(parentFragment, dataSet);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        final String key = getKeyList().get(i);
        itemViewHolder.textView.setText(getDataSet().get(key).getName());
        itemViewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("EXERCISE_ADAPTER", "CLICKED " + key);
                getOnUpdateListener().onExerciseSelected(key);
            }
        });
    }
}