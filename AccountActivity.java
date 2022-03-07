package com.example.learningharmonizer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Map;

public class AccountActivity extends AppCompatActivity {
    TextView your_email, your_phone, your_name, your_password, mHomeBtn;
    String TAG = "USERS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        DocumentReference docRef = LoginActivity.docRef;
        your_email = findViewById(R.id.your_email);
        your_name = findViewById(R.id.your_name);
        your_phone = findViewById(R.id.your_phone);
        your_password = findViewById(R.id.your_password);
        mHomeBtn = findViewById(R.id.txtHome);

        mHomeBtn.setOnClickListener(view -> openMenuActivity());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        System.out.println("ertytrerty" + document.getData());
                        Map<String, Object> your_user_account = document.getData();
                        assert your_user_account != null;
                        set_parametre_account(your_user_account);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }

            private void set_parametre_account(Map<String, Object> your_user_account) {
                String element1 = (String) your_user_account.get("email");
                String element2 = (String) your_user_account.get("phone");
                String element3 = (String) your_user_account.get("pseudo");
                String element4 = (String) your_user_account.get("password");

                your_email.setText(element1);
                your_phone.setText(element2);
                your_name.setText(element3);
                your_password.setText(element4);
            }
        });


    }

    private void openMenuActivity() {
        startActivity(new Intent(getApplicationContext(), MenuActivity.class));
        finish();
    }
}