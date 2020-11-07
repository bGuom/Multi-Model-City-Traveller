package com.app.smartcitytraveler.PathFinder;

import com.google.android.gms.maps.model.LatLng;

public class Step {

    private int StepNum;
    private String StepMedium; // "WK,BS,TR,TX,MM"
    private String StepName;
    private String StepDetails;
    private LatLng StartLatLng;
    private LatLng EndLatLng;
    private String OnTime; // Format HH:MM:SS 24 Hour Format

    public Step(int stepNum, String stepMedium, String stepName, String stepDetails, LatLng startLatLng, LatLng endLatLng, String onTime) {
        StepNum = stepNum;
        StepMedium = stepMedium;
        StepName = stepName;
        StepDetails = stepDetails;
        StartLatLng = startLatLng;
        EndLatLng = endLatLng;
        OnTime = onTime;
    }

    public int getStepNum() {
        return StepNum;
    }

    public void setStepNum(int stepNum) {
        StepNum = stepNum;
    }

    public String getStepMedium() {
        return StepMedium;
    }

    public void setStepMedium(String stepMedium) {
        StepMedium = stepMedium;
    }

    public String getStepName() {
        return StepName;
    }

    public void setStepName(String stepName) {
        StepName = stepName;
    }

    public String getStepDetails() {
        return StepDetails;
    }

    public LatLng getStartLatLng() {
        return StartLatLng;
    }

    public void setStartLatLng(LatLng startLatLng) {
        StartLatLng = startLatLng;
    }

    public LatLng getEndLatLng() {
        return EndLatLng;
    }

    public void setEndLatLng(LatLng endLatLng) {
        EndLatLng = endLatLng;
    }

    public void setStepDetails(String stepDetails) {
        StepDetails = stepDetails;
    }

    /**
    public LatLng getStepLatLng() {
        return StepLatLng;
    }

    public void setStepLatLng(LatLng stepLatLng) {
        StepLatLng = stepLatLng;
    }
     **/

    public String getOnTime() {
        return OnTime;
    }

    public void setOnTime(String onTime) {
        OnTime = onTime;
    }
}

