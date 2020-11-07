package com.app.smartcitytraveler.MediumData.OptionClasses;


import com.app.smartcitytraveler.MediumData.SuperClasses.OptionData;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class TaxiOptionData extends OptionData {


    public TaxiOptionData(ArrayList<LatLng> Destinations, String time, String cost) {

        super("TAXI", "Nearest Taxi Park","", Destinations, "", "", time, cost,"TX","0");
    }


}
