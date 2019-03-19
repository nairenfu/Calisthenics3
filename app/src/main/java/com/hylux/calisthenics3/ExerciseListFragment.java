package com.hylux.calisthenics3;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ExerciseListFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private DatabaseInterface onUpdateListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_exercise_list, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ExerciseAdapter(this, DatabaseInterface.exerciseList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        ((ExerciseAdapter) adapter).setOnUpdateListener(onUpdateListener);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof DatabaseInterface) {
            onUpdateListener = (DatabaseInterface) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement DatabaseInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onUpdateListener = null;
    }

    public void updateData() {
        Log.d("UPDATE", String.valueOf(adapter.getItemCount()));
        ((ExerciseAdapter) adapter).updateAdapter(DatabaseInterface.exerciseList);
    }

    public DatabaseInterface getOnUpdateListener() {
        return this.onUpdateListener;
    }

    public void setOnUpdateListener(DatabaseInterface onUpdateListener) {
        this.onUpdateListener = onUpdateListener;
    }
}
