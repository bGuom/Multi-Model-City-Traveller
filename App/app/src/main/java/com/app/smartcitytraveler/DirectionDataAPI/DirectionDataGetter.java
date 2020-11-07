package com.app.smartcitytraveler.DirectionDataAPI;

import com.app.smartcitytraveler.MainActivity;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

public class DirectionDataGetter {


    private static DirectionDataGetter single_instance = null;
    private HashMap<String,String> result1,result2,result3;


    public static DirectionDataGetter getInstance()
    {
        if (single_instance == null)
            single_instance = new DirectionDataGetter();

        return single_instance;
    }

    //41.43206,-81.38992
    //41.43206,-81.38995

    public String getDirectionUrl(LatLng start, LatLng end, String method){
        String mode ="driving";

        switch (method){
            case "WK":
                mode="walking";
                break;

        }

        StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
        stringBuilder.append("origin="+Double.toString( start.latitude)+","+Double.toString(start.longitude));
        stringBuilder.append("&destination="+Double.toString(end.latitude)+","+Double.toString(end.longitude));
        stringBuilder.append("&key=" + MainActivity.GOOGLE_MAPS_API_KEY);
        stringBuilder.append("&mode="+mode);



        return stringBuilder.toString();

    }






}
