package com.insiteprojectid.masakyuk.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Gregorius Andito on 1/25/2017.
 */

public class WishListModel extends SQLiteOpenHelper {

    private static final String TAG = WishListModel.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "WishListDB";

    // Login table name
    private static final String TABLE_WISH = "WishList";

    // Login Table Columns names
    private static final String KEY_ID = "_id";
    private static final String id_resep = "id_resep";
    private static final String id_cat = "id_cat";
    private static final String judul = "judul";
    private static final String gambar = "gambar";
    private static final String link_youtube = "link_youtube";
    private static final String rekomendasi = "rekomendasi";


    public WishListModel(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_WISH + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + id_resep + " TEXT," +
                id_cat + " TEXT," + judul + " TEXT," +
                gambar + " TEXT," + link_youtube + " TEXT," +
                rekomendasi + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WISH);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addResep(String _id_resep, String _id_cat, String _judul, String _gambar, String _link_youtube, String _rekomendasi) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(id_resep, _id_resep);
        values.put(id_cat, _id_cat);
        values.put(judul, _judul);
        values.put(gambar, _gambar);
        values.put(link_youtube, _link_youtube);
        values.put(rekomendasi, _rekomendasi);

        // Inserting Row
        long id = db.insert(TABLE_WISH, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New recipe inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getResepDetail() {
        HashMap<String, String> resep = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_WISH;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                if (cursor.getCount() > 0) {
                    resep.put("id_resep", cursor.getString(1));
                    resep.put("id_cat", cursor.getString(2));
                    resep.put("judul", cursor.getString(3));
                    resep.put("gambar", cursor.getString(4));
                    resep.put("link_youtube", cursor.getString(5));
                    resep.put("rekomendasi", cursor.getString(6));
                }
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching resep from Sqlite");

        return resep;
    }

    public Cursor getListResep(){
        // TodoDatabaseHandler is a SQLiteOpenHelper class connecting to SQLite
//        WishListModel handler = new WishListModel(this);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor todoCursor = db.rawQuery("SELECT  * FROM "+TABLE_WISH, null);
        return todoCursor;
    }

    public void deleteResep(String _id_resep) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_WISH,id_resep + " = ?",new String[] { _id_resep });
        db.close(); // Closing database connection

        Log.d(TAG, "1 recipe deleted from sqlite");
    }

    public int getResepCount() {
        String countQuery = "SELECT  * FROM " + TABLE_WISH;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    public boolean isExists(String id_resep) {
        String countQuery = "SELECT  * FROM " + TABLE_WISH + " WHERE id_resep = "+id_resep;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,
                null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_WISH, null, null);
        db.close();

        Log.d(TAG, "Deleted all recipe info from sqlite");
    }

}
