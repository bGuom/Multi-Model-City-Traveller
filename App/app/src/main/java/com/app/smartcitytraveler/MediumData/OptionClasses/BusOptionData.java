package com.app.smartcitytraveler.MediumData.OptionClasses;


import com.app.smartcitytraveler.MediumData.SuperClasses.OptionData;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class BusOptionData extends OptionData {


    public BusOptionData(String no, String start, String end, ArrayList<LatLng> Destinations, String startTime, String endTime, String time, String cost) {
        super(no, start, end, Destinations, startTime, endTime, time, cost,"BS","0");
    }

}
