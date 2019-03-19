package com.hylux.calisthenics3;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class Routine {

    private String name;
    private ArrayList<HashMap<String, Integer>> routine;
    private ArrayList<Long> equipment, aim;

    public Routine(String name, ArrayList<HashMap<String, Integer>> routine) {
        this.name = name;
        this.routine = routine;
        this.equipment = new ArrayList<>();
        this.aim = new ArrayList<>();
        for (HashMap<String, Integer> set : routine) {
            String key = (String) set.keySet().toArray()[0];
            Log.d("KEY", key);
            long equipmentForSet = DatabaseInterface.exerciseList.get(key).getEquipment();
            Log.d("EQUIPMENT", String.valueOf(equipmentForSet));
            if (!equipment.contains(equipmentForSet)) {
                equipment.add(equipmentForSet);
                Log.d("EQUIPMENTS", equipment.toString());
            }
            ArrayList<Long> aimForSet = DatabaseInterface.exerciseList.get(key).getAim();
            Log.d("AIM", aimForSet.toString());
            for (long aim : aimForSet) {
                if (!this.aim.contains(aim)) {
                    this.aim.add(aim);
                    Log.d("AIMS", this.aim.toString());
                }
            }
        }
    }

    public Routine(String name, ArrayList<HashMap<String, Integer>> routine, ArrayList<Long> equipment, ArrayList<Long> aim) {
        this.name = name;
        this.routine = routine;
        this.equipment = equipment;
        this.aim = aim;
    }

    public Routine(HashMap<String, Object> routine) {
        this.name = (String) routine.get("name");
        this.routine = (ArrayList<HashMap<String, Integer>>) routine.get("routine");
        this.equipment = (ArrayList<Long>) routine.get("equipment");
        this.aim = (ArrayList<Long>) routine.get("aim");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Long> getEquipment() {
        return equipment;
    }

    public void setEquipment(ArrayList<Long> equipment) {
        this.equipment = equipment;
    }

    public ArrayList<Long> getAim() {
        return aim;
    }

    public void setAim(ArrayList<Long> aim) {
        this.aim = aim;
    }

    public ArrayList<HashMap<String, Integer>> getRoutine() {
        return routine;
    }

    public void setRoutine(ArrayList<HashMap<String, Integer>> routine) {
        this.routine = routine;
    }

    @Override
    public String toString() {
        return "Routine{" +
                "name='" + name + '\'' +
                ", routine=" + routine +
                ", equipment=" + equipment +
                ", aim=" + aim +
                '}';
    }
}
