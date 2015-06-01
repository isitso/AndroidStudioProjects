package com.example.ryan.myweather;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

/**
 * Created by Ryan on 5/31/2015.
 */
public class WeatherAdapter extends CursorAdapter{

    Context mContext;
    Cursor mCursor;
    LayoutInflater mLayoutInflater;
    public WeatherAdapter(Context context, Cursor c) {
        super(context, c);
        mContext = context;
        mCursor = c;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public WeatherAdapter(Context context, Cursor c, String[] desc){
        super(context, c);
        mContext = context;
        mCursor = c;
        mLayoutInflater = LayoutInflater.from(context);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v = mLayoutInflater.inflate(R.layout.weather_row, parent, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }
}
