package com.insiteprojectid.masakyuk.model;

/**
 * Created by Gregorius Andito on 1/23/2017.
 */

public class BahanModel {

    public static final String BASE_URL="http://192.168.0.120/masakyuk/";
    public static final String GET_LIST_BAHAN_RESEP=BASE_URL+"Welcome/get_list_bahan_resep";

    public static final String id_resep="id_resep";
    public static final String id_bahan_utama="id_bahan_utama";
    public static final String banyaknya="banyaknya";
    public static final String satuan="satuan";
    public static final String nama_bahan="nama_bahan";

    public static String getId_resep() {
        return id_resep;
    }

    public static String getId_bahan_utama() {
        return id_bahan_utama;
    }

    public static String getBanyaknya() {
        return banyaknya;
    }

    public static String getSatuan() {
        return satuan;
    }

    public static String getNama_bahan() {
        return nama_bahan;
    }
}
