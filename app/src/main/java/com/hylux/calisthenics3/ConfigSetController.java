package com.hylux.calisthenics3;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ConfigSetController {

    public ConfigSetController(final RoutineAdapter parentAdapter, final View parentLayout, final String key, final int index) {

        final EditText editReps = parentLayout.findViewById(R.id.editReps);

        Button save = parentLayout.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    parentAdapter.getSet().put(key, Integer.valueOf(editReps.getText().toString()));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                Log.d("VIEWS", parentLayout.toString());
                for(int index=0; index<(((ViewGroup) parentLayout).getChildCount()); ++index) {
                    View nextChild = ((ViewGroup) parentLayout).getChildAt(index);
                    Log.d("VIEW", nextChild.toString());
                }

                ((ViewGroup) parentLayout.getParent()).removeView(parentLayout);
                parentAdapter.setHasConfigView(false);
                Log.d("HASCONFIGVIEW", String.valueOf(parentAdapter.isHasConfigView()));
            }
        });

        Button delete = parentLayout.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parentAdapter.removeExercise(index);
                ((ViewGroup) parentLayout.getParent()).removeView(parentLayout);
                parentAdapter.setHasConfigView(false);
                Log.d("HASCONFIGVIEW", String.valueOf(parentAdapter.isHasConfigView()));
            }
        });
    }
}
