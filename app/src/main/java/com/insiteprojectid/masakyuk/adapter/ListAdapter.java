package com.insiteprojectid.masakyuk.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.insiteprojectid.masakyuk.R;
import com.insiteprojectid.masakyuk.controller.MenuUtama;
import com.insiteprojectid.masakyuk.model.ResepModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Gregorius Andito on 1/19/2017.
 */

public class ListAdapter extends BaseAdapter {

    private Activity activity;
    private ListAdapter listAdapter;
    private ResepModel resepModel;
    private MenuUtama menuUtama;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;

//    private final Activity context;
//    private final String[] menuItem;
//    private final Integer[] menuGambar;
//
//    public ListAdapter(Activity context, String[] menuItem, Integer[] menuGambar) {
//        super(context, R.layout.list_item, menuItem);
//        this.context = context;
//        this.menuItem = menuItem;
//        this.menuGambar = menuGambar;
//    }

    public ListAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data = d;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (convertView == null)
            rowView = inflater.inflate(R.layout.list_item, null);
//        LayoutInflater inflater = activity.getLayoutInflater();
//        View rowView = inflater.inflate(R.layout.list_item,null,true);
        TextView namaResep = (TextView)rowView.findViewById(R.id.nama_resep);
        ImageView gambarResep = (ImageView) rowView.findViewById(R.id.imageView);

        HashMap<String, String> daftar_resep = new HashMap<String, String>();
        daftar_resep = data.get(position);

        namaResep.setText(daftar_resep.get(resepModel.getJudul()));
        Picasso.with(activity.getApplicationContext()).load(daftar_resep.get(resepModel.getGambar())).into(gambarResep);
        return rowView;
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        LayoutInflater inflater = context.getLayoutInflater();
//        View rowView = inflater.inflate(R.layout.list_item,null,true);
//
//        TextView namaResep = (TextView)rowView.findViewById(R.id.nama_resep);
//        ImageView gambarResep = (ImageView) rowView.findViewById(R.id.imageView);
//
//        namaResep.setText(menuItem[position]);
//        gambarResep.setImageResource(menuGambar[position]);
//
//        return rowView;
//    }
}
