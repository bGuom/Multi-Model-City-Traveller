package com.app.smartcitytraveler.MediumData;

public class BusData {

    private int BusID;
    private String BusNo;
    private String FromCity;
    private String ToCity;
    private String RouteID;
    private String PvtCtb;
    private String StartTime;
    private String EndTime;

    private BusData(){}

    public BusData(int busID, String busNo, String fromCity, String toCity, String routeID, String pvtCtb, String startTime, String endTime) {
        BusID = busID;
        BusNo = busNo;
        FromCity = fromCity;
        ToCity = toCity;
        RouteID = routeID;
        PvtCtb = pvtCtb;
        StartTime = startTime;
        EndTime = endTime;
    }

    public int getBusID() {
        return BusID;
    }

    public void setBusID(int busID) {
        BusID = busID;
    }

    public String getBusNo() {
        return BusNo;
    }

    public void setBusNo(String busNo) {
        BusNo = busNo;
    }

    public String getFromCity() {
        return FromCity;
    }

    public void setFromCity(String fromCity) {
        FromCity = fromCity;
    }

    public String getToCity() {
        return ToCity;
    }

    public void setToCity(String toCity) {
        ToCity = toCity;
    }

    public String getRouteID() {
        return RouteID;
    }

    public void setRouteID(String routeID) {
        RouteID = routeID;
    }

    public String getPvtCtb() {
        return PvtCtb;
    }

    public void setPvtCtb(String pvtCtb) {
        PvtCtb = pvtCtb;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }
}
