package com.hylux.calisthenics3;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StartWorkoutFragment extends Fragment {

    private String key;
    private Routine currentRoutine;

    private FragmentManager fm;
    private ExerciseBriefFragment exerciseBriefFragment;

    public StartWorkoutFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fm = getChildFragmentManager();

        return inflater.inflate(R.layout.fragment_start_workout, container, false);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Routine getCurrentRoutine() {
        return currentRoutine;
    }

    public void setCurrentRoutine(Routine currentRoutine) {
        this.currentRoutine = currentRoutine;
    }

    public void setCurrentRoutine(String id) {
        this.currentRoutine = DatabaseInterface.routineList.get(id);
    }

    public ExerciseBriefFragment getExerciseBriefFragment() {
        return exerciseBriefFragment;
    }

    public void setExerciseBriefFragment(ExerciseBriefFragment exerciseBriefFragment) {
        this.exerciseBriefFragment = exerciseBriefFragment;
    }

    public void newExerciseBriefFragment() {
        exerciseBriefFragment = (ExerciseBriefFragment) fm.findFragmentByTag("ebf");
        if (exerciseBriefFragment == null) {
            exerciseBriefFragment = ExerciseBriefFragment.newInstance(key);
            fm.beginTransaction().add(R.id.startWorkoutFragment, exerciseBriefFragment, "ebf").commit();
        }
    }
}
