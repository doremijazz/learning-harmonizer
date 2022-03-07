package com.example.learningharmonizer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    ImageButton btt, Log_register, Account, contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        btt = findViewById(R.id.btt);
        btt.setOnClickListener(view -> openPianoActivity());

        contact = findViewById(R.id.contact);
        contact.setOnClickListener(this::onClick);

        Account = findViewById(R.id.Account);
        Account.setOnClickListener(view -> openAccountActivity());

        Log_register = findViewById(R.id.Log_register);
        Log_register.setOnClickListener(view -> openLoginActivity());


        int state_log = LoginActivity.state_account_etat();
        int state_register = RegisterActivity.state_account_etat();
        if (state_log==2 || state_register==2){
            Log_register.setVisibility(View.INVISIBLE);
            Account.setVisibility(View.VISIBLE);
        }
    }

    private void openAccountActivity() {
        startActivity(new Intent(getApplicationContext(),AccountActivity.class));
        finish();
    }

    public void onClick( View view) {
        String url = "https://docs.google.com/forms/d/e/1FAIpQLSdbCjgt_mvkADias4MYYeTojrwV8CH1PKp_vwlEpwR4tQWanQ/viewform?usp=sf_link";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
            startActivity(i);
    }

    private void openLoginActivity() {
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }

    private void openPianoActivity() {
        Intent intent = new Intent (this, PianoActivity.class);
        startActivity(intent);
    }

}
