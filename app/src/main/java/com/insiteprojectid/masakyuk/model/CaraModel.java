package com.insiteprojectid.masakyuk.model;

/**
 * Created by Gregorius Andito on 1/23/2017.
 */

public class CaraModel {

    public static final String BASE_URL="http://masakyuk.pe.hu/";
    public static final String GET_LIST_CARA_MEMASAK=BASE_URL+"Welcome/get_list_cara_memasak";

    public static final String id_resep="id_resep";
    public static final String id_cara_memasak="id_cara_memasak";
    public static final String cara="cara";

    public static String getId_resep() {
        return id_resep;
    }

    public static String getId_cara_memasak() {
        return id_cara_memasak;
    }

    public static String getCara() {
        return cara;
    }
}
