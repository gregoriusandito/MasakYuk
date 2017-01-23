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
 * Created by Gregorius Andito on 1/19/2017.
 */

public class TypeAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] menuItem;
    private final Integer[] menuGambar;

    public TypeAdapter(Activity context, String[] menuItem, Integer[] menuGambar) {
        super(context, R.layout.list_item, menuItem);
        this.context = context;
        this.menuItem = menuItem;
        this.menuGambar = menuGambar;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_jenis,null,true);

        TextView namaResep = (TextView)rowView.findViewById(R.id.nama_resep);
        ImageView gambarResep = (ImageView) rowView.findViewById(R.id.imageView);

        namaResep.setText(menuItem[position]);
        gambarResep.setImageResource(menuGambar[position]);

        return rowView;
    }
}
