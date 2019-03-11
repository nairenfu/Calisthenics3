package com.hylux.calisthenics3;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import java.util.HashMap;

public class ExerciseAdapter extends ItemAdapter {

    public ExerciseAdapter(HashMap<String, Exercise> dataSet) {
        super(dataSet);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        final String key = getKeyList().get(i);
        itemViewHolder.textView.setText(getDataSet().get(key).getName());
        itemViewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("RECYCLER_VIEW", "CLICKED " + key);
                getOnUpdateListener().onExerciseSelected(key);
            }
        });
    }
}