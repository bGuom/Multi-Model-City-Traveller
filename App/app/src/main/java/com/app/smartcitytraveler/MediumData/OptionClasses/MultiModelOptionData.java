package com.app.smartcitytraveler.MediumData.OptionClasses;


import com.app.smartcitytraveler.MediumData.SuperClasses.OptionData;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MultiModelOptionData extends OptionData {


    public MultiModelOptionData(ArrayList<LatLng> destinations) {
        super("", "", "", destinations, "", "", "", "","MM","0");
    }

}
