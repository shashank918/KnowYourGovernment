package com.example.naray.knowyourgovernment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by naray on 4/3/2017.
 */

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
