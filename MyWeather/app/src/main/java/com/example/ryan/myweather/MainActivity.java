package com.example.ryan.myweather;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    ProgressBar pb;
    ArrayList<WeatherInfo.Info> mInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pb = (ProgressBar)findViewById(R.id.progressBar);
        pb.setMax(100);
        if (isOnline()) {
            pb.setProgress(50);
            LoadWeather task = new LoadWeather();
            task.execute();
        }else{
            pb.setVisibility(View.GONE);
            Toast.makeText(this, "Not Online", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // check for internet connection
    public boolean isOnline(){
        ConnectivityManager cm =(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return (ni != null && ni.isConnected());
    }

    /*=============================================
    helper class to load weather in background
     ==============================================*/
    private class LoadWeather extends AsyncTask<String, Long, Long>{
        HttpURLConnection c;
        ArrayList<WeatherInfo.Info> info;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onProgressUpdate(Long... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            if (aLong != 1l){
                Log.d(Constants.TAG, "Got my result");
                setWeatherInfo(info);
                showList();
            }else{
                Toast.makeText(getApplicationContext(), "Background Task failed", Toast.LENGTH_LONG).show();
            }
            pb.setVisibility(View.GONE);
        }

        @Override
        protected Long doInBackground(String... params) {
            String api_str = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=35&lon=139&cnt=10&mode=json";
            try{
                URL data_url = new URL(api_str);
                c = (HttpURLConnection)data_url.openConnection();
                c.connect();
                int status = c.getResponseCode();

                // check for status
                if (status == 200) {  // OK
                    InputStream is = c.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String response_str;
                    StringBuilder sb = new StringBuilder();

                    while ((response_str = br.readLine()) != null){
                        sb = sb.append(response_str);
                    }
                    String weather_data = sb.toString();
                    Log.d(Constants.TAG, weather_data);
                    info = WeatherInfo.getInfoList(weather_data);
                    return 0l;
                }else
                    return 1l;
            } catch (MalformedURLException e) {
                Log.e(Constants.TAG, "Malformed URL");
                e.printStackTrace();
                return 1l;
            } catch (IOException e) {
                e.printStackTrace();
                return 1l;
            } catch (JSONException e) {
                e.printStackTrace();
                return 1l;
            } finally {  // close the connection
                if (c != null)
                    c.disconnect();
            }
        }
    }

    public ArrayList<WeatherInfo.Info> getWeather(){
        return mInfo;
    }

    public void showList(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.container, new WeatherFragment());
        ft.commit();

    }

    public void setWeatherInfo (ArrayList<WeatherInfo.Info> info){
        this.mInfo = info;
    }
}
