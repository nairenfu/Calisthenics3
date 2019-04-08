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
    private WorkoutOverviewFragment workoutOverviewFragment;

    private View rootView;

    public StartWorkoutFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fm = getChildFragmentManager();
        rootView = inflater.inflate(R.layout.fragment_start_workout, container, false);

        return rootView;
    }

    public void clear() {
        if (workoutOverviewFragment != null) {
            fm.beginTransaction().remove(workoutOverviewFragment).commit();
        }
        ((ViewGroup) rootView).removeAllViews();
    }

    public boolean back() {
        if (workoutOverviewFragment != null) {
            return workoutOverviewFragment.back();
        }

        return false;
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

    public WorkoutOverviewFragment getWorkoutOverviewFragment() {
        return workoutOverviewFragment;
    }

    public void newWorkoutOverviewFragment(String id) {
        workoutOverviewFragment = (WorkoutOverviewFragment) fm.findFragmentByTag("wof");
        if (workoutOverviewFragment == null) {
            workoutOverviewFragment = WorkoutOverviewFragment.newInstance(id);
            fm.beginTransaction().add(R.id.startWorkoutFragment, workoutOverviewFragment, "wof").commit();
        }
    }
}
