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

public class TotalHargaAdapter extends BaseAdapter {
    private Activity activity;
    private BahanAdapter bahanAdapter;
    private BahanModel bahanModel;
    private MenuUtama menuUtama;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;
    private Integer jumlah, per, harga_per_satuan, total;

    public TotalHargaAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
            rowView = inflater.inflate(R.layout.list_total_harga, null);
        TextView banyakBahan = (TextView)rowView.findViewById(R.id.banyaknya_bahan_total);
        TextView satuanBahan = (TextView) rowView.findViewById(R.id.satuan_total);
        TextView namaBahan = (TextView) rowView.findViewById(R.id.nama_bahan_total);
        TextView hargaBahan = (TextView) rowView.findViewById(R.id.harga_total);

        HashMap<String, String> daftar_bahan = new HashMap<String, String>();
        daftar_bahan = data.get(position);

        jumlah = jumlah.parseInt(daftar_bahan.get(bahanModel.getBanyaknya()));
        per = per.parseInt(daftar_bahan.get(bahanModel.getPer()));
        harga_per_satuan = harga_per_satuan.parseInt(daftar_bahan.get(bahanModel.getHarga_per_satuan()));

        total = (jumlah/per) * (harga_per_satuan);
        String total_str= total.toString();

        banyakBahan.setText(daftar_bahan.get(bahanModel.getBanyaknya()));
        satuanBahan.setText(daftar_bahan.get(bahanModel.getSatuan()));
        namaBahan.setText(daftar_bahan.get(bahanModel.getNama_bahan()));
        hargaBahan.setText(total_str);
        return rowView;
    }


}
