package com.hylux.calisthenics3;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class WorkoutOverviewFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private DatabaseInterface onUpdateListener;

    private String key;

    public WorkoutOverviewFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            key = getArguments().getString("EXTRA_KEY");
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_workout_overview, container, false);

        Routine currentRoutine = DatabaseInterface.routineList.get(key);

        TextView nameView = rootView.findViewById(R.id.name);
        nameView.setText(currentRoutine.getName());
        TextView aimView = rootView.findViewById(R.id.aim);
        aimView.setText(currentRoutine.getAim().toString());
        TextView equipmentView = rootView.findViewById(R.id.equipment);
        ArrayList<String> equipments = new ArrayList<>();
        for (Long equipmentIndex : currentRoutine.getEquipment()) {
            equipments.add(DatabaseInterface.Equipments.get(equipmentIndex.intValue()));
        }
        equipmentView.setText(equipments.toString());

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        final ArrayList<HashMap<String, Integer>> routine = currentRoutine.getRoutine();

        adapter = new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                TextView nameView = new TextView(getContext());
                String key = new ArrayList<>(routine.get(i).keySet()).get(0);
                String name = DatabaseInterface.exerciseList.get(key).getName();
                nameView.setText(name);

                return new RecyclerView.ViewHolder(nameView) {
                };
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

            }

            @Override
            public int getItemCount() {
                return routine.size();
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //((ExerciseAdapter) adapter).setOnUpdateListener(onUpdateListener);

        return rootView;
    }

    public static WorkoutOverviewFragment newInstance(String key) {
        WorkoutOverviewFragment fragment = new WorkoutOverviewFragment();

        Bundle args = new Bundle();
        args.putString("EXTRA_KEY", key);
        fragment.setArguments(args);

        return fragment;
    }

}
