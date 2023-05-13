package com.matteo.biblioteca;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText nome, cognome, email, password;
    Button login, register;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        nome = (EditText) findViewById(R.id.nome_r);
        cognome = (EditText) findViewById(R.id.cognome_r);
        email = (EditText) findViewById(R.id.email_r);
        password = (EditText) findViewById(R.id.password_r);
        login = (Button) findViewById(R.id.login_r);
        register = (Button) findViewById(R.id.register_r);
        progressDialog = new ProgressDialog(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(email.getText().toString().equals(""))
                {
                    email.setText("Indirizzo email richiesto. Compila tutti i campi!");
                }
                else if(password.getText().toString().equals(""))
                {
                    password.setText("Indirizzo email richiesto. Compila tutti i campi!");
                }
                else if(nome.getText().toString().equals(""))
                {
                    nome.setText("Indirizzo email richiesto. Compila tutti i campi!");
                }
                else
                {
                    progressDialog.setTitle("Registrazione in corso...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    register();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });
    }

    void register() {

        String nomeParameter = nome.getText().toString();
        String cognomeParameter = cognome.getText().toString();
        String emailParameter = email.getText().toString();
        String passwordParameter =password.getText().toString();

        Call<ResponseFormServer> call = ConnectionDatabase.getClient().create(Methods.class).
                registerMethod(nomeParameter, cognomeParameter, emailParameter, passwordParameter);

        call.enqueue(new Callback<ResponseFormServer>() {
            @Override
            public void onResponse(Call<ResponseFormServer> call, Response<ResponseFormServer> response) {

                if(response.code() == 200)
                {
                    if(response.body().getStatus().equals("OK"))
                    {
                        if(response.body().getResultCode() == 1)
                        {
                            Toast.makeText(getApplicationContext(), "Utente registrato. Benvenuto!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), UserHomePageActivity.class);
                            startActivity(intent);
                            finish();
                            progressDialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "L'utente esiste già. Accedi!", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                }
                else
                {
                    //se non è 200
                }

            }

            @Override
            public void onFailure(Call<ResponseFormServer> call, Throwable t) {

                //se non è 200

            }
        });

    }
}