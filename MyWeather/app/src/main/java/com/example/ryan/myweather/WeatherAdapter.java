package com.example.ryan.myweather;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Ryan on 6/1/2015.
 */
public class WeatherAdapter extends CursorAdapter {

    Context mContext;
    Cursor mCursor;
    LayoutInflater mLayoutInflater;
    public WeatherAdapter(Context context, Cursor c) {
        super(context, c, 0);
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
        TextView tv = (TextView)view.findViewById(R.id.text);
        ImageView iv = (ImageView)view.findViewById(R.id.image);

        double low = mCursor.getDouble(mCursor.getColumnIndex(Constants.TEMP_LOW_KEY));
        double high = mCursor.getDouble(mCursor.getColumnIndex(Constants.TEMP_HIGH_KEY));
        String main = mCursor.getString(mCursor.getColumnIndex(Constants.MAIN_KEY));
        String info = "High: " + String.valueOf((int)high) + " Low: " + String.valueOf((int)low);
        tv.setText(info);
        Bitmap bm = null;
        if (main.contains("rain"))
            bm = BitmapFactory.decodeResource(view.getResources(), R.drawable.rain);
        else if (main.contains("loud"))
            bm = BitmapFactory.decodeResource(view.getResources(), R.drawable.cloudy);
        else
            bm = BitmapFactory.decodeResource(view.getResources(), R.drawable.sunny);
        iv.setImageBitmap(bm);
    }
}
