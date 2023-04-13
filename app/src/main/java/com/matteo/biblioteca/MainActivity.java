package com.matteo.biblioteca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username, password, cpassword;
    Button login, register;
    DBConnection database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        cpassword = (EditText) findViewById(R.id.cpassword);
        register = (Button) findViewById(R.id.register);
        login = (Button) findViewById(R.id.login);
        database = new DBConnection(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user_name = username.getText().toString();
                String pass = password.getText().toString();
                String cpass = cpassword.getText().toString();

                if(user_name.equals("") || pass.equals("") || cpass.equals(""))
                {
                    Toast.makeText(MainActivity.this, "Compila tutti i campi!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(pass.equals(cpass))
                    {
                        Boolean checkuser = database.checkusername(user_name);

                        if(checkuser == false)
                        {
                            Boolean insert = database.insertData(user_name, pass);

                            if(insert == true)
                            {
                                Toast.makeText(MainActivity.this, "L'utente è stato registrato!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, "Registrazione fallita. Riprovare!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Utente già registrato. Accedi!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Le password non coincidono!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

            }
        });
    }
}