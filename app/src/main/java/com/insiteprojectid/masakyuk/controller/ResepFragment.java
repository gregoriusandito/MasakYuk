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


/**
 * A simple {@link Fragment} subclass.
 */
public class ResepFragment extends Fragment {

    String[] menuItem = {"Gudeg Jogja", "Ayam Bakar", "Soto Lamongan", "Mie Goreng Jawa", "Mie Aceh"};

    Integer[] menuGambar = {R.drawable.gudeg_jogja, R.drawable.ayam_bakar, R.drawable.soto_ayam, R.drawable.mie_goreng_jawa, R.drawable.mie_aceh};

    ListView lv;


    public ResepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_resep, container, false);
        ListAdapter adapter = new ListAdapter(getActivity(),menuItem,menuGambar);
        ListView lv = (ListView)rootView.findViewById(R.id.list_resep);
        lv.setAdapter(adapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity().getApplicationContext(),ResepActivity.class);
                String selectedItem = menuItem[position];
                Toast.makeText(getActivity().getApplicationContext(), selectedItem, Toast.LENGTH_SHORT).show();
                startActivity(i);
//                getActivity().finish();
            }
        });

        return rootView;
    }

}
