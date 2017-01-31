package com.insiteprojectid.masakyuk.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.insiteprojectid.masakyuk.R;
import com.insiteprojectid.masakyuk.controller.MenuUtama;
import com.insiteprojectid.masakyuk.model.ResepModel;
import com.insiteprojectid.masakyuk.model.WishListModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Gregorius Andito on 1/19/2017.
 */

public class RekomendasiAdapter extends BaseAdapter {

    private Activity activity;
    private RekomendasiAdapter listAdapter;
    private ResepModel resepModel;
    private MenuUtama menuUtama;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;
    private WishListModel db;

    public RekomendasiAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data = d;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        db = new WishListModel(RekomendasiAdapter.inflater.getContext());
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
            rowView = inflater.inflate(R.layout.list_rekomendasi, null);

        TextView namaResep = (TextView)rowView.findViewById(R.id.nama_resep_rekomendasi);
        ImageView gambarResep = (ImageView) rowView.findViewById(R.id.gambarRekomendasi);

        HashMap<String, String> daftar_resep = new HashMap<String, String>();
        daftar_resep = data.get(position);

        namaResep.setText(daftar_resep.get(resepModel.getJudul()));
        Picasso.with(activity.getApplicationContext()).load(daftar_resep.get(resepModel.getGambar())).into(gambarResep);

        return rowView;
    }


}
