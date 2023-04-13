package com.matteo.biblioteca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button login, register;
    DBConnection database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username_l);
        password = (EditText) findViewById(R.id.password_l);
        login = (Button) findViewById(R.id.login_l);
        register = (Button) findViewById(R.id.register_l);
        database = new DBConnection(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user_name = username.getText().toString();
                String pass = password.getText().toString();

                if(user_name.equals("") || pass.equals(""))
                {
                    Toast.makeText(LoginActivity.this, "Compila tutti i campi!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    boolean checkuserpass = database.checkusernamepassword(user_name, pass);
                    if(checkuserpass == true)
                    {
                        Toast.makeText(LoginActivity.this, "Accesso completato. Caricamento.....", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                        intent.putExtra("name", username.getText().toString());
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "Credenziali non valide!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });
    }
}