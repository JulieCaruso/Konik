package com.kapouter.konik.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kapouter.konik.R;
import com.kapouter.konik.auth.SignInActivity;
import com.kapouter.konik.util.request.RequestCallback;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        BookManager.getList("combined-print-and-e-book-fiction", new RequestCallback() {
            @Override
            public void newData() {
                Log.d("azerty", "newdata");
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
