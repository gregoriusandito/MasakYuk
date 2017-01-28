package com.insiteprojectid.masakyuk.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.insiteprojectid.masakyuk.R;
import com.insiteprojectid.masakyuk.controller.MenuUtama;
import com.insiteprojectid.masakyuk.model.BahanModel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Gregorius Andito on 1/27/2017.
 */

public class TotalBahanAdapter extends BaseAdapter {
    private Activity activity;
    private BahanAdapter bahanAdapter;
    private BahanModel bahanModel;
    private MenuUtama menuUtama;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;

    public TotalBahanAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
            rowView = inflater.inflate(R.layout.list_total_bahan, null);
        TextView banyakBahan = (TextView)rowView.findViewById(R.id.banyaknya_total);
        TextView satuanBahan = (TextView) rowView.findViewById(R.id.satuan_total);
        TextView namaBahan = (TextView) rowView.findViewById(R.id.nama_bahan_total);

        HashMap<String, String> daftar_bahan = new HashMap<String, String>();
        daftar_bahan = data.get(position);

        banyakBahan.setText(daftar_bahan.get(bahanModel.getBanyaknya()));
        satuanBahan.setText(daftar_bahan.get(bahanModel.getSatuan()));
        namaBahan.setText(daftar_bahan.get(bahanModel.getNama_bahan()));
        return rowView;
    }


}
