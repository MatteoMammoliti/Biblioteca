package com.matteo.biblioteca;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Methods {

    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseFormServer>loginMethod(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("register.php")
    Call<ResponseFormServer>registerMethod(@Field("nome_r") String nome, @Field("cognome_r") String cognome,
                                           @Field("email_r") String email, @Field("password_r") String password);
}
