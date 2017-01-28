package com.insiteprojectid.masakyuk.controller;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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

public class HasilCariActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<HashMap<String, String>> DaftarResep = new ArrayList<>();
    ProgressDialog pDialog;
    private static final String TAG = HasilCariActivity.class.getSimpleName();
    ResepModel resepModel;
    ListAdapter listAdapter;
    JSONArray jsonArray;
    private ImageView shareIcon;
    private TextView emptyTextCari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_cari);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        lv = (ListView)findViewById(R.id.list_resep);
        DaftarResep = new ArrayList<>();
        shareIcon = (ImageView)findViewById(R.id.share);
        emptyTextCari = (TextView)findViewById(R.id.emptyTextCari);

        lv.setEmptyView(emptyTextCari);

        handleIntent(getIntent());

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,String> map = DaftarResep.get(position);
                Intent i = new Intent(getApplicationContext(),ResepActivity.class);
                i.putExtra(resepModel.id_resep,map.get(resepModel.id_resep));
                i.putExtra(resepModel.id_cat,map.get(resepModel.id_cat));
                i.putExtra(resepModel.link_youtube,map.get(resepModel.link_youtube));
                i.putExtra(resepModel.judul,map.get(resepModel.judul));
                i.putExtra(resepModel.gambar,map.get(resepModel.gambar));
                i.putExtra(resepModel.rekomendasi,map.get(resepModel.rekomendasi));
                startActivity(i);
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_utama, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            loadResep(query);
        }
    }

    private void loadResep(final String query) {
        String tag_string_req = "req_load_resep";
        pDialog.setMessage("Memuat ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, ResepModel.GET_HASIL_CARI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Resep Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    jsonArray = jObj.getJSONArray("resep");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject c = jsonArray.getJSONObject(i);
                        String id_resep = c.getString("id_resep");
                        String id_cat = c.getString("id_cat");
                        String judul = c.getString("judul");
                        String gambar = ResepModel.BASE_IMG + c.getString("gambar");
                        String link_youtube = c.getString("link_youtube");
                        String rekomendasi = c.getString("rekomendasi");
                        HashMap<String, String> map_resep = new HashMap<>();
                        map_resep.put(resepModel.id_resep, id_resep);
                        map_resep.put(resepModel.id_cat, id_cat);
                        map_resep.put(resepModel.judul, judul);
                        map_resep.put(resepModel.gambar, gambar);
                        map_resep.put(resepModel.link_youtube, link_youtube);
                        map_resep.put(resepModel.rekomendasi, rekomendasi);
                        DaftarResep.add(map_resep);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                SetListResep(DaftarResep, query);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Resep Load Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("query", query);
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

    private void SetListResep(ArrayList<HashMap<String, String>> daftarResep, String query) {
        if (daftarResep.size() == 0) {
            listAdapter = new ListAdapter(this, new ArrayList<HashMap<String, String>>());
            emptyTextCari.setText("Tidak ada hasil yang ditemukan untuk bahan "+query);
            lv.setAdapter(listAdapter);
            listAdapter.notifyDataSetInvalidated();
        } else {
            listAdapter = new ListAdapter(this, daftarResep);
            lv.setAdapter(listAdapter);
//            UIUtils.setListViewHeightBasedOnItems(komentar);
            listAdapter.notifyDataSetChanged();
        }

    }

}
