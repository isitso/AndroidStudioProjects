package com.example.ryan.myweather;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WeatherFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherFragment extends Fragment implements AdapterView.OnItemClickListener {

    // weather
    WeatherInfo mWeatherInfo;   // ArrayList<Info> info_list holds list of weathers
    ArrayList<WeatherInfo.Info> info;

    String[] titles;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_weather, container, false);
        MainActivity activity = (MainActivity) this.getActivity();
        info = activity.getWeather();
        if (info != null) {
            titles = new String[info.size()];
            for (int i = 0; i < info.size(); i++)
                titles[i] = info.get(i).weather_list.get(0).main;
        }

        ListView lv = (ListView) v.findViewById(R.id.listView);
        lv.setAdapter(new ArrayAdapter<String>(this.getActivity(), R.layout.abc_list_menu_item_layout, titles));
        return v;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
}