package com.app.smartcitytraveler.Stations;

import com.google.android.gms.maps.model.LatLng;

public class TaxiPark {

    private int ID;
    private LatLng latLng;

    public TaxiPark(){}

    public TaxiPark(int ID, LatLng latLng) {
        this.ID = ID;
        this.latLng = latLng;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
