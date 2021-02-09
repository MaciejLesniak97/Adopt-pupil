package com.maciejlesniak.Dopasowania;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maciejlesniak.Chat.ChatActivity;
import com.maciejlesniak.R;

public class DopasowaniaViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mMatchId, mDescriptionId, mDopasowaniaName;
    public ImageView mMatchImage;
    public DopasowaniaViewHolders(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mMatchId = (TextView) itemView.findViewById(R.id.MatchId);
        mDescriptionId = (TextView) itemView.findViewById(R.id.DescriptionId);
        mDopasowaniaName = (TextView) itemView.findViewById(R.id.MatchName);
        mMatchImage = (ImageView) itemView.findViewById(R.id.MatchImage);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), ChatActivity.class);
        Bundle b = new Bundle();
        b.putString("matchId", mMatchId.getText().toString());
        intent.putExtras(b);
        v.getContext().startActivity(intent);
    }
}
