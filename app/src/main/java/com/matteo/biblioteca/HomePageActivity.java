package com.matteo.biblioteca;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        TextView username = (TextView) findViewById(R.id.username_afterlogin);
        username.setText(getIntent().getExtras().getString("name"));

    }
}