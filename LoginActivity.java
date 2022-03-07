package com.example.learningharmonizer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private static int state_account;
    EditText mEmail, mPassword;
    Button mLoginBtn;
    TextView mCreateBtn, mHomeBtn;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseFirestore db;
    String TAG = "USERS";
    public static DocumentReference docRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);

        mLoginBtn = findViewById(R.id.btnLogin);
        mCreateBtn = findViewById(R.id.textRegister);
        mHomeBtn = findViewById(R.id.txtHome);

        mCreateBtn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            finish();
        });

        mHomeBtn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), MenuActivity.class));
            finish();
        });

        mLoginBtn.setOnClickListener(view -> onClick());
    }

    private void onClick() {

        fAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            mEmail.setError("Email is Required");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            mPassword.setError("Password is Required");
            return;
        }

        if (password.length() < 6) {
            mPassword.setError("Password Must be >= 6 Characters");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

         //authenticate the user

        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(LoginActivity.this, "Successfully Logged In", Toast.LENGTH_LONG).show();
                state_account = 2;
                get_user_info(email);
                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                startActivity(intent);

            } else {
                Toast.makeText(LoginActivity.this, "Error ! " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                state_account = -1;
                progressBar.setVisibility(View.GONE);
            }

        });

    }

    public static int state_account_etat() {
        return state_account;
    }

    private void get_user_info(String email) {
        docRef = db.collection("user").document(email);
        System.out.println("aaaaaaaaaaaaaaaaa" + docRef);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                } else {
                    Log.d(TAG, "No such document");
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });
    }
}