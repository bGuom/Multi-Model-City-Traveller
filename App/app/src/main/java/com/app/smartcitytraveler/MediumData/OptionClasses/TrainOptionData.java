package com.app.smartcitytraveler.MediumData.OptionClasses;


import com.app.smartcitytraveler.MediumData.SuperClasses.OptionData;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class TrainOptionData extends OptionData {


    public TrainOptionData(String no, String start, String end, ArrayList<LatLng> Destinations, String startTime, String endTime, String time, String cost, String days) {
        super(no, start, end, Destinations, startTime, endTime, time, cost,"TR",days);
    }

}
