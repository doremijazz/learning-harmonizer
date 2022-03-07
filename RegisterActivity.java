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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {
    public static int state_account;
    EditText mFullName, mEmail, mPassword, mPhone;
    Button mRegisterBtn;
    TextView mLoginBtn, mHomeBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore db;
    ProgressBar progressBar;
    String TAG = "USERS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FirebaseApp.initializeApp(RegisterActivity.this);

        mFullName = findViewById(R.id.fullName);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mPhone = findViewById(R.id.phone);
        mRegisterBtn = findViewById(R.id.btnLogin);
        mLoginBtn = findViewById(R.id.textRegister);
        mHomeBtn = findViewById(R.id.homeText);

        fAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

        mRegisterBtn.setOnClickListener(v -> {
            String email = mEmail.getText().toString().trim();
            String phone_number = mPhone.getText().toString().trim();
            String pseudo = mFullName.getText().toString().trim();
            String password = mPassword.getText().toString().trim();

            user_profil new_user = new user_profil(email, phone_number, pseudo, password);

            if (TextUtils.isEmpty(email)) {
                mEmail.setError("Email is required.");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                mPassword.setError("Password is required.");
                return;
            }

            if (password.length() < 6) {
                mPassword.setError("Password must be greater or equals to 6 characters.");
            }

            progressBar.setVisibility(View.VISIBLE);

            fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Error ! Please verify your e-mail or password ", Toast.LENGTH_SHORT).show();
                    state_account = -1;
                    progressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(RegisterActivity.this, "User Created. Please log-in !", Toast.LENGTH_SHORT).show();
                    save_user_info_database(new_user);
                    state_account = 2;
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            });
        });
        mLoginBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        });
        mHomeBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(intent);
        });

    }

    private void save_user_info_database(user_profil new_user) {
         //Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("email", new_user.getFirst());
        user.put("phone", new_user.getSecond());
        user.put("pseudo", new_user.getThird());
        user.put("password", new_user.getFourth());

        System.out.println(new_user.getFirst());

 //Add a new document with a generated ID
        db.collection("user").document(new_user.getFirst())
                .set(user)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully written!"))
                .addOnFailureListener(e -> Log.w(TAG, "Error writing document", e));

    }

    public static int state_account_etat (){ return state_account;}
}
