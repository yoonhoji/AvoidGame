package com.example.shootergame;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {
    ArrayList<UserRanking> items = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType){
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.ranking_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position){
        UserRanking item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount(){
        return items.size();
    }
    public void addItem(UserRanking item){
        items.add(item);
    }
    public void setItems(ArrayList<UserRanking> items){
        this.items = items;
    }
    public UserRanking getItem(int position){
        return items.get(position);
    }
    public void setItem(int position, UserRanking item){
        items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView noItem, nameItem, rpItem;

        public ViewHolder(View itemView){
            super(itemView);
            noItem = itemView.findViewById(R.id.noItem);
            nameItem = itemView.findViewById(R.id.nameItem);
            rpItem = itemView.findViewById(R.id.rpItem);
        }
        public void setItem(UserRanking item){
            noItem.setText(String.valueOf(item.getNo()));
            nameItem.setText(item.getName());
            rpItem.setText(item.getRp());
        }
    }
}
