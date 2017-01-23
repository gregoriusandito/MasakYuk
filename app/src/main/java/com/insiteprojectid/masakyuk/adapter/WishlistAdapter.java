package com.insiteprojectid.masakyuk.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.insiteprojectid.masakyuk.R;

/**
 * Created by Gregorius Andito on 1/20/2017.
 */

public class WishlistAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] menuItem;
    private final String[] menuJenis;

    public WishlistAdapter(Activity context, String[] menuItem, String[] menuJenis) {
        super(context, R.layout.list_item, menuItem);
        this.context = context;
        this.menuItem = menuItem;
        this.menuJenis = menuJenis;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_jenis,null,true);

        TextView namaResep = (TextView)rowView.findViewById(R.id.wish_nama_resep);
        TextView jenisResep = (TextView) rowView.findViewById(R.id.wish_jenis_resep);

        namaResep.setText(menuItem[position]);
        jenisResep.setText(menuJenis[position]);

        return rowView;
    }

}
