package com.app.smartcitytraveler.Stations;

import com.google.android.gms.maps.model.LatLng;

public class TrainStation {

    private int stationID;
    private String name;
    private LatLng LatLng;

    public TrainStation(){

    }

    public TrainStation(int stationID, String name, LatLng latLng) {
        this.stationID = stationID;
        this.name = name;
        this.LatLng = latLng;
    }

    public int getStationID() {
        return stationID;
    }

    public void setStationID(int stationID) {
        this.stationID = stationID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getLatLng() {
        return LatLng;
    }

    public void setLatLng(LatLng latLng) {
        LatLng = latLng;
    }
}
