package com.kapouter.konik.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.kapouter.konik.R;
import com.kapouter.konik.util.request.RequestCallback;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private RecyclerView mRecycler;
    private BookAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        mRecycler = (RecyclerView) findViewById(R.id.home_recycler);

        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new BookAdapter();
        mAdapter.setItems(BookManager.getCachedBooks());
        mRecycler.setAdapter(mAdapter);

        mAuth = FirebaseAuth.getInstance();
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
        /*FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(this, SignInActivity.class));
        }*/
    }
}
