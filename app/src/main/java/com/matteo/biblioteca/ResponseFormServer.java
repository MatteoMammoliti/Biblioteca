package com.matteo.biblioteca;

import com.google.gson.annotations.SerializedName;

public class ResponseFormServer {

    @SerializedName("status")
    String status;

    @SerializedName("email")
    String email;

    @SerializedName("resultCode")
    int resultCode;

    @SerializedName("role")
    String role;

    public String getStatus()
    {
        return status;
    }

    public String getEmail()
    {
        return email;
    }

    public int getResultCode()
    {
        return resultCode;
    }

    public String getRole()
    {
        return role;
    }
}
