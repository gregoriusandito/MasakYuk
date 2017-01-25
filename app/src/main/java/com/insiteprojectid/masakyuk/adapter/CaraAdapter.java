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
import com.insiteprojectid.masakyuk.model.CaraModel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Gregorius Andito on 1/24/2017.
 */

public class CaraAdapter extends BaseAdapter{

    private Activity activity;
    private CaraAdapter caraAdapter;
    private CaraModel caraModel;
    private MenuUtama menuUtama;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;

    public CaraAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
            rowView = inflater.inflate(R.layout.list_cara, null);
        TextView no = (TextView) rowView.findViewById(R.id.no);
        TextView caraMasak = (TextView) rowView.findViewById(R.id.cara_memasak);

        HashMap<String, String> daftar_cara = new HashMap<String, String>();
        daftar_cara = data.get(position);

        no.setText("" + (position + 1) + ". ");
        caraMasak.setText(daftar_cara.get(caraModel.getCara()));
        return rowView;
    }


}
