package com.insiteprojectid.masakyuk.controller;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.insiteprojectid.masakyuk.R;
import com.insiteprojectid.masakyuk.adapter.ListAdapter;
import com.insiteprojectid.masakyuk.model.ResepModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class RekomendasiFragment extends Fragment {

    ListView lv;
    ArrayList<HashMap<String, String>> DaftarResep = new ArrayList<>();
    ProgressDialog pDialog;
    private static final String TAG = MenuUtama.class.getSimpleName();
    ResepModel resepModel;
    ListAdapter listAdapter;
    JSONArray jsonArray;

    public RekomendasiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_rekomendasi, container, false);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        // Inflate the layout for this fragment
        lv = (ListView)rootView.findViewById(R.id.list_rekomendasi);
        DaftarResep = new ArrayList<>();

        loadResepRekomendasi();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,String> map = DaftarResep.get(position);
                Intent i = new Intent(getActivity().getApplicationContext(),ResepActivity.class);
                i.putExtra(resepModel.id_resep,map.get(resepModel.id_resep));
                i.putExtra(resepModel.link_youtube,map.get(resepModel.link_youtube));
                i.putExtra(resepModel.judul,map.get(resepModel.judul));
                startActivity(i);
//                getActivity().finish();
            }
        });

        return rootView;
    }

    private void loadResepRekomendasi(){
        String tag_string_req = "req_load_resep";
        pDialog.setMessage("Memuat ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, ResepModel.GET_REKOMENDASI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Resep Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    jsonArray = jObj.getJSONArray("resep");
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject c = jsonArray.getJSONObject(i);
                        String id_resep = c.getString("id_resep");
                        String id_cat = c.getString("id_cat");
                        String judul = c.getString("judul");
                        String gambar = ResepModel.BASE_IMG+c.getString("gambar");
                        String link_youtube = c.getString("link_youtube");
                        String rekomendasi = c.getString("rekomendasi");
                        HashMap<String,String> map_resep = new HashMap<>();
                        map_resep.put(resepModel.id_resep,id_resep);
                        map_resep.put(resepModel.id_cat,id_cat);
                        map_resep.put(resepModel.judul,judul);
                        map_resep.put(resepModel.gambar,gambar);
                        map_resep.put(resepModel.link_youtube,link_youtube);
                        map_resep.put(resepModel.rekomendasi,rekomendasi);
                        DaftarResep.add(map_resep);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                SetListResep(DaftarResep);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Resep Load Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
//                params.put("id_kota", id_kota);
//                params.put("id_wisata", id_wisata);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void SetListResep(ArrayList<HashMap<String, String>> daftarResep) {
        if(daftarResep.size() == 0) {
            listAdapter = new ListAdapter(getActivity(), new ArrayList<HashMap<String, String>>());
//            emptyTV.setText("Tidak ada komentar");
            lv.setAdapter(listAdapter);
            listAdapter.notifyDataSetInvalidated();
        } else {
            listAdapter = new ListAdapter(getActivity(), daftarResep);
            lv.setAdapter(listAdapter);
//            UIUtils.setListViewHeightBasedOnItems(komentar);
            listAdapter.notifyDataSetChanged();
        }

    }


}
