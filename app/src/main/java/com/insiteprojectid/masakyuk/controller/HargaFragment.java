package com.insiteprojectid.masakyuk.controller;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.insiteprojectid.masakyuk.R;
import com.insiteprojectid.masakyuk.adapter.TotalHargaAdapter;
import com.insiteprojectid.masakyuk.model.BahanModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class HargaFragment extends Fragment {

    private TextView hargaTotal;
    private String rawHarga, id_resep_1, id_resep_2, id_resep_3;;
    private Double temp_harga_per_satuan, temp_per, temp_banyaknya, harga, end;
    private Long longTemp, hargaFinal;

    ProgressDialog pDialog;

    private static final String TAG = TotalHargaActivity.class.getSimpleName();

    private JSONArray jsonArray;

    public HargaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rowView = inflater.inflate(R.layout.fragment_harga, container, false);
        hargaTotal = (TextView)rowView.findViewById(R.id.harga_total);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        id_resep_1 = getActivity().getIntent().getStringExtra("id_resep");
        id_resep_2 = getActivity().getIntent().getStringExtra("id_resep_2");
        id_resep_3 = getActivity().getIntent().getStringExtra("id_resep_3");

        loadTotalHarga(id_resep_1,id_resep_2,id_resep_3);

        return rowView;
    }

    private void loadTotalHarga(final String id_resep, final String id_resep_2, final String id_resep_3){
        String tag_string_req = "req_load_harga";
        pDialog.setMessage("Memuat ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, BahanModel.GET_LIST_HARGA_BAHAN_FROM_WISHLIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Harga Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    jsonArray = jObj.getJSONArray("harga");
                    end = 0.0;
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject c = jsonArray.getJSONObject(i);
                        String banyaknya = c.getString("banyaknya");
                        String satuan = c.getString("satuan");
                        String nama_bahan = c.getString("nama_bahan");
                        String per = c.getString("per");
                        String harga_per_satuan = c.getString("harga_per_satuan");

                        temp_per = Double.parseDouble(per);
                        temp_banyaknya = Double.parseDouble(banyaknya);
                        temp_harga_per_satuan = Double.parseDouble(harga_per_satuan);

                        harga = (temp_banyaknya/temp_per) * temp_harga_per_satuan;
                        longTemp = Math.round(harga);
                        rawHarga = longTemp.toString();
                        end = end + Double.parseDouble(rawHarga);
                    }
                    hargaFinal = Math.round(end);
                    hargaTotal.setText(hargaFinal.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Harga Load Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                if(id_resep_1 != null && id_resep_2 == null && id_resep_3 == null){
                    params.put("id_resep_1", id_resep);
                } else if(id_resep_1 != null && id_resep_2 != null && id_resep_3 == null){
                    params.put("id_resep_1", id_resep);
                    params.put("id_resep_2", id_resep_2);
                } else if(id_resep_1 != null && id_resep_2 != null && id_resep_3 != null){
                    params.put("id_resep_1", id_resep);
                    params.put("id_resep_2", id_resep_2);
                    params.put("id_resep_3", id_resep_3);
                }
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


}
