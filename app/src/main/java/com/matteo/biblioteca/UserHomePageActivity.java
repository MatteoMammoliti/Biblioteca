package com.matteo.biblioteca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserHomePageActivity extends AppCompatActivity {

    TextView tipologia, email;
    Button logout;
    String SharedPreferences = "myfref_xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_home_page);

        tipologia = findViewById(R.id.usertipologia);
        email = findViewById(R.id.useremail);
        logout = findViewById(R.id.logout);

        SharedPreferences sharedPreferences = getSharedPreferences(SharedPreferences, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        tipologia.setText(sharedPreferences.getString("role", "Utente non registrato"));
        email.setText(sharedPreferences.getString("email", "Utente non registrato"));

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.commit();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }
}