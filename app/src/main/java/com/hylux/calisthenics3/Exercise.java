package com.hylux.calisthenics3;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Exercise extends Object {

    private String name, brief, pathToVideo;
    private ArrayList<String> pathsToImages;
    private ArrayList<Long> aim;
    private long equipment;

    public Exercise(String name, ArrayList<Long> aim, String brief, long equipment) {
        this.name = name;
        this.aim = aim;
        this.brief = brief;
        this.equipment = equipment;
        this.pathsToImages = new ArrayList<>();
    }

    public Exercise(String name, ArrayList<Long> aim, String brief, long equipment, String pathToVideo, ArrayList<String> pathsToImages) {
        this.name = name;
        this.aim = aim;
        this.brief = brief;
        this.equipment = equipment;
        this.pathToVideo = pathToVideo;
        this.pathsToImages = pathsToImages;
    }

    public Exercise(HashMap<String, Object> exercise) {
        this.name = (String) exercise.get("name");
        this.aim = (ArrayList<Long>) exercise.get("aim");
        this.brief = (String) exercise.get("brief");
        this.equipment = (Long) exercise.get("equipment");
        this.pathToVideo = (String) exercise.get("pathToVideo");
        this.pathsToImages = (ArrayList<String>) exercise.get("pathsToImages");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Long> getAim() {
        return aim;
    }

    public void setAim(ArrayList<Long> aim) {
        this.aim = aim;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public long getEquipment() {
        return equipment;
    }

    public void setEquipment(long equipment) {
        this.equipment = equipment;
    }

    public String getPathToVideo() {
        return pathToVideo;
    }

    public void setPathToVideo(String pathToVideo) {
        this.pathToVideo = pathToVideo;
    }

    public ArrayList<String> getPathsToImages() {
        return pathsToImages;
    }

    public void setPathsToImages(ArrayList<String> pathsToImages) {
        this.pathsToImages = pathsToImages;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "name='" + name + '\'' +
                ", aim='" + aim + '\'' +
                ", brief='" + brief + '\'' +
                ", pathToVideo='" + pathToVideo + '\'' +
                ", pathsToImages=" + pathsToImages +
                '}';
    }

    public void addToCollection() {
        FirebaseFirestore.getInstance().collection("exercises")
                .add(this)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("DB", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("DB", "Error adding document", e);
                    }
                });
    }
}
