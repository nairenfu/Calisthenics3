package com.hylux.calisthenics3;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class RoutineListAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private HashMap<String, Routine> dataSet;
    private ArrayList<String> keyList;

    public RoutineListAdapter(HashMap<String, Routine> dataSet) {
        this.dataSet = dataSet;
        this.keyList = new ArrayList<>(dataSet.keySet());
    }

    @NonNull
    @Override
    public ItemAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sample_exercise_text_view, viewGroup, false);
        return new ItemAdapter.ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ItemViewHolder itemViewHolder, int i) {
        final String key = keyList.get(i);
        itemViewHolder.textView.setText(dataSet.get(key).getName());
    }

    @Override
    public int getItemCount() {
        return keyList.size();
    }

    public void updateAdapter(HashMap<String, Routine> dataSet) {
        this.dataSet = dataSet;
        keyList = new ArrayList<>(dataSet.keySet());
        notifyDataSetChanged();
    }

    public HashMap<String, Routine> getDataSet() {
        return dataSet;
    }

    public void setDataSet(HashMap<String, Routine> dataSet) {
        this.dataSet = dataSet;
    }

    public ArrayList<String> getKeyList() {
        return keyList;
    }

    public void setKeyList(ArrayList<String> keyList) {
        this.keyList = keyList;
    }
}
