package com.insiteprojectid.masakyuk.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

        TextView namaResep = (TextView)rowView.findViewById(R.id.nama_resep);
        ImageView gambarResep = (ImageView) rowView.findViewById(R.id.imageView);

        HashMap<String, String> daftar_resep = new HashMap<String, String>();
        daftar_resep = data.get(position);

        namaResep.setText(daftar_resep.get(resepModel.getJudul()));
        Picasso.with(activity.getApplicationContext()).load(daftar_resep.get(resepModel.getGambar())).into(gambarResep);

        final String judul_inner = daftar_resep.get(resepModel.getJudul());
        ImageView shareIcon = (ImageView)rowView.findViewById(R.id.share);
        shareIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Intent.ACTION_SEND);
                a.setType("text/plain");
                a.putExtra(Intent.EXTRA_SUBJECT, "Masak Yuk");
                a.putExtra(Intent.EXTRA_TEXT, "Masak "+judul_inner+" yuk! Download aplikasi Masak Yuk di Play Store sekarang juga.");
                activity.startActivity(Intent.createChooser(a,"Share it"));
            }
        });
        return rowView;
    }

}
