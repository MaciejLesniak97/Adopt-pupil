package com.maciejlesniak.Matching;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.maciejlesniak.R;

import java.util.List;

public class MatchingAdapter extends RecyclerView.Adapter<MatchingViewHolders> {
    private List<MatchingObject> matchingList;
    private Context context;


    public MatchingAdapter(List<MatchingObject> matchingList, Context context) {
        this.matchingList = matchingList;
        this.context = context;
    }

    @NonNull
    @Override
    public MatchingViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matches, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        MatchingViewHolders rcv = new MatchingViewHolders(layoutView);

        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull MatchingViewHolders holder, int position) {
        holder.mMatchId.setText(matchingList.get(position).getUserId());
        holder.mDescriptionId.setText(matchingList.get(position).getDescription());
        holder.mMatchName.setText(matchingList.get(position).getName());
        if (!matchingList.get(position).getProfileImageUrl().equals("default")) {
            Glide.with(context).load(matchingList.get(position).getProfileImageUrl()).into(holder.mMatchImage);
        }
    }

    @Override
    public int getItemCount() {
        return matchingList.size();
    }
}
