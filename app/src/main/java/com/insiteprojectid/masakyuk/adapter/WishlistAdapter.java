package com.insiteprojectid.masakyuk.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.insiteprojectid.masakyuk.R;
import com.insiteprojectid.masakyuk.model.WishListModel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Gregorius Andito on 1/20/2017.
 */

public class WishlistAdapter extends CursorAdapter {

    public WishlistAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_wish, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView namaResep = (TextView)view.findViewById(R.id.wish_nama_resep);
        TextView jenisResep = (TextView)view.findViewById(R.id.wish_jenis_resep);

        String judul = cursor.getString(cursor.getColumnIndex("judul"));
        String id_cat = cursor.getString(cursor.getColumnIndexOrThrow("id_cat"));

        namaResep.setText(judul);

        if(id_cat.equals("1")){
            jenisResep.setText("Makanan Pembuka");
        } else if(id_cat.equals("2")){
            jenisResep.setText("Makanan Utama");
        } else if(id_cat.equals("3")){
            jenisResep.setText("Makanan Penutup");
        }

    }

}
