package com.insiteprojectid.masakyuk.model;

/**
 * Created by Gregorius Andito on 4/1/2016.
 */
public class ResepModel {

    public static final String BASE_URL="http://192.168.0.120/masakyuk/";
        public static final String BASE_IMG=BASE_URL+"gambar/";
    public static final String GET_RESEP=BASE_URL+"Welcome/get_resep";
    public static final String GET_REKOMENDASI=BASE_URL+"Welcome/get_rekomendasi_resep";
    public static final String GET_MAKANAN_PEMBUKA=BASE_URL+"Welcome/get_list_makanan_pembuka";
    public static final String GET_MAKANAN_UTAMA=BASE_URL+"Welcome/get_list_makanan_utama";
    public static final String GET_MAKANAN_PENUTUP=BASE_URL+"Welcome/get_list_makanan_penutup";

    public static final String id_resep = "id_resep";
    public static final String id_cat = "id_cat";
    public static final String judul = "judul";
    public static final String gambar = "gambar";
    public static final String link_youtube = "link_youtube";
    public static final String rekomendasi = "rekomendasi";


    public static String getId_resep() {
        return id_resep;
    }

    public static String getId_cat() {
        return id_cat;
    }

    public static String getJudul() {
        return judul;
    }

    public static String getGambar() {
        return gambar;
    }

    public static String getLink_youtube() {
        return link_youtube;
    }

    public static String getRekomendasi() {
        return rekomendasi;
    }
}
