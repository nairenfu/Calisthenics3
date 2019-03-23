package com.hylux.calisthenics3;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CreateRoutineFragment extends Fragment {

    private DatabaseInterface onUpdateListener;

    private FragmentManager fm;
    private ExerciseListFragment exerciseListFragment;
    private EditRoutineFragment editRoutineFragment;
    //private RoutineListFragment routineListFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("CRF", "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_routine, container, false);

        fm = getChildFragmentManager();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("CRF", "onViewCreated");

        exerciseListFragment = (ExerciseListFragment) fm.findFragmentByTag("elf");
        if (exerciseListFragment == null) {
            exerciseListFragment = new ExerciseListFragment();
            fm.beginTransaction().add(R.id.exerciseListContainer, exerciseListFragment, "elf").commit();
        }

        editRoutineFragment = (EditRoutineFragment) fm.findFragmentByTag("erf");
        if (editRoutineFragment == null) {
            editRoutineFragment = new EditRoutineFragment();
            fm.beginTransaction().add(R.id.editRoutineContainer, editRoutineFragment, "erf").commit();
        }

        /*routineListFragment = (RoutineListFragment) fm.findFragmentByTag("rlf");
        if (routineListFragment == null) {
            routineListFragment = new RoutineListFragment();
            fm.beginTransaction().add(R.id.routineListContainer, routineListFragment, "rlf").commit();
        }*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof DatabaseInterface) {
            onUpdateListener = (DatabaseInterface) context;
            Log.d("DBI_CRF", onUpdateListener.toString());
        } else {
            throw new RuntimeException(context.toString() + "must implement DatabaseInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onUpdateListener = null;
    }

    public ExerciseListFragment getExerciseListFragment() {
        return exerciseListFragment;
    }

    public EditRoutineFragment getEditRoutineFragment() {
        return editRoutineFragment;
    }

    /*public RoutineListFragment getRoutineListFragment() {
        return routineListFragment;
    }*/
}
