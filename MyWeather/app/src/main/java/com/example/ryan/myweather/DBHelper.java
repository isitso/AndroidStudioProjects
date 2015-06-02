package com.example.ryan.myweather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Ryan on 5/31/2015.
 */
public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public  DBHelper(Context context){
        super(context, Constants.DB_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Constants.DROP_SQL);
    }

    public boolean insertWeather(String main, String desc, double high, double low){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Constants.MAIN_KEY, main);
        cv.put(Constants.DESCRIPTION_KEY, desc);
        cv.put(Constants.TEMP_HIGH_KEY, high);
        cv.put(Constants.TEMP_LOW_KEY, low);
        db.insert(Constants.TABLE_NAME, null, cv);
        return true;
    }

    public boolean insertWeathers(ArrayList<SimpleWeather> list){
        boolean bool = true;

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(Constants.DROP_SQL);
        db.execSQL(Constants.CREATE_SQL);
        ContentValues cv = new ContentValues();

        for (SimpleWeather sw : list){
            cv.put(Constants.MAIN_KEY, sw.main);
            cv.put(Constants.DESCRIPTION_KEY, sw.desc);
            cv.put(Constants.TEMP_HIGH_KEY, sw.temp_high);
            cv.put(Constants.TEMP_LOW_KEY, sw.temp_low);
            db.insert(Constants.TABLE_NAME, null, cv);
        }
        return bool;
    }
    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(Constants.GET_SQL + id, null);
        return c;
    }

    public Cursor getAll(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(Constants.GET_ALL_SQL, null);
        return c;

    }
    public ArrayList<SimpleWeather> getWeatherList(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(Constants.GET_ALL_SQL, null);
        c.moveToFirst();
        ArrayList<SimpleWeather> list = new ArrayList<SimpleWeather>();
        SimpleWeather sw = new SimpleWeather();
        while (!c.isAfterLast()){
            sw.main = c.getString(c.getColumnIndex(Constants.MAIN_KEY));
            sw.desc = c.getString(c.getColumnIndex(Constants.DESCRIPTION_KEY));
            sw.temp_high = c.getDouble(c.getColumnIndex(Constants.TEMP_HIGH_KEY));
            sw.temp_low = c.getDouble(c.getColumnIndex(Constants.TEMP_LOW_KEY));
            list.add(sw);
        }
        return list;
    }

}
