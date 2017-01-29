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
public class CaraResepFragment extends Fragment {

    private String id_resep;

    private ListView listCara;
    ArrayList<HashMap<String, String>> DaftarCara = new ArrayList<>();
    ProgressDialog pDialog;
    private static final String TAG = ResepActivity.class.getSimpleName();
    CaraModel caraModel;
    ResepModel resepModel;
    CaraAdapter caraAdapter;
    JSONArray jsonArray;

    public CaraResepFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rowView = inflater.inflate(R.layout.fragment_cara_resep, container, false);

        id_resep = getActivity().getIntent().getStringExtra(resepModel.getId_resep());
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        listCara = (ListView)rowView.findViewById(R.id.listCara);

        loadCara(id_resep);

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


    private void loadCara(final String id_resep){
        String tag_string_req = "req_load_cara";
        pDialog.setMessage("Memuat ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, CaraModel.GET_LIST_CARA_MEMASAK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Cara Response: " + response.toString());
                hideDialog();

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

    private void SetListCara(ArrayList<HashMap<String, String>> daftarCara) {
        if(daftarCara.size() == 0) {
            caraAdapter = new CaraAdapter(getActivity(), new ArrayList<HashMap<String, String>>());
            listCara.setAdapter(caraAdapter);
            caraAdapter.notifyDataSetInvalidated();
        } else {
            caraAdapter = new CaraAdapter(getActivity(), daftarCara);
            listCara.setAdapter(caraAdapter);
            caraAdapter.notifyDataSetChanged();
        }
    }

}
