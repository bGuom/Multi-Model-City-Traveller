package com.app.smartcitytraveler.Stations;

import com.google.android.gms.maps.model.LatLng;

public class BusHalt {

    private int ID;
    private int Order;
    private String HaltName;
    private LatLng latLng;
    private String RouteID;
    private String Distance;


    public BusHalt(){}


    public BusHalt(int ID, int order, String haltName, LatLng latLng, String routeID,String distance) {
        this.ID = ID;
        Order = order;
        HaltName = haltName;
        this.latLng = latLng;
        RouteID = routeID;
        Distance = distance;

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getOrder() {
        return Order;
    }

    public void setOrder(int order) {
        Order = order;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getHaltName() {
        return HaltName;
    }

    public void setHaltName(String haltName) {
        HaltName = haltName;
    }

    public String getRouteID() {
        return RouteID;
    }

    public void setRouteID(String routeID) {
        RouteID = routeID;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String distance) {
        Distance = distance;
    }


}
