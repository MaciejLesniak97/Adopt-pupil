package com.maciejlesniak.Dopasowania;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.maciejlesniak.R;

import java.util.List;

public class DopasowaniaAdapter extends RecyclerView.Adapter<DopasowaniaViewHolders> {
    private List<DopasowaniaObject> dopasowaniaList;
    private Context context;


    public DopasowaniaAdapter(List<DopasowaniaObject> dopasowaniaList, Context context) {
        this.dopasowaniaList = dopasowaniaList;
        this.context = context;
    }

    @NonNull
    @Override
    public DopasowaniaViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dopasowania, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        DopasowaniaViewHolders rcv = new DopasowaniaViewHolders(layoutView);

        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull DopasowaniaViewHolders holder, int position) {
        holder.mDopasowaniaId.setText(dopasowaniaList.get(position).getUserId());
        holder.mDopasowaniaName.setText(dopasowaniaList.get(position).getName());
        if (!dopasowaniaList.get(position).getProfileImageUrl().equals("default")) {
            Glide.with(context).load(dopasowaniaList.get(position).getProfileImageUrl()).into(holder.mMatchImage);
        }
    }

    @Override
    public int getItemCount() {
        return dopasowaniaList.size();
    }
}
