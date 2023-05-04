package com.matteo.biblioteca;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectionDatabase {

    private static final String URL="https://www.confsalvvff.it/php/login.php";
    private static Retrofit retrofit = null;

    public static Retrofit getClient()
    {
        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit;
    }

}
