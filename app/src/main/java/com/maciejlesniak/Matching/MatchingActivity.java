package com.maciejlesniak.Matching;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maciejlesniak.R;

import java.util.ArrayList;
import java.util.List;

public class MatchingActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mMatchingAdapter;
    private RecyclerView.LayoutManager mMatchingLayoutManager;

    private String currentUserID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setNestedScrollingEnabled(false); //scroll freely through the recyclerview
        mRecyclerView.setHasFixedSize(true);
        mMatchingLayoutManager = new LinearLayoutManager(MatchingActivity.this);
        mRecyclerView.setLayoutManager(mMatchingLayoutManager);
        mMatchingAdapter = new MatchingAdapter(getDataSetDopasowania(), MatchingActivity.this);
        mRecyclerView.setAdapter(mMatchingAdapter);

        getUserMatchId();
    }

    private void getUserMatchId() {
        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference("Users").child(currentUserID).child("połączenia").child("dopasowania");
        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot matches : snapshot.getChildren()) {
                        FetchMatchInformation(matches.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //GET MATCHE'S INFORMATION
    private void FetchMatchInformation(String key) {
        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference("Users").child(key);
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String userId = snapshot.getKey();
                    String name = "";
                    String profileImageUrl = "";
                    String description = "";
                    if (snapshot.child("nazwa").getValue() != null) {
                        name = snapshot.child("nazwa").getValue().toString();
                    }
                    if (snapshot.child("profileImageUrl").getValue() != null) {
                        profileImageUrl = snapshot.child("profileImageUrl").getValue().toString();
                    }
                    if (snapshot.child("opis").getValue() != null) {
                        description = snapshot.child("opis").getValue().toString();
                    }

                    MatchingObject obj = new MatchingObject(userId, name, profileImageUrl, description);
                    resultsDopasowania.add(obj);
                    mMatchingAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private ArrayList<MatchingObject> resultsDopasowania = new ArrayList<MatchingObject>();
    private List<MatchingObject> getDataSetDopasowania() {
        return resultsDopasowania;
    }
}
