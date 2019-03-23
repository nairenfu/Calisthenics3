package com.hylux.calisthenics3;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;

public class EditRoutineFragment extends Fragment {

    private String currentId;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private DatabaseInterface onUpdateListener;

    public EditRoutineFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentId = "";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_edit_routine, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        HashMap<String, Exercise> testSet = new HashMap<>();
        //testSet.put("P90unQ7HjFw7ei7Z8HCn", new Exercise("a", "b", "c"));
        adapter = new RoutineAdapter(this, testSet);
        recyclerView.setAdapter(adapter);
        ((RoutineAdapter) adapter).setOnUpdateListener(onUpdateListener);
        adapter.notifyDataSetChanged();

        Button button = rootView.findViewById(R.id.button);
        final EditText setName = rootView.findViewById(R.id.editText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onUpdateListener.onHideKeyboard();
                addRoutine(currentId, setName.getText().toString());
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof DatabaseInterface) {
            onUpdateListener = (DatabaseInterface) context;
            Log.d("DBI_ERF", onUpdateListener.toString());
        } else {
            throw new RuntimeException(context.toString() + "must implement DatabaseInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onUpdateListener = null;
    }

    public String getCurrentId() {
        return currentId;
    }

    public void setCurrentId(String id) {
        this.currentId = id;
        Log.d("ROUTINE", currentId);
    }

    public void addExercise(String id) {
        ((RoutineAdapter) adapter).addExercise(id);
        adapter.notifyDataSetChanged();
    }

    public void addRoutine(String currentId, String name) {
        ((RoutineAdapter) adapter).addRoutine(currentId, name);
        ((ExploreFragment) getParentFragment().getParentFragment()).back();
    }

    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }
}
