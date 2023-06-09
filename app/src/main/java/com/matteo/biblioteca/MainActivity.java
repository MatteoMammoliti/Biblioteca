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

    //Dichiarazione dei campi di input
    EditText email, password;
    Button login, register, adminLogin;
    ProgressDialog progressDialog;
    String SharedPreferences = "myfref_xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Dichiarazione delle variabili
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        register = (Button) findViewById(R.id.register);
        login = (Button) findViewById(R.id.login);
        progressDialog = new ProgressDialog(this);
        adminLogin = findViewById(R.id.admin_login);

        //Creazione del file per il salvataggio automatico delle credenziali per il secondo accesso
        SharedPreferences sharedPreferences = getSharedPreferences(SharedPreferences, MODE_PRIVATE);
        String emailPref = sharedPreferences.getString("email", null);
        String rolePref = sharedPreferences.getString("role", null);

        // Accesso automatico se l'email è salvata
        if (emailPref != null)
        {
            if (rolePref.equals("Utente"))
            {
                Intent intent = new Intent(getApplicationContext(), UserHomePageActivity.class);
                startActivity(intent);
                finish();
            }
        }

        // Passaggio all'activity di login per l'amministratore
        adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), AdminLoginActivity.class);
                startActivity(intent);

            }
        });

        // Passaggio all'activity di login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //Se il campo email è vuoto
                if(email.getText().toString().equals(""))
                {
                    // Errore
                    email.setError("L'indirizzo e-mail è obbligatorio. Compila tutti i campi");
                }
                //Se il campo email è vuoto
                else if(password.getText().toString().equals(""))
                {
                    // Errore
                    password.setError("La password è obbligatoria. Compila tutti i campi");
                }
                else
                {
                    //Se i campi sono entrambi riempiti
                    progressDialog.setTitle("Accesso in corso...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    login(); //Login
                }
            }
        });

        // Passaggio all'activity di registrazione
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);

            }
        });
    }

    //Funzione di Login
    void login()
    {

        //Ottenimento dei valori dai campi di input
        String emailParameter = email.getText().toString();
        String passwordParameter =password.getText().toString();

        //Richiamo del metodo di login dall'API di login
        Call<ResponseFormServer> call = ConnectionDatabase.getClient().create(Methods.class).loginMethod(emailParameter, passwordParameter);
        call.enqueue(new Callback<ResponseFormServer>() {
            @Override
            public void onResponse(Call<ResponseFormServer> call, Response<ResponseFormServer> response) {

                //Se la chiamata va a buon fine
                if(response.code() == 200)
                {
                    if(response.body().getStatus().equals("OK"))
                    {
                        //Se la select trova un match nel DB
                        if(response.body().getResultCode() == 1)
                        {
                            //Salvataggio automatico delle credenziali
                            SharedPreferences sharedPreferences = getSharedPreferences(SharedPreferences, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString("email", response.body().getEmail());
                            editor.putString("role", response.body().getRole());
                            editor.apply();

                            Toast.makeText(getApplicationContext(), "Benvenuto", Toast.LENGTH_LONG).show();
                            String role = response.body().getRole();

                            //Accesso
                            switch (role)
                            {
                                case "Utente":
                                    Intent intent = new Intent(getApplicationContext(), UserHomePageActivity.class);
                                    startActivity(intent);
                                    finish();
                                    progressDialog.dismiss();
                                    break;
                            }
                        }

                        //Se la select non trova alcun match nel DB
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Accesso fallito. Credenziali errate!", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                }
                else
                {

                    //NESSUNA CONNESSIONE

                }

            }

            @Override
            public void onFailure(Call<ResponseFormServer> call, Throwable t) {

                //NESSUNA CONNESSIONE

            }
        });
    }
}