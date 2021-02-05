package com.maciejlesniak.Dopasowania;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maciejlesniak.R;

public class DopasowaniaViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mDopasowaniaId, mDopasowaniaName;
    public ImageView mMatchImage;
    public DopasowaniaViewHolders(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mDopasowaniaId = (TextView) itemView.findViewById(R.id.DopasowaniaId);
        mDopasowaniaName = (TextView) itemView.findViewById(R.id.MatchName);
        mMatchImage = (ImageView) itemView.findViewById(R.id.MatchImage);
    }

    @Override
    public void onClick(View v) {

    }
}
