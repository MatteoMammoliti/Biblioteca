package com.matteo.biblioteca;

import android.os.StrictMode;
import java.sql.Connection;

public class DBConnection {
    Connection connection;
    String uname, password, ip, port, database;

    public Connection connectionclass() {

        ip = "127.0.0.1";
        database = "dbbiblioteca";
        uname = "root";
        password = "";
        port = "3306";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }

}
