package com.kapouter.konik.auth;

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
import com.kapouter.konik.util.SimpleMessageDialog;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);
        findViewById(R.id.sign_up_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.sign_up_button).setOnClickListener(signUpOnClickListener);

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
                finish();
            } else {
                // User is signed out
                Log.d("azerty", "onAuthStateChanged:signed_out");
            }
        }
    };

    private View.OnClickListener signUpOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean valid = true;
            EditText usernameInput = (EditText) findViewById(R.id.sign_up_username);
            String username = usernameInput.getText().toString();
            if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
                valid = false;
                usernameInput.setError(v.getContext().getString(R.string.error_field_invalid_email));
            }
            EditText passwordInput = (EditText) findViewById(R.id.sign_up_password);
            String password = passwordInput.getText().toString();
            if (password.length() < 8) {
                valid = false;
                passwordInput.setError(v.getContext().getString(R.string.error_field_under_8));
            }
            EditText passwordConfirmationInput = (EditText) findViewById(R.id.sign_up_password_confirmation);
            String passwordConfirmation = passwordConfirmationInput.getText().toString();
            if (!password.equals(passwordConfirmation)) {
                valid = false;
                passwordConfirmationInput.setError(v.getContext().getString(R.string.error_unidentical_passwords));
            }
            if (valid) {
                mAuth.createUserWithEmailAndPassword(username, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignUpActivity.this, "auth failed", Toast.LENGTH_SHORT).show();
                                    SimpleMessageDialog.show(SignUpActivity.this, R.string.error_sign_up_title, R.string.error_sign_up_message);
                                } else {
                                    Toast.makeText(SignUpActivity.this, "auth succeeded", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
    };
}
