package com.hylux.calisthenics3;

import java.util.ArrayList;

public class Workout {

    private String name, intro;
    private ArrayList<Routine> routines;
    private ArrayList<Long> equipment;

    public Workout(String name, String intro, ArrayList<Routine> routines) {
        this.name = name;
        this.intro = intro;
        this.routines = routines;
        this.equipment = new ArrayList<>();
        for (Routine routine : routines) {
            this.equipment.addAll(routine.getEquipment());
        }
    }

    public Workout(String name, String intro, ArrayList<Long> equipment, ArrayList<Routine> routines) {
        this.name = name;
        this.intro = intro;
        this.routines = routines;
        this.equipment = equipment;
    }
}
