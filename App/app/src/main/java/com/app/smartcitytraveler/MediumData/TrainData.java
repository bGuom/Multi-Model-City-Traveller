package com.app.smartcitytraveler.MediumData;

public class TrainData {

    private int TrainID;
    private String TrainNo;
    private String StoppingStationIds;
    private String Times;
    private String Days;

    private TrainData(){}

    public TrainData(int trainID, String trainNo, String stoppingStationIds, String times, String days) {
        TrainID = trainID;
        TrainNo = trainNo;
        StoppingStationIds = stoppingStationIds;
        Times = times;
        Days = days;
    }

    public int getTrainID() {
        return TrainID;
    }

    public void setTrainID(int trainID) {
        TrainID = trainID;
    }

    public String getTrainNo() {
        return TrainNo;
    }

    public void setTrainNo(String trainNo) {
        TrainNo = trainNo;
    }

    public String getStoppingStationIds() {
        return StoppingStationIds;
    }

    public void setStoppingStationIds(String stoppingStationIds) {
        StoppingStationIds = stoppingStationIds;
    }

    public String getTimes() {
        return Times;
    }

    public void setTimes(String times) {
        Times = times;
    }

    public String getDays() {
        return Days;
    }

    public void setDays(String days) {
        Days = days;
    }
}
