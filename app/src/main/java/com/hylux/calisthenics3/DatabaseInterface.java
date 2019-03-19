package com.hylux.calisthenics3;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public interface DatabaseInterface {
    HashMap<String, Exercise> exerciseList = new HashMap<>();
    HashMap<String, Routine> routineList = new HashMap<>();
    ArrayList<HashMap<String,Integer>> currentRoutine = new ArrayList<>();

    ArrayList<String> MuscleGroups = new ArrayList<>(Arrays.asList(
            "none",
            "triceps", "bicep",
            "chest", "abdominal",
            "upper_back", "lower_back",
            "quads", "hamstring", "calves"));

    //BodyGroups

    ArrayList<String> Equipments = new ArrayList<>(Arrays.asList(
            "none", "dumbbell", "machine"));

    void onExerciseSelected(String id);
    void onRoutineAdded(String id, String name, ArrayList<HashMap<String, Integer>> routine);
    void onHideKeyboard();
}
