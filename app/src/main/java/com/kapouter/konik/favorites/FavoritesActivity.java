package com.kapouter.konik.favorites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.kapouter.konik.R;
import com.kapouter.konik.home.Book;

import java.util.List;
import java.util.Map;

public class FavoritesActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FavoritesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites_activity);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) finish();

        RecyclerView recycler = (RecyclerView) findViewById(R.id.favorites_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new FavoritesAdapter();
        mAdapter.setUid(mAuth.getCurrentUser().getUid());
        mAdapter.setItems(FavoritesManager.getCachedFavorites());
        recycler.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        FavoritesManager.getFavorites(mAuth.getCurrentUser().getUid(), new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FavoritesManager.setCachedFavorites((Map<String, Map<String, String>>) dataSnapshot.child("favorites").getValue());
                mAdapter.setItems(FavoritesManager.getCachedFavorites());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
