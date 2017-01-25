package com.insiteprojectid.masakyuk.controller;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.insiteprojectid.masakyuk.R;
import com.insiteprojectid.masakyuk.adapter.ListAdapter;
import com.insiteprojectid.masakyuk.adapter.WishlistAdapter;
import com.insiteprojectid.masakyuk.model.WishListModel;

import java.util.List;

public class WishlistActivity extends AppCompatActivity {

//    String[] menuItem = {"Lumpia Semarang", "Ayam Bakar", "Es Buah"};
//    String[] jenisItem = {"Makanan Pembuka", "Makanan Utama", "Makanan Penutup"};
    ListView lv;
    WishListModel wm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        wm = new WishListModel(this);

        ListView lv = (ListView)findViewById(R.id.wish_list);
        WishlistAdapter adapter = new WishlistAdapter(this, wm.getListResep());
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }





}
