package com.project.cs496.tab_app;



import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by q on 2016-07-01.
 */
public class JSONParser {


    public Weather getData(String str){
        Weather weather = new Weather();
        Location loc = new Location();
        Log.v("raw_data",str);

        try{
            JSONObject jObj = new JSONObject(str);

            JSONObject coordObj = getObject("coord", jObj);
            loc.setLatitude(getFloat("lat", coordObj));
            loc.setLongitude(getFloat("lon", coordObj));

            JSONObject sysObj = getObject("sys", jObj);
            loc.setCountry(getString("country", sysObj));
            loc.setSunrise(getInt("sunrise", sysObj));
            loc.setSunset(getInt("sunset", sysObj));
            loc.setCity(getString("name", jObj));
            weather.location = loc;

            JSONArray jArr = jObj.getJSONArray("weather");

// We use only the first value
            JSONObject JSONWeather = jArr.getJSONObject(0);

            Log.v("raw_data2",JSONWeather.toString());
            Log.v("raw_data3", Integer.toString(getInt("id",JSONWeather)));
            weather.currentCondition.setWeatherId(getInt("id", JSONWeather));
            weather.currentCondition.setDescr(getString("description", JSONWeather));
            weather.currentCondition.setCondition(getString("main", JSONWeather));
            weather.currentCondition.setIcon(getString("icon", JSONWeather));

            JSONObject mainObj = getObject("main", jObj);
            weather.currentCondition.setHumidity(getInt("humidity", mainObj));
            weather.currentCondition.setPressure(getInt("pressure", mainObj));
            weather.temperature.setMaxTemp(getFloat("temp_max", mainObj));
            weather.temperature.setMinTemp(getFloat("temp_min", mainObj));
            weather.temperature.setTemp(getFloat("temp", mainObj));

// Wind
            JSONObject wObj = getObject("wind", jObj);
            Log.v("windspeed",Float.toString(getFloat("speed",wObj)));
            weather.wind.setSpeed(getFloat("speed", wObj));
            weather.wind.setDeg(getFloat("deg", wObj));

// Clouds
            JSONObject cObj = getObject("clouds", jObj);
            weather.clouds.setPerc(getInt("all", cObj));

        }catch(Exception e){
            e.printStackTrace();
        }

        return weather;

    }
    private static JSONObject getObject(String tagName, JSONObject jObj) throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    private static float getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static int getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }
}
