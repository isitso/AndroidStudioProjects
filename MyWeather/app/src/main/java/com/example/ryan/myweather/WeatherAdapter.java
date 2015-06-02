package com.example.ryan.myweather;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
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
    public WeatherAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
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
        ViewHolder holder = new ViewHolder();
        holder.text = (TextView)v.findViewById(R.id.text);
        holder.image = (ImageView)v.findViewById(R.id.image);
        v.setTag(holder);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder)view.getTag();

        int pos = cursor.getPosition();
        Log.d(Constants.TAG, "current cursor pos " + pos);
        double low = cursor.getDouble(cursor.getColumnIndex(Constants.TEMP_LOW_KEY));
        double high = cursor.getDouble(cursor.getColumnIndex(Constants.TEMP_HIGH_KEY));
        String main = cursor.getString(cursor.getColumnIndex(Constants.MAIN_KEY));
        String info = "High: " + String.valueOf((int)high) + " Low: " + String.valueOf((int)low);
        holder.text.setText(info);
        Bitmap bm = null;
        if (main.contains("ain")) {
            bm = BitmapFactory.decodeResource(view.getResources(), R.drawable.rain);
        }else if (main.contains("loud")) {
            bm = BitmapFactory.decodeResource(view.getResources(), R.drawable.cloudy);
        }else {
            bm = BitmapFactory.decodeResource(view.getResources(), R.drawable.sunny);
        }
        Log.d(Constants.TAG, "binding: " + main);
        holder.image.setImageBitmap(bm);
    }

    static class ViewHolder {
        TextView text;
        ImageView image;
    }
}
