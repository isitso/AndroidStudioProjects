package com.example.ryan.myweather;

/**
 * Created by Ryan on 5/27/2015.
 */
public class Constants {
    public static final String OPENWEATHER_API_KEY = "ec2ca3adc2ee15fe0ab8adc5ab626962";
    public static final String TAG = "My code";
    public static final String DB_NAME = "my_weather_db";
    public static final String TABLE_NAME = "weathers";
    public static final String CREATE_SQL = "create table weathers (id integer primary key, main text, description text, "
            + "temp_high real, temp_low real);";
    public static final String DROP_SQL = "drop table if exists weathers";
    public static final String GET_SQL = "select * from weathers where id=";

    // http://stackoverflow.com/questions/3359414/android-column-id-does-not-exist
    // SimpleCursorAdapter requires that the Cursor's result set must include a column named exactly "_id".
    public static final String GET_ALL_SQL = "select t.*, t.id as _id from weathers t;";
    public static final String DELETE_ALL_SQL = "delete * from weathers";

    public static final String MAIN_KEY = "main";
    public static final String DESCRIPTION_KEY = "description";
    public static final String TEMP_HIGH_KEY = "temp_high";
    public static final String TEMP_LOW_KEY = "temp_low";

    public static final int LAST_LOCATION_TIME = 2 * 60 * 1000; // 2 minutes
}
