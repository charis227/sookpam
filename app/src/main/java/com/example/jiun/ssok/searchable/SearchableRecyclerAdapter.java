package com.example.jiun.ssok.searchable;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.example.jiun.ssok.model.DualModel;
import com.example.jiun.ssok.model.RecordDBManager;
import com.example.jiun.ssok.util.ViewHolderFactory;
import java.util.ArrayList;
import java.util.List;
import io.realm.Realm;

public class SearchableRecyclerAdapter extends RecyclerView.Adapter {
    private List<DualModel> modelList;
    private ArrayList<? extends DualModel> recordVoList;

    SearchableRecyclerAdapter(List<DualModel> items) {
        modelList = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolderFactory.create(parent);
    }

    @Override
    public int getItemViewType(int position) {
        return modelList.get(position).getItemViewType();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        modelList.get(position).onBindViewHolder(holder);
    }


    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public void searchInRealm(String charText) {
        RecordDBManager recordManager = new RecordDBManager(Realm.getDefaultInstance());
        recordVoList = recordManager.contains(charText);
        modelList.clear();
        modelList.addAll(recordVoList);
        notifyDataSetChanged();
    }

    public void add(List<DualModel> items) {
        modelList.addAll(items);
        notifyDataSetChanged();
    }

    public List<DualModel> getModelList() {
        return modelList;
    }

    public void clear() {
        modelList.removeAll(recordVoList);
        modelList.clear();
        notifyDataSetChanged();
    }

}