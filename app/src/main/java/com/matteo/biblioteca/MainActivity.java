package com.matteo.biblioteca;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText email, password;
    Button login, register;
    ProgressDialog progressDialog;
    String SharedPreferences = "myfref_xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        register = (Button) findViewById(R.id.register);
        login = (Button) findViewById(R.id.login);
        progressDialog = new ProgressDialog(this);

        SharedPreferences sharedPreferences = getSharedPreferences(SharedPreferences, MODE_PRIVATE);
        String emailPref = sharedPreferences.getString("email" ,null);
        String rolePref = sharedPreferences.getString("role" ,null);

        if(emailPref != null)
        {
            if(rolePref.equals("Utente"))
            {
                Intent intent = new Intent(getApplicationContext(), UserHomePageActivity.class);
                startActivity(intent);
                finish();
            }
            else if(rolePref.equals("Admin"))
            {
                Intent intent = new Intent(getApplicationContext(), AdminHomePageActvity.class);
                startActivity(intent);
                finish();
            }
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(email.getText().toString().equals(""))
                {
                    email.setError("L'indirizzo e-mail è obbligatorio. Compila tutti i campi");
                }
                else if(password.getText().toString().equals(""))
                {
                    password.setError("La password è obbligatoria. Compila tutti i campi");
                }
                else
                {
                    progressDialog.setTitle("Accesso in corso...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    login();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);

            }
        });
    }

    void login()
    {
        String emailParameter = email.getText().toString();
        String passwordParameter =password.getText().toString();

        Call<ResponseFormServer> call = ConnectionDatabase.getClient().create(Methods.class).loginMethod(emailParameter, passwordParameter);
        call.enqueue(new Callback<ResponseFormServer>() {
            @Override
            public void onResponse(Call<ResponseFormServer> call, Response<ResponseFormServer> response) {

                if(response.code() == 200)
                {
                    if(response.body().getStatus().equals("OK"))
                    {
                        if(response.body().getResultCode() == 1)
                        {
                            SharedPreferences sharedPreferences = getSharedPreferences(SharedPreferences, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString("email", response.body().getEmail());
                            editor.putString("role", response.body().getRole());
                            editor.apply();

                            Toast.makeText(getApplicationContext(), "Benvenuto", Toast.LENGTH_LONG).show();
                            String role = response.body().getRole();

                            switch (role)
                            {
                                case "Utente":
                                    Intent intent = new Intent(getApplicationContext(), UserHomePageActivity.class);
                                    startActivity(intent);
                                    finish();
                                    progressDialog.dismiss();
                                    break;

                                case "Admin":
                                    Intent i = new Intent(getApplicationContext(), AdminHomePageActvity.class);
                                    startActivity(i);
                                    finish();
                                    progressDialog.dismiss();
                                    break;
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Accesso fallito. Credenziali errate!", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                else
                {


                }

            }

            @Override
            public void onFailure(Call<ResponseFormServer> call, Throwable t) {

            }
        });

    }
}