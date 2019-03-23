package com.hylux.calisthenics3;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements DatabaseInterface {

    FirebaseFirestore db;
    StartWorkoutFragment startWorkoutFragment;
    ExploreFragment exploreFragment;
    FragmentManager fm;
    ViewPager viewPager;
    ScrollingPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = this.getSupportFragmentManager();

        //----------- MATCHING LOCAL DATABASE WITH FIRE_BASE ----------------------------------------------------------------------------------------------------------------
        db = FirebaseFirestore.getInstance();
        updateExercises(db);
        updateRoutines(db);

        Log.d("FRAGMENT", fm.getFragments().toString());

        viewPager = findViewById(R.id.mainActivity);
        pagerAdapter = new ScrollingPagerAdapter(fm);
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public void onBackPressed() {
        Log.d("FRAGMENTS", fm.getFragments().toString());
        fm.popBackStackImmediate();
        if (viewPager.getCurrentItem() == 1) {
            exploreFragment.back();
        }
    }

    public class ScrollingPagerAdapter extends FragmentPagerAdapter {
        private int numPages = 2;

        public ScrollingPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    startWorkoutFragment = (StartWorkoutFragment) fm.findFragmentByTag(
                            "android:switcher:" + R.id.mainActivity + ":" + 0);
                    if (fm.findFragmentByTag("android:switcher:" + R.id.mainActivity + ":" + 0) == null) {
                        startWorkoutFragment = new StartWorkoutFragment();
                    }
                    return startWorkoutFragment;

                case 1:
                    exploreFragment = (ExploreFragment) fm.findFragmentByTag(
                            "android:switcher:" + R.id.mainActivity + ":" + 1);
                    if (fm.findFragmentByTag("android:switcher:" + R.id.mainActivity + ":" + 1) == null) {
                        exploreFragment = new ExploreFragment();
                    }
                    return exploreFragment;

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return numPages;
        }
    }

    //----------- COLLECTION METHODS -------------------------------------------------------------------------------------------------------------------------------------------------
    public void updateExercises(FirebaseFirestore db) {
        db.collection("exercises")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Log.d("DB", document.getId() + " => " + document.getData());
                                DatabaseInterface.exerciseList.put(document.getId(), new Exercise((HashMap<String, Object>) document.getData()));
                            }
                            try {
                                exploreFragment.updateData();
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.w("DB", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void updateRoutines(FirebaseFirestore db) {
        db.collection("routines")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Log.d("DB", document.getId() + " => " + document.getData());
                                DatabaseInterface.routineList.put(document.getId(), new Routine((HashMap<String, Object>) document.getData()));
                            }
                            try {
                                exploreFragment.updateData();
                                //createRoutineFragment.getRoutineListFragment().updateData();
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.w("DB", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    //----------- CREATE ROUTINE FRAGMENT --------------------------------------------------------------------------------------------------------------------------------------------

    @Override
    public void onExerciseSelected(String id) {
        Log.d("INTERFACE", "DING DONG INTERFACE CALLED");
        //createRoutineFragment.getEditRoutineFragment().addExercise(id);
        //HashMap<String, Integer> exercise = new HashMap<>();
        //exercise.put(id, 10);
        //DatabaseInterface.currentRoutine.add(exercise);
        if (exploreFragment.getEditFragment().getClass() == CreateRoutineFragment.class) {
            ((CreateRoutineFragment) exploreFragment.getEditFragment()).getEditRoutineFragment().addExercise(id);
        }
        Log.d("ROUTINE", DatabaseInterface.currentRoutine.toString());
    }

    @Override
    public void onRoutineAdded(String id, String name, ArrayList<HashMap<String, Integer>> routine) {

        Routine routineMap = new Routine(name, routine);
        Log.d("ROUTINE", routineMap.toString());

        if (!exploreFragment.getEditFragment().getEditRoutineFragment().getCurrentId().equals("")) {
            db.collection("routines")
                    .document(exploreFragment.getEditFragment().getEditRoutineFragment().getCurrentId())
                    .set(routineMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //Log.d("DB", task.getResult().toString());
                        }
                    });
        } else {
            db.collection("routines")
                    .add(routineMap)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Log.d("ROUTINE", task.getResult().getId());
                            try {
                                exploreFragment.getEditFragment().getEditRoutineFragment().setCurrentId(task.getResult().getId());
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }

        updateRoutines(db);
    }

    @Override
    public void onHideKeyboard() {
        Log.d("ON_HIDE_KEYBOARD", "TRUE");
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(getApplicationContext());
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
