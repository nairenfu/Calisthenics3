package com.hylux.calisthenics3;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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
    CreateRoutineFragment createRoutineFragment;
    RoutineListFragment routineListFragment;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = this.getSupportFragmentManager();

        db = FirebaseFirestore.getInstance();
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
                            createRoutineFragment = (CreateRoutineFragment) fm.findFragmentByTag("crf");
                            if (fm.findFragmentByTag("crf") == null) {
                                createRoutineFragment = new CreateRoutineFragment();
                                fm.beginTransaction().add(R.id.mainActivity, createRoutineFragment, "crf").commit();
                                Log.d("FRAGMENTS", fm.getFragments().toString());
                            }
                            Log.d("FRAGMENTS", fm.getFragments().toString());
                        } else {
                            Log.w("DB", "Error getting documents.", task.getException());
                        }
                    }
                });
        Log.d("FRAGMENT", fm.getFragments().toString());
    }

    //----------- CREATE ROUTINE FRAGMENT --------------------------------------------------------------------------------------------------------------------------------------------

    @Override
    public void onExerciseSelected(String id) {
        Log.d("INTERFACE", "DING DONG INTERFACE CALLED");
        createRoutineFragment.getEditRoutineFragment().addExercise(id);
    }

    @Override
    public void onRoutineAdded(String id, String name, ArrayList<HashMap<String, Integer>> routine) {

        Routine routineMap = new Routine(name, routine);
        Log.d("ROUTINE", routineMap.toString());

        if (!createRoutineFragment.getEditRoutineFragment().getCurrentId().equals("")) {
            db.collection("routines")
                    .document(createRoutineFragment.getEditRoutineFragment().getCurrentId())
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
                            createRoutineFragment.getEditRoutineFragment().setCurrentId(task.getResult().getId());
                        }
                    });
        }

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
                            routineListFragment = (RoutineListFragment) fm.findFragmentByTag("rlf");
                            if (fm.findFragmentByTag("crf") == null) {
                                routineListFragment = new RoutineListFragment();
                                fm.beginTransaction().add(R.id.mainActivity, routineListFragment, "rlf").commit();
                                Log.d("FRAGMENTS", fm.getFragments().toString());
                            }
                            /*else {
                                fm.beginTransaction().remove(routineListFragment).add(R.id.mainActivity, routineListFragment, "rlf").commit();
                            }*/
                            Log.d("FRAGMENTS", fm.getFragments().toString());
                            Log.d("ROUTINES", DatabaseInterface.routineList.toString());
                        } else {
                            Log.w("DB", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}
