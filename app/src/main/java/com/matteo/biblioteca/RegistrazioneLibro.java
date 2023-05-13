package com.matteo.biblioteca;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrazioneLibro extends AppCompatActivity {

    EditText titolo, isbn, autore, lingua, data_pubblicazione;
    Spinner genere, numero_scaffale;
    Button registra;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione_libro);

        titolo = (EditText) findViewById(R.id.titolo);
        isbn = (EditText) findViewById(R.id.isbn);
        autore = (EditText) findViewById(R.id.autore);
        lingua = (EditText) findViewById(R.id.lingua);
        data_pubblicazione = (EditText) findViewById(R.id.data);
        genere = (Spinner) findViewById(R.id.spinnerGeneri);
        numero_scaffale = (Spinner) findViewById(R.id.spinnerScaffale);
        registra = (Button) findViewById(R.id.register_book);
        progressDialog = new ProgressDialog(this);

        registra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(titolo.getText().toString().equals(""))
                {
                    titolo.setText("Titolo richiesto. Compila tutti i campi!");
                }
                else if(isbn.getText().toString().equals(""))
                {
                    isbn.setText("ISBN richiesto. Compila tutti i campi!");
                }
                else if(autore.getText().toString().equals(""))
                {
                    autore.setText("Autore richiesto. Compila tutti i campi!");
                }
                else if(lingua.getText().toString().equals(""))
                {
                    lingua.setText("Lingua richiesta. Compila tutti i campi!");
                }
                else if(data_pubblicazione.getText().toString().equals(""))
                {
                    data_pubblicazione.setText("Data richiesta. Compila tutti i campi!");
                }
                else if(genere.getSelectedItem().toString().equals("Seleziona"))
                {
                    Toast.makeText(getApplicationContext(), "Genere necessario. Compila tutti i campi!", Toast.LENGTH_LONG).show();
                }
                else if(numero_scaffale.getSelectedItem().toString().equals("Seleziona"))
                {
                    Toast.makeText(getApplicationContext(), "Numero dello scaffale necessario. Compila tutti i campi!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    progressDialog.setTitle("Registrazione del libro in corso...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    register();
                }
            }
        });
    }

    void register() {

        String titoloParameter = titolo.getText().toString();
        String isbnParameter = isbn.getText().toString();
        String autoreParameter = autore.getText().toString();
        String linguaParameter = lingua.getText().toString();
        String dataParameter = data_pubblicazione.getText().toString();
        String genereParameter = genere.getSelectedItem().toString();
        String numeroScaffaleParameter = numero_scaffale.getSelectedItem().toString();

        Call<ResponseFormServer> call = ConnectionDatabase.getClient().create(Methods.class).registrazioneLibro(titoloParameter, isbnParameter, autoreParameter,
                linguaParameter, dataParameter, genereParameter, numeroScaffaleParameter);

        call.enqueue(new Callback<ResponseFormServer>() {
            @Override
            public void onResponse(Call<ResponseFormServer> call, Response<ResponseFormServer> response) {

                if(response.code() == 200)
                {
                    if(response.body().getStatus().equals("OK"))
                    {
                        if(response.body().getResultCode() == 1)
                        {
                            Toast.makeText(getApplicationContext(), "Libro registrato correttamente.", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), AdminHomePage.class);
                            startActivity(intent);
                            finish();
                            progressDialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Il libro è già presente nel Database!", Toast.LENGTH_LONG).show();
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