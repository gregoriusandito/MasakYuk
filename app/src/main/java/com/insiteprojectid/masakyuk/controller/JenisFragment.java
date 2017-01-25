package com.insiteprojectid.masakyuk.controller;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.insiteprojectid.masakyuk.R;
import com.insiteprojectid.masakyuk.adapter.ListAdapter;
import com.insiteprojectid.masakyuk.adapter.TypeAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class JenisFragment extends Fragment {

    String[] menuItem = {"Makanan Pembuka", "Makanan Utama", "Makanan Penutup"};

    Integer[] menuGambar = {R.drawable.lumpia_semarang, R.drawable.ayam_bakar, R.drawable.es_buah};

    ListView lv;

    public static ArrayList<String> jenis = new ArrayList<>();

    public JenisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_jenis, container, false);
        TypeAdapter adapter = new TypeAdapter(getActivity(),menuItem,menuGambar);
        ListView lv = (ListView)rootView.findViewById(R.id.list_jenis);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = menuItem[position];
                Intent intent = new Intent(getActivity().getApplicationContext(), DetailJenisActivity.class);
                if(item == "Makanan Pembuka"){
                    intent.putExtra("detail_code","pembuka");
                } else if(item == "Makanan Utama") {
                    intent.putExtra("detail_code","utama");
                } else if (item == "Makanan Penutup") {
                    intent.putExtra("detail_code","penutup");
                }
                startActivity(intent);
            }
        });

        return rootView;
    }

}
