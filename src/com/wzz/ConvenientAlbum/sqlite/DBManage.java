package com.wzz.ConvenientAlbum.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.wzz.ConvenientAlbum.bean.LocationInfoBean;

/**
 * Created by WANGZHENGZE on 2014/10/10.
 */
public class DBManage {
    // database版本
    private final static int DB_VERSION = 1;
    // database名
    private final static String DB_NAME = "convenient.db";

    private Context context;

    private static DBManage dbManage;

    private SQLiteDatabase db = null;

    private DataBaseHelper dbHelper = null;

    private DBManage(Context context) {
        this.context = context;
    }

    private static class DataBaseHelper extends SQLiteOpenHelper {

        Context context;
        DataBaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table location(_id integer primary key, longitude double, latitude double, " +
                    "province text, city text, others text, address text, path text, name text)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    public static synchronized DBManage getInstance(Context context) {
        if (dbManage == null) {
            dbManage = new DBManage(context);
        }
        return dbManage;
    }

    public void open() throws SQLException {
        if (isOpen()) {
            return;
        }
        dbHelper = new DataBaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void quit() {
        try {
            if (isOpen() && dbHelper != null)
                dbHelper.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isOpen() {
        return db != null && db.isOpen();
    }

    public void saveLocation(LocationInfoBean bean) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("longitude", bean.getLongitude());
        contentValues.put("latitude", bean.getLatitude());
        contentValues.put("province", bean.getProvince());
        contentValues.put("city", bean.getCity());
        contentValues.put("others", bean.getOthers());
        contentValues.put("address", bean.getProvince() + bean.getCity() + bean.getOthers());
        contentValues.put("path", bean.getPath());
        contentValues.put("name", bean.getName());
        db.insert("location", null, contentValues);
    }

}
