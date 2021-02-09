package com.maciejlesniak.Dopasowania;

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

public class DopasowaniaActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mDopasowaniaAdapter;
    private RecyclerView.LayoutManager mDopasowaniaLayoutManager;

    private String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dopasowania);

        currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setNestedScrollingEnabled(false); //scroll freely through the recyclerview
        mRecyclerView.setHasFixedSize(true);
        mDopasowaniaLayoutManager = new LinearLayoutManager(DopasowaniaActivity.this);
        mRecyclerView.setLayoutManager(mDopasowaniaLayoutManager);
        mDopasowaniaAdapter = new DopasowaniaAdapter(getDataSetDopasowania(), DopasowaniaActivity.this);
        mRecyclerView.setAdapter(mDopasowaniaAdapter);

        getUserMatchId();



    }

    private void getUserMatchId() {
        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference("Users").child(currentUserID).child("connections").child("matches");
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
                    if (snapshot.child("name").getValue() != null) {
                        name = snapshot.child("name").getValue().toString();
                    }
                    if (snapshot.child("profileImageUrl").getValue() != null) {
                        profileImageUrl = snapshot.child("profileImageUrl").getValue().toString();
                    }
                    if (snapshot.child("description").getValue() != null) {
                        description = snapshot.child("description").getValue().toString();
                    }

                    DopasowaniaObject obj = new DopasowaniaObject(userId, name, profileImageUrl, description);
                    resultsDopasowania.add(obj);
                    mDopasowaniaAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private ArrayList<DopasowaniaObject> resultsDopasowania = new ArrayList<DopasowaniaObject>();
    private List<DopasowaniaObject> getDataSetDopasowania() {
        return resultsDopasowania;
    }
}
