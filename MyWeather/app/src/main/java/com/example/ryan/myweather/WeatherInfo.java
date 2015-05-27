package com.example.ryan.myweather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ryan on 5/27/2015.
 */
public class WeatherInfo {

    // JSON Fields
    String base, name;
    double dt;
    long id, cod;
    Coord coord;
    Sys sys;
    ArrayList<Weather> weather;
    Main main;
    Wind wind;
    Cloud clouds;
    Rain rain;

    public WeatherInfo(JSONObject json_weather) throws JSONException{

        // coords
        JSONObject tmp_coord = json_weather.optJSONObject("coord");
        this.coord = new Coord();
        this.coord.lat = tmp_coord.optLong("lat");
        this.coord.lon = tmp_coord.optLong("lon");

        // sys
        JSONObject tmp_sys = json_weather.optJSONObject("sys");
        this.sys = new Sys();
        this.sys.message = tmp_sys.optString("message");
        this.sys.country = tmp_sys.optString("country");
        this.sys.sunrise = tmp_sys.optLong("sunrise");
        this.sys.sunset = tmp_sys.optLong("sunset");

        // weather - this should be an arraylist
        JSONArray array_weather = json_weather.optJSONArray("weather");
        this.weather = new ArrayList<Weather>();
        //JSONObject tmp_weather = json_weather.optJSONObject("weather");
        for (int i = 0; i < array_weather.length(); i++){
            JSONObject tmp_json_weather = array_weather.getJSONObject(i);
            Weather tmp_weather = new Weather();
            tmp_weather.id = tmp_json_weather.optLong("id");
            tmp_weather.main = tmp_json_weather.optString("main");
            tmp_weather.description = tmp_json_weather.optString("description");
            tmp_weather.icon = tmp_json_weather.optString("icon");
            this.weather.add(tmp_weather);
        }

        // base
        this.base = json_weather.optString("base");

        // main
        JSONObject tmp_main = json_weather.optJSONObject("main");
        this.main = new Main();
        this.main.temp = tmp_main.optDouble("temp");
        this.main.temp_min = tmp_main.optDouble("temp_min");
        this.main.temp_max = tmp_main.optDouble("temp_max");
        this.main.pressure = tmp_main.optDouble("pressure");
        this.main.sea_level = tmp_main.optDouble("sea_level");
        this.main.grnd_level = tmp_main.optDouble("grnd_level");
        this.main.humidity = tmp_main.optDouble("humidity");

        // wind
        JSONObject tmp_wind = json_weather.optJSONObject("wind");
        this.wind = new Wind();
        this.wind.speed = tmp_wind.optDouble("speed");
        this.wind.deg = tmp_wind.optDouble("deg");

        // clouds
        JSONObject tmp_clouds = json_weather.optJSONObject("clouds");
        this.clouds = new Cloud();
        this.clouds.all = tmp_clouds.optLong("all");

        // rain
        JSONObject tmp_rain = json_weather.optJSONObject("rain");
        this.rain = new Rain();
        this.rain.three_h = tmp_rain.optDouble("3h");

        this.dt = json_weather.optLong("dt");
        this.id = json_weather.optLong("id");
        this.name = json_weather.optString("name");
        this.cod = json_weather.optLong("cod");

    }



    /*==================================================
    Helper classes
     *===================================================*/
    public class Coord{
        public long lon, lat;
    }

    public class Sys{
        public String message, country;
        public long sunrise, sunset;
    }

    public class Weather{
        public long id;
        public String main, description, icon;
    }

    public class Main{
        public double temp, temp_min, temp_max, pressure, sea_level, grnd_level, humidity;
    }

    public class Wind{
        public double speed, deg;
    }

    public class Cloud{
        public long all;
    }

    public class Rain{
        public double three_h;
    }


}
