package com.insiteprojectid.masakyuk.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.insiteprojectid.masakyuk.R;
import com.insiteprojectid.masakyuk.adapter.ListAdapter;
import com.insiteprojectid.masakyuk.adapter.WishlistAdapter;

import java.util.List;

public class WishlistActivity extends AppCompatActivity {

    String[] menuItem = {"Lumpia Semarang", "Ayam Bakar", "Es Buah"};
    String[] jenisItem = {"Makanan Pembuka", "Makanan Utama", "Makanan Penutup"};
    ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        WishlistAdapter adapter = new WishlistAdapter(this,menuItem,jenisItem);
        ListView lv = (ListView)findViewById(R.id.wish_list);
        lv.setAdapter(adapter);
    }





}
