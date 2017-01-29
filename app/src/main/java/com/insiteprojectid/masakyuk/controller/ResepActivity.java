package com.insiteprojectid.masakyuk.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.insiteprojectid.masakyuk.adapter.BahanAdapter;
import com.insiteprojectid.masakyuk.adapter.CaraAdapter;
import com.insiteprojectid.masakyuk.adapter.ListAdapter;
import com.insiteprojectid.masakyuk.model.BahanModel;
import com.insiteprojectid.masakyuk.model.CaraModel;
import com.insiteprojectid.masakyuk.model.ResepModel;
import com.insiteprojectid.masakyuk.model.WishListModel;
import com.insiteprojectid.masakyuk.utils.Config;
import com.insiteprojectid.masakyuk.R;
import com.insiteprojectid.masakyuk.utils.UIUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.insiteprojectid.masakyuk.utils.Config.YOUTUBE_API_KEY;

//public class ResepActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
public class ResepActivity extends AppCompatActivity {
    private YouTubePlayerView youTubeView;

    private static final int RECOVERY_REQUEST = 1;
    private ImageView favourite, share;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    private TextView judul_resep;
    private String id_resep, link_youtube, judul, rekomendasi, gambar, id_cat;

    ResepModel resepModel;
    private WishListModel db;
    AppCompatActivity app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resep);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        favourite = (ImageView)findViewById(R.id.fav_resep);
        share = (ImageView)findViewById(R.id.share_resep);

        judul_resep = (TextView)findViewById(R.id.nama_resep);


//        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
//        youTubeView.initialize(Config.YOUTUBE_API_KEY, this);

        db = new WishListModel(getApplicationContext());

        id_resep = getIntent().getStringExtra(resepModel.getId_resep());
        id_cat = getIntent().getStringExtra(resepModel.getId_cat());
        link_youtube = getIntent().getStringExtra(resepModel.getLink_youtube());
        judul = getIntent().getStringExtra(resepModel.getJudul());
        gambar = getIntent().getStringExtra(resepModel.getGambar());
        rekomendasi = getIntent().getStringExtra(resepModel.getRekomendasi());

        YouTubePlayerFragment youtubeFragment = (YouTubePlayerFragment)
                getFragmentManager().findFragmentById(R.id.youtube_view);
        youtubeFragment.initialize(YOUTUBE_API_KEY,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {
                        if(!b){
                            youTubePlayer.cueVideo(link_youtube);
                        }
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult errorReason) {
                                if (errorReason.isUserRecoverableError()) {
                                    errorReason.getErrorDialog(getParent(), RECOVERY_REQUEST).show();
                                } else {
                                    String error = String.format(getString(R.string.player_error), errorReason.toString());
                                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
                                }
                    }
                });

        judul_resep.setText(judul);

        if(db.isExists(id_resep)){
            favourite.setImageResource(R.drawable.heart_red);
        } else {
            favourite.setImageResource(R.drawable.heart_white);
        }

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

        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new WishListModel(getApplicationContext());
                if(db.isExists(id_resep)){
                    db.deleteResep(id_resep);
                    favourite.setImageResource(R.drawable.heart_white);
                } else {
                    if(db.getResepCount() < 3) {
                        db.addResep(id_resep, id_cat, judul, gambar, link_youtube, rekomendasi);
                        favourite.setImageResource(R.drawable.heart_red);
                    } else {
                        Toast.makeText(getApplicationContext(), "Wishlist anda penuh", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private static final int FRAGMENT_COUNT = 2;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position){
                case 0:
                    return new BahanResepFragment();
                case 1:
                    return new CaraResepFragment();
            }
            return null;
//            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return FRAGMENT_COUNT;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Bahan";
                case 1:
                    return "Cara Memasak";
            }
            return null;
        }
    }


}
