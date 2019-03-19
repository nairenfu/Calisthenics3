package com.hylux.calisthenics3;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class ExploreFragment extends Fragment {

    private View rootView;
    private FrameLayout rootLayout;
    private TextView workoutsTab, exercisesTab;
    private Button createWorkoutButton, createExerciseButton;

    private FragmentManager fm;
    private Fragment currentFragment;

    public ExploreFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_explore, container, false);
        rootLayout = rootView.findViewById(R.id.exploreFragment);

        workoutsTab = rootView.findViewById(R.id.workoutsTab);
        exercisesTab = rootView.findViewById(R.id.exercisesTab);
        createWorkoutButton = rootView.findViewById(R.id.createWorkoutButton);
        createExerciseButton = rootView.findViewById(R.id.createExerciseButton);

        fm = getChildFragmentManager();

        currentFragment = new RoutineListFragment();
        fm.beginTransaction().add(R.id.listContainer, currentFragment, "WORKOUT").commit();

        workoutsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fm.getFragments().get(0).getClass() != RoutineListFragment.class) {
                    fm.beginTransaction().remove(currentFragment).commit();
                    currentFragment = new RoutineListFragment();
                    fm.beginTransaction().add(R.id.listContainer, currentFragment, "WORKOUT").commit();
                }
            }
        });

        exercisesTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fm.getFragments().get(0).getClass() != ExerciseListFragment.class) {
                    fm.beginTransaction().remove(currentFragment).commit();
                    currentFragment = new ExerciseListFragment();
                    fm.beginTransaction().add(R.id.listContainer, currentFragment, "EXERCISE").commit();
                }
            }
        });

        createWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FrameLayout editLayout = new FrameLayout(getContext());
                editLayout.setId(R.id.editContainer);
                editLayout.setBackgroundColor(Color.CYAN);
                rootLayout.addView(editLayout);
                fm.beginTransaction().add(R.id.editContainer, new CreateRoutineFragment(), "CREATE").addToBackStack("EXPLORE").commit();
            }
        });

        Log.d("FRAGMENTS", fm.getFragments().toString());

        return rootView;
    }

    public void updateData() {
        if (fm.getFragments().get(0).getClass() == RoutineListFragment.class) {
            ((RoutineListFragment) currentFragment).updateData();
        }
        if (fm.getFragments().get(0).getClass() == ExerciseListFragment.class) {
            ((ExerciseListFragment) currentFragment).updateData();
        }
    }

    public void back() {
        fm.popBackStackImmediate();
        rootLayout.removeView(rootView.findViewById(R.id.editContainer));
    }

    public Fragment getEditFragment() {
        return fm.findFragmentByTag("CREATE");
    }
}
