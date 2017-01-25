package com.insiteprojectid.masakyuk.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.insiteprojectid.masakyuk.adapter.BahanAdapter;
import com.insiteprojectid.masakyuk.adapter.CaraAdapter;
import com.insiteprojectid.masakyuk.adapter.ListAdapter;
import com.insiteprojectid.masakyuk.model.BahanModel;
import com.insiteprojectid.masakyuk.model.CaraModel;
import com.insiteprojectid.masakyuk.model.ResepModel;
import com.insiteprojectid.masakyuk.utils.Config;
import com.insiteprojectid.masakyuk.R;
import com.insiteprojectid.masakyuk.utils.UIUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResepActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private YouTubePlayerView youTubeView;

    private static final int RECOVERY_REQUEST = 1;
    private ImageView favourite, share;

    private ListView listBahan, listCara;
    private TextView judul_resep;
    private String id_resep, link_youtube, judul;

    ArrayList<HashMap<String, String>> DaftarBahan = new ArrayList<>();
    ArrayList<HashMap<String, String>> DaftarCara = new ArrayList<>();
    ProgressDialog pDialog;
    private static final String TAG = ResepActivity.class.getSimpleName();
    BahanModel bahanModel;
    CaraModel caraModel;
    ResepModel resepModel;
    BahanAdapter bahanAdapter;
    CaraAdapter caraAdapter;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resep);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        favourite = (ImageView)findViewById(R.id.fav_resep);
        share = (ImageView)findViewById(R.id.share_resep);

        judul_resep = (TextView)findViewById(R.id.nama_resep);

        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(Config.YOUTUBE_API_KEY, this);

        id_resep = getIntent().getStringExtra(resepModel.getId_resep());
        link_youtube = getIntent().getStringExtra(resepModel.getLink_youtube());
        judul = getIntent().getStringExtra(resepModel.getJudul());

        listBahan = (ListView)findViewById(R.id.listBahan);
        listCara = (ListView)findViewById(R.id.listCara);

        judul_resep.setText(judul);

        loadBahan(id_resep);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Intent.ACTION_SEND);
                a.setType("text/plain");
                a.putExtra(Intent.EXTRA_SUBJECT, "Masak Yuk");
                a.putExtra(Intent.EXTRA_TEXT, "Masak "+judul+" yuk! Download aplikasi Masak Yuk di Play Store sekarang juga.");
                startActivity(Intent.createChooser(a,"Share it"));
            }
        });

    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            youTubePlayer.cueVideo(link_youtube);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Config.YOUTUBE_API_KEY, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
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
                loadCara(id_resep);
                SetListBahan(DaftarBahan);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Bahan Load Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
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


    private void loadCara(final String id_resep){
        String tag_string_req = "req_load_cara";
//        pDialog.setMessage("Memuat ...");
//        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, CaraModel.GET_LIST_CARA_MEMASAK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Cara Response: " + response.toString());
//                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    jsonArray = jObj.getJSONArray("cara");
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject c = jsonArray.getJSONObject(i);
                        String id_cara_memasak = c.getString("id_cara_memasak");
                        String id_resep = c.getString("id_resep");
                        String cara = c.getString("cara");
                        HashMap<String,String> map_cara = new HashMap<>();
                        map_cara.put(caraModel.id_resep,id_resep);
                        map_cara.put(caraModel.id_cara_memasak,id_cara_memasak);
                        map_cara.put(caraModel.cara,cara);
                        DaftarCara.add(map_cara);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                SetListCara(DaftarCara);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Cara Load Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
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
            bahanAdapter = new BahanAdapter(this, new ArrayList<HashMap<String, String>>());
//            emptyTV.setText("Tidak ada komentar");
            listBahan.setAdapter(bahanAdapter);
            bahanAdapter.notifyDataSetInvalidated();
        } else {
            bahanAdapter = new BahanAdapter(this, daftarBahan);
            listBahan.setAdapter(bahanAdapter);
            UIUtils.setListViewHeightBasedOnItems(listBahan);
            bahanAdapter.notifyDataSetChanged();
        }
    }

    private void SetListCara(ArrayList<HashMap<String, String>> daftarCara) {
        if(daftarCara.size() == 0) {
            caraAdapter = new CaraAdapter(this, new ArrayList<HashMap<String, String>>());
//            emptyTV.setText("Tidak ada komentar");
            listCara.setAdapter(caraAdapter);
            caraAdapter.notifyDataSetInvalidated();
        } else {
            caraAdapter = new CaraAdapter(this, daftarCara);
            listCara.setAdapter(caraAdapter);
            UIUtils.setListViewHeightBasedOnItems(listCara);
            caraAdapter.notifyDataSetChanged();
        }
    }

}
