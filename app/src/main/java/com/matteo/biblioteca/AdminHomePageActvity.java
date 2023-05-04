package com.matteo.biblioteca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdminHomePageActvity extends AppCompatActivity {

    TextView tipologia, email;
    Button logout;
    String SharedPreferences = "myfref_xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page_actvity);

        tipologia = findViewById(R.id.admintipologia);
        email = findViewById(R.id.adminemail);
        logout = findViewById(R.id.logoutadmin);

        android.content.SharedPreferences sharedPreferences = getSharedPreferences(SharedPreferences, MODE_PRIVATE);
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