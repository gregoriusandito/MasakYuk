package com.insiteprojectid.masakyuk.controller;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.insiteprojectid.masakyuk.R;
import com.insiteprojectid.masakyuk.adapter.ListAdapter;
import com.insiteprojectid.masakyuk.adapter.WishlistAdapter;
import com.insiteprojectid.masakyuk.model.ResepModel;
import com.insiteprojectid.masakyuk.model.WishListModel;
import com.insiteprojectid.masakyuk.utils.UIUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WishlistActivity extends AppCompatActivity {

    private ListView lv;
    private WishListModel wm;
    private ResepModel resepModel;
    private ImageView emptyList;
    private Button lihatHarga, lihatBahan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        wm = new WishListModel(this);

        lv = (ListView)findViewById(R.id.wish_list);
        emptyList = (ImageView)findViewById(R.id.emptyImage);
        lihatBahan = (Button)findViewById(R.id.btnLihatBahan);
        lihatHarga = (Button)findViewById(R.id.btnLihatHarga);

        final WishlistAdapter adapter = new WishlistAdapter(this, wm.getListResep());
        if(wm.getListResep().getCount() == 0) {
            lv.setAdapter(adapter);
            adapter.notifyDataSetInvalidated();
        } else {
            lv.setAdapter(adapter);
            emptyList.setVisibility(View.GONE);
            UIUtils.setListViewHeightBasedOnItems(lv);
            adapter.notifyDataSetChanged();
        }


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = adapter.getCursor();
                cursor.moveToPosition(position);
                String id_resep = cursor.getString(cursor.getColumnIndex("id_resep"));
                String id_cat = cursor.getString(cursor.getColumnIndex("id_cat"));
                String link_youtube = cursor.getString(cursor.getColumnIndex("link_youtube"));
                String judul = cursor.getString(cursor.getColumnIndex("judul"));
                String gambar = cursor.getString(cursor.getColumnIndex("gambar"));
                String rekomendasi = cursor.getString(cursor.getColumnIndex("rekomendasi"));
                Intent i = new Intent(getApplicationContext(),ResepActivity.class);
                i.putExtra(resepModel.id_resep,id_resep);
                i.putExtra(resepModel.id_cat,id_cat);
                i.putExtra(resepModel.link_youtube,link_youtube);
                i.putExtra(resepModel.judul,judul);
                i.putExtra(resepModel.gambar,gambar);
                i.putExtra(resepModel.rekomendasi,rekomendasi);
                startActivity(i);
            }
        });

        lihatBahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wm.getListResep().getCount() == 0) {
                    // intentionally left blank
                } else if (wm.getListResep().getCount() == 1) {
                    Cursor cursor = adapter.getCursor();
                    cursor.moveToPosition(0);
                    String id_resep = cursor.getString(cursor.getColumnIndex("id_resep"));
                    Intent i = new Intent(getApplicationContext(),TotalBahanActivity.class);
                    i.putExtra("id_resep",id_resep);
                    startActivity(i);
                } else if (wm.getListResep().getCount() == 2) {
                    // first
                    Cursor one = adapter.getCursor();
                    one.moveToPosition(0);
                    String id_resep = one.getString(one.getColumnIndex("id_resep"));

                    //second
                    Cursor second = adapter.getCursor();
                    second.moveToPosition(1);
                    String id_resep_2 = second.getString(one.getColumnIndex("id_resep"));

                    Intent i = new Intent(getApplicationContext(),TotalBahanActivity.class);
                    i.putExtra("id_resep",id_resep);
                    i.putExtra("id_resep_2",id_resep_2);
                    startActivity(i);
                } else if (wm.getListResep().getCount() == 3) {
                    // first
                    Cursor one = adapter.getCursor();
                    one.moveToPosition(0);
                    String id_resep = one.getString(one.getColumnIndex("id_resep"));

                    //second
                    Cursor second = adapter.getCursor();
                    second.moveToPosition(1);
                    String id_resep_2 = second.getString(second.getColumnIndex("id_resep"));

                    Cursor third = adapter.getCursor();
                    third.moveToPosition(2);
                    String id_resep_3 = third.getString(third.getColumnIndex("id_resep"));

                    Intent i = new Intent(getApplicationContext(),TotalBahanActivity.class);
                    i.putExtra("id_resep",id_resep);
                    i.putExtra("id_resep_2",id_resep_2);
                    i.putExtra("id_resep_3",id_resep_3);
                    startActivity(i);
                }
            }
        });

        lihatHarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wm.getListResep().getCount() == 0) {
                    // intentionally left blank
                } else if (wm.getListResep().getCount() == 1) {
                    Cursor cursor = adapter.getCursor();
                    cursor.moveToPosition(0);
                    String id_resep = cursor.getString(cursor.getColumnIndex("id_resep"));
                    Intent i = new Intent(getApplicationContext(),TotalHargaActivity.class);
                    i.putExtra("id_resep",id_resep);
                    startActivity(i);
                } else if (wm.getListResep().getCount() == 2) {
                    // first
                    Cursor one = adapter.getCursor();
                    one.moveToPosition(0);
                    String id_resep = one.getString(one.getColumnIndex("id_resep"));

                    //second
                    Cursor second = adapter.getCursor();
                    second.moveToPosition(1);
                    String id_resep_2 = second.getString(one.getColumnIndex("id_resep"));

                    Intent i = new Intent(getApplicationContext(),TotalHargaActivity.class);
                    i.putExtra("id_resep",id_resep);
                    i.putExtra("id_resep_2",id_resep_2);
                    startActivity(i);
                } else if (wm.getListResep().getCount() == 3) {
                    // first
                    Cursor one = adapter.getCursor();
                    one.moveToPosition(0);
                    String id_resep = one.getString(one.getColumnIndex("id_resep"));

                    //second
                    Cursor second = adapter.getCursor();
                    second.moveToPosition(1);
                    String id_resep_2 = second.getString(second.getColumnIndex("id_resep"));

                    Cursor third = adapter.getCursor();
                    third.moveToPosition(2);
                    String id_resep_3 = third.getString(third.getColumnIndex("id_resep"));

                    Intent i = new Intent(getApplicationContext(), TotalHargaActivity.class);
                    i.putExtra("id_resep", id_resep);
                    i.putExtra("id_resep_2", id_resep_2);
                    i.putExtra("id_resep_3", id_resep_3);
                    startActivity(i);
                }
            }
        });
    }
}
