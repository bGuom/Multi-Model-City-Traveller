package com.app.smartcitytraveler.DirectionDataAPI;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class JsonDataParser {

    public HashMap<String,String> getDirectionData(String jsonData){
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("routes").getJSONObject(0).getJSONArray("legs");
            Log.d("qwer",jsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getData(jsonArray);

    }

    private HashMap<String,String> getData(JSONArray jsonArray){

        HashMap<String,String> TempHashMap = new HashMap<String, String>();

        String distance = "";
        String duration ="";

        try {
            distance = jsonArray.getJSONObject(0).getJSONObject("distance").getString("value");
            TempHashMap.put("distance",distance);
            duration = jsonArray.getJSONObject(0).getJSONObject("duration").getString("value");
            TempHashMap.put("duration",duration);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return TempHashMap;
    }

    public ArrayList<String> getPathsArray(String jsonData){
        ArrayList<String> TempArr = new ArrayList<String>();

        JSONArray jsonArray = null;
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONArray("steps");
            Log.d("qwer",jsonArray.toString());
            for(int i=0;i<jsonArray.length();i++){
                TempArr.add(getPath(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return TempArr;
    }

    private String getPath(JSONObject googlePathJson){
        String polyline = "";
        try {
            polyline = googlePathJson.getJSONObject("polyline").getString("points");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return polyline;
    }


}
