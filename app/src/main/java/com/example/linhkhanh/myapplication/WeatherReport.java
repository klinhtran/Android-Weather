package com.example.linhkhanh.myapplication;

/**
 * Created by linhkhanh on 1/5/19.
 */

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Scanner;

import org.json.*;

public class WeatherReport {

    public String weatherDes;
    public double temperature;
    public static DecimalFormat df = new DecimalFormat("#.#");
    public String city;

    public WeatherReport(JSONObject o, String s) throws JSONException{
        weatherDes = getWeatherState(o);
        temperature = (double)getTemp(o) - 273.15;
        city = s;
    }

    public String getDescription() {
        return "The weather today in " + city + " is " + weatherDes +" with a temperature of "+ df.format(temperature)+" degrees";
    }

    public static String getWeatherState(JSONObject obj) throws JSONException {
        JSONArray weather = obj.getJSONArray("weather");
        if(weather.length() != 0){
            JSONObject o = weather.getJSONObject(0);
            String description = o.getString("description");
            return description;
        }

        return null;
    }

    public Number getTemp(JSONObject obj) throws JSONException {
        Number temp = obj.getJSONObject("main").getDouble("temp");
        return temp;
    }
}