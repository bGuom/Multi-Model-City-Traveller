package com.app.smartcitytraveler.MediumData.SuperClasses;

import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public abstract class OptionData  {

    private String No;
    private String Start;
    private String End;
    private ArrayList<LatLng> Destinations; // StartLatLng, First Station LAtLng,....... ,EndLatLng
    private String StartTime;
    private String EndTime;
    private String TotalTime;
    private String Cost;
    private String StepMedium; // "WK,BS,TR,TX,MM"
    private String Days;

    public OptionData(String no, String start, String end, ArrayList<LatLng> destinations, String startTime, String endTime, String totalTime, String cost, String stepMedium, String days) {
        No = no;
        Start = start;
        End = end;
        Destinations = destinations;
        StartTime = startTime;
        EndTime = endTime;
        TotalTime=totalTime;
        Cost = cost;
        StepMedium = stepMedium;
        Days = days;
    }

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }

    public String getStart() {
        return Start;
    }

    public void setStart(String start) {
        Start = start;
    }

    public String getEnd() {
        return End;
    }

    public void setEnd(String end) {
        End = end;
    }

    public LatLng getStartLatLng() {
        return Destinations.get(0);
    }

    public LatLng getEndLatLng() {
        return Destinations.get(Destinations.size()-1);
    }

    public ArrayList<LatLng> getMiddleDestinations(){
        ArrayList<LatLng> TempArr =  new ArrayList<LatLng>();
        TempArr.addAll(Destinations.subList(1,Destinations.size()));
        return TempArr;
    }

    public ArrayList<LatLng> getDestinations() {
        return Destinations;
    }

    public void setDestinations(ArrayList<LatLng> destinations) {
        Destinations = destinations;
    }

    public LatLng getDestinationByID(int ID){
        return Destinations.get(ID);
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

    public String getTotalTime() {
        return TotalTime;
    }

    public void setTotalTime(String totalTime) {
        TotalTime = totalTime;
    }

    public String getCost() {
        return Cost;
    }

    public void setCost(String cost) {
        Cost = cost;
    }

    public String getStepMedium() {
        return StepMedium;
    }

    public void setStepMedium(String stepMedium) {
        StepMedium = stepMedium;
    }

    public String getDays() {
        return Days;
    }

    public void setDays(String days) {
        Days = days;
    }




}
