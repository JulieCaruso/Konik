package com.kapouter.konik.favorites;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.kapouter.konik.R;

import java.util.HashMap;
import java.util.Map;

public class FavoritesActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites_activity);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) finish();

    }

    @Override
    protected void onResume() {
        super.onResume();

        FavoritesManager.getFavorites(mAuth.getCurrentUser().getUid(), new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Map<String, String>> favorites = (Map<String, Map<String, String>>) dataSnapshot.child("favorites").getValue();
                favorites.equals(null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
