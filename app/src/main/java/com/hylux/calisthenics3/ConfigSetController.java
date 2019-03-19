package com.hylux.calisthenics3;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ConfigSetController {

    public ConfigSetController(final RoutineAdapter parentAdapter, final View parentLayout, final String key) {

        final EditText editReps = parentLayout.findViewById(R.id.editReps);

        Button save = parentLayout.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parentAdapter.getSet().put(key, Integer.valueOf(editReps.getText().toString()));
                ((ViewGroup) parentLayout.getRootView()).removeView(parentLayout);
            }
        });

        Button delete = parentLayout.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
