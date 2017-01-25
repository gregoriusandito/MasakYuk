package com.insiteprojectid.masakyuk.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.insiteprojectid.masakyuk.R;
import com.insiteprojectid.masakyuk.controller.MenuUtama;
import com.insiteprojectid.masakyuk.model.BahanModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Gregorius Andito on 1/24/2017.
 */

public class BahanAdapter extends BaseAdapter{

    private Activity activity;
    private BahanAdapter bahanAdapter;
    private BahanModel bahanModel;
    private MenuUtama menuUtama;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;

    public BahanAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
            rowView = inflater.inflate(R.layout.list_bahan, null);
//        LayoutInflater inflater = activity.getLayoutInflater();
//        View rowView = inflater.inflate(R.layout.list_item,null,true);
        TextView banyakBahan = (TextView)rowView.findViewById(R.id.banyaknya);
        TextView satuanBahan = (TextView) rowView.findViewById(R.id.satuan);
        TextView namaBahan = (TextView) rowView.findViewById(R.id.nama_bahan);

        HashMap<String, String> daftar_bahan = new HashMap<String, String>();
        daftar_bahan = data.get(position);

        banyakBahan.setText(daftar_bahan.get(bahanModel.getBanyaknya()));
        satuanBahan.setText(daftar_bahan.get(bahanModel.getSatuan()));
        namaBahan.setText(daftar_bahan.get(bahanModel.getNama_bahan()));
        return rowView;
    }


}
