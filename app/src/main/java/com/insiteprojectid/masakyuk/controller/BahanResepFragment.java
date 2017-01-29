package com.insiteprojectid.masakyuk.controller;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.insiteprojectid.masakyuk.R;
import com.insiteprojectid.masakyuk.adapter.BahanAdapter;
import com.insiteprojectid.masakyuk.adapter.CaraAdapter;
import com.insiteprojectid.masakyuk.model.BahanModel;
import com.insiteprojectid.masakyuk.model.CaraModel;
import com.insiteprojectid.masakyuk.model.ResepModel;
import com.insiteprojectid.masakyuk.utils.UIUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class BahanResepFragment extends Fragment {

    private String id_resep;

    private ListView listBahan;
    ArrayList<HashMap<String, String>> DaftarBahan = new ArrayList<>();
    ProgressDialog pDialog;
    private static final String TAG = ResepActivity.class.getSimpleName();
    BahanModel bahanModel;
    ResepModel resepModel;
    BahanAdapter bahanAdapter;
    JSONArray jsonArray;

    public BahanResepFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rowView = inflater.inflate(R.layout.fragment_bahan_resep, container, false);

        id_resep = getActivity().getIntent().getStringExtra(resepModel.getId_resep());
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        listBahan = (ListView)rowView.findViewById(R.id.listBahan);

        loadBahan(id_resep);

        return rowView;
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void loadBahan(final String id_resep){
        String tag_string_req = "req_load_bahan";
        pDialog.setMessage("Memuat ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, BahanModel.GET_LIST_BAHAN_RESEP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Bahan Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    jsonArray = jObj.getJSONArray("bahan");
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject c = jsonArray.getJSONObject(i);
                        String id_bahan_utama = c.getString("id_bahan_utama");
                        String id_resep = c.getString("id_resep");
                        String banyaknya = c.getString("banyaknya");
                        String satuan = c.getString("satuan");
                        String nama_bahan = c.getString("nama_bahan");
                        HashMap<String,String> map_bahan = new HashMap<>();
                        map_bahan.put(bahanModel.id_resep,id_resep);
                        map_bahan.put(bahanModel.id_bahan_utama,id_bahan_utama);
                        map_bahan.put(bahanModel.banyaknya,banyaknya);
                        map_bahan.put(bahanModel.satuan,satuan);
                        map_bahan.put(bahanModel.nama_bahan,nama_bahan);
                        DaftarBahan.add(map_bahan);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                SetListBahan(DaftarBahan);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Bahan Load Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_resep", id_resep);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void SetListBahan(ArrayList<HashMap<String, String>> daftarBahan) {
        if(daftarBahan.size() == 0) {
            bahanAdapter = new BahanAdapter(getActivity(), new ArrayList<HashMap<String, String>>());
            listBahan.setAdapter(bahanAdapter);
            bahanAdapter.notifyDataSetInvalidated();
        } else {
            bahanAdapter = new BahanAdapter(getActivity(), daftarBahan);
            listBahan.setAdapter(bahanAdapter);
            bahanAdapter.notifyDataSetChanged();
        }
    }



}
