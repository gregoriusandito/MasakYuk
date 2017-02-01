package com.insiteprojectid.masakyuk.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.insiteprojectid.masakyuk.R;

public class MasukanActivity extends AppCompatActivity {

    private Button btnUlas, btnTidak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masukan);
        btnUlas = (Button)findViewById(R.id.beriUlasan);
        btnTidak = (Button)findViewById(R.id.tidak);

        btnTidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnUlas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        "Maaf, fitur ini belum tersedia", Toast.LENGTH_LONG).show();
            }
        });

    }
}
