package com.insiteprojectid.masakyuk.controller;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.insiteprojectid.masakyuk.R;
import com.insiteprojectid.masakyuk.adapter.TotalBahanAdapter;
import com.insiteprojectid.masakyuk.adapter.TotalHargaAdapter;
import com.insiteprojectid.masakyuk.model.BahanModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TotalHargaActivity extends AppCompatActivity {

    private ListView lv;
    private static final String TAG = TotalHargaActivity.class.getSimpleName();
    ArrayList<HashMap<String, String>> DaftarBahan = new ArrayList<>();
    ProgressDialog pDialog;
    BahanModel bahanModel;
    TotalHargaAdapter totalHargaAdapter;
    JSONArray jsonArray;
    String id_resep_1, id_resep_2, id_resep_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_harga);

        lv = (ListView)findViewById(R.id.list_total_harga);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        id_resep_1 = getIntent().getStringExtra("id_resep");
        id_resep_2 = getIntent().getStringExtra("id_resep_2");
        id_resep_3 = getIntent().getStringExtra("id_resep_3");

        loadHarga(id_resep_1,id_resep_2,id_resep_3);

    }

    private void loadHarga(final String id_resep, final String id_resep_2, final String id_resep_3){
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
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject c = jsonArray.getJSONObject(i);
                        String banyaknya = c.getString("banyaknya");
                        String satuan = c.getString("satuan");
                        String nama_bahan = c.getString("nama_bahan");
                        String per = c.getString("per");
                        String harga_per_satuan = c.getString("harga_per_satuan");
                        HashMap<String,String> map_bahan = new HashMap<>();
                        map_bahan.put(bahanModel.banyaknya,banyaknya);
                        map_bahan.put(bahanModel.satuan,satuan);
                        map_bahan.put(bahanModel.nama_bahan,nama_bahan);
                        map_bahan.put(bahanModel.harga_per_satuan,harga_per_satuan);
                        map_bahan.put(bahanModel.per,per);
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
                Log.e(TAG, "Harga Load Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
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

    private void SetListBahan(ArrayList<HashMap<String, String>> daftarBahan) {
        if(daftarBahan.size() == 0) {
            totalHargaAdapter = new TotalHargaAdapter(this, new ArrayList<HashMap<String, String>>());
//            emptyTV.setText("Tidak ada komentar");
            lv.setAdapter(totalHargaAdapter);
            totalHargaAdapter.notifyDataSetInvalidated();
        } else {
            totalHargaAdapter = new TotalHargaAdapter(this, daftarBahan);
            lv.setAdapter(totalHargaAdapter);
            totalHargaAdapter.notifyDataSetChanged();
        }
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
