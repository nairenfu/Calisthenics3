package com.hylux.calisthenics3;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private HashMap<String, Exercise> dataSet;
    private ArrayList<String> keyList;

    private DatabaseInterface onUpdateListener;

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public String id;

        public ItemViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

    public ItemAdapter(HashMap<String, Exercise> dataSet) {
        this.dataSet = dataSet;
        keyList = new ArrayList<>(dataSet.keySet());
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        TextView v = (TextView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sample_exercise_text_view, viewGroup, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        final String key = keyList.get(i);
        itemViewHolder.textView.setText(dataSet.get(key).getName());
    }

    @Override
    public int getItemCount() {
        return keyList.size();
    }

    public void setOnUpdateListener(DatabaseInterface onUpdateListener) {
        this.onUpdateListener = onUpdateListener;
    }

    public HashMap<String, Exercise> getDataSet() {
        return dataSet;
    }

    public void setDataSet(HashMap<String, Exercise> dataSet) {
        this.dataSet = dataSet;
    }

    public ArrayList<String> getKeyList() {
        return keyList;
    }

    public void setKeyList(ArrayList<String> keyList) {
        this.keyList = keyList;
    }

    public DatabaseInterface getOnUpdateListener() {
        return onUpdateListener;
    }
}
