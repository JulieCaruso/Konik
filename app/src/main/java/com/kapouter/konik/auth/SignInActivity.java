package com.kapouter.konik.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kapouter.konik.R;
import com.kapouter.konik.app.App;
import com.kapouter.konik.util.SimpleMessageDialog;

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_activity);
        findViewById(R.id.sign_in_button).setOnClickListener(signInOnClickListener);
        findViewById(R.id.sign_in_sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });
        findViewById(R.id.sign_in_skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.editPreferences().putBoolean(getApplicationContext().getString(R.string.skip_sign_in), true).commit();
                finish();
            }
        });
        mAuth = FirebaseAuth.getInstance();
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

    private View.OnClickListener signInOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean valid = true;
            EditText usernameInput = (EditText) findViewById(R.id.sign_in_username);
            EditText passwordInput = (EditText) findViewById(R.id.sign_in_password);
            String username = usernameInput.getText().toString();
            if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
                valid = false;
                usernameInput.setError(v.getContext().getString(R.string.error_field_invalid_email));
            }
            String password = passwordInput.getText().toString();
            if (password.length() < 8) {
                valid = false;
                passwordInput.setError(v.getContext().getString(R.string.error_field_under_8));
            }
            if (valid) signin(username, password);
        }
    };

    private void signin(String username, String password) {
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            SimpleMessageDialog.show(SignInActivity.this, R.string.error_sign_in_title, R.string.error_sign_in_message);
                        } else {
                            Toast.makeText(SignInActivity.this, "auth succeeded", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
    }
}
