package com.kapouter.konik.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kapouter.konik.R;
import com.kapouter.konik.app.App;
import com.kapouter.konik.auth.SignInActivity;
import com.kapouter.konik.favorites.FavoritesActivity;
import com.kapouter.konik.util.request.RequestCallback;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private RecyclerView mRecycler;
    private BookAdapter mAdapter;

    private ImageView mFavorites;
    private ImageView mSignInOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        mRecycler = (RecyclerView) findViewById(R.id.home_recycler);

        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new BookAdapter();
        mAdapter.setItems(BookManager.getCachedBooks());
        mRecycler.setAdapter(mAdapter);

        mFavorites = (ImageView) findViewById(R.id.home_favorites);
        mSignInOut = (ImageView) findViewById(R.id.home_sign_in_out);

        mAuth = FirebaseAuth.getInstance();

        mFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, FavoritesActivity.class));
            }
        });

        mSignInOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAuth.getCurrentUser() == null) {
                    startActivity(new Intent(HomeActivity.this, SignInActivity.class));
                } else {
                    mFavorites.setVisibility(View.GONE);
                    mAuth.signOut();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        BookManager.getList("combined-print-and-e-book-fiction", new RequestCallback() {
            @Override
            public void newData() {
                Log.d("azerty", "newdata");
                mAdapter.setItems(BookManager.getCachedBooks());
            }

            @Override
            public void noChange() {
                Log.d("azerty", "onchange");
            }

            @Override
            public void error() {
                Log.d("azerty", "error");
            }
        });
        FirebaseUser user = mAuth.getCurrentUser();
        boolean skip = App.getSharedPreferences().getBoolean(getApplicationContext().getString(R.string.skip_sign_in), false);
        if (user == null && !skip) {
            startActivity(new Intent(this, SignInActivity.class));
        } else if (user == null) {
            mFavorites.setVisibility(View.GONE);
        } else {
            mFavorites.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthStateListener != null)
            mAuth.removeAuthStateListener(mAuthStateListener);
    }

    private FirebaseAuth.AuthStateListener mAuthStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                // User is signed in
                Log.d("azerty", "onAuthStateChanged:signed_in:" + user.getUid());
            } else {
                // User is signed out
                Log.d("azerty", "onAuthStateChanged:signed_out");
            }
        }
    };
}
