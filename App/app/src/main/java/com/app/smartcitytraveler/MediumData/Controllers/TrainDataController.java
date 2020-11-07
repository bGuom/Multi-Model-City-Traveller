package com.app.smartcitytraveler.MediumData.Controllers;

import android.util.Log;

import com.app.smartcitytraveler.MainActivity;
import com.app.smartcitytraveler.MediumData.SuperClasses.OptionData;
import com.app.smartcitytraveler.MediumData.TrainData;
import com.app.smartcitytraveler.MediumData.OptionClasses.TrainOptionData;
import com.app.smartcitytraveler.Stations.TrainStation;
import com.google.android.gms.maps.model.LatLng;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class TrainDataController {

    private ArrayList<TrainStation> TrainStations = new ArrayList<TrainStation>();
    private ArrayList<TrainData> UpTrainDataArray = new ArrayList<TrainData>();
    private ArrayList<TrainData> DownTrainDataArray = new ArrayList<TrainData>();

    public TrainDataController(ArrayList<TrainStation> trainStations , ArrayList<TrainData> upTrainDataArray , ArrayList<TrainData> downTrainDataArray) {
        TrainStations = trainStations;
        UpTrainDataArray = upTrainDataArray;
        DownTrainDataArray = downTrainDataArray;
    }

    public ArrayList<TrainData> getUpTrainDataArray() {
        return UpTrainDataArray;
    }

    public void setUpTrainDataArray(ArrayList<TrainData> trainDataArray) {
        UpTrainDataArray = trainDataArray;
    }

    public ArrayList<TrainData> getDownTrainDataArray() {
        return DownTrainDataArray;
    }

    public void setDownTrainDataArray(ArrayList<TrainData> downTrainDataArray) {
        DownTrainDataArray = downTrainDataArray;
    }

    public ArrayList<TrainStation> getTrainStations(){
        return TrainStations;
    }

    public void setTrainStations(ArrayList<TrainStation> TrainStaions){
        this.TrainStations=TrainStaions;
    }

    public TrainData getUpTrainDataByID(int ID){
        return UpTrainDataArray.get(ID);
    }

    public void addUpTrainData(TrainData trainData){
        UpTrainDataArray.add(trainData);
    }

    public TrainData getDownTrainDataByID(int ID){
        return DownTrainDataArray.get(ID);
    }

    public void addDownTrainData(TrainData trainData){
        DownTrainDataArray.add(trainData);
    }

    public TrainStation getTrainStationByID(int ID){
        return TrainStations.get(ID);
    }

    public void addTrainStation(TrainStation TrainStationLatLng){
        TrainStations.add(TrainStationLatLng);
    }

    public int getStationCount(){
        return TrainStations.size();
    }

    public int getUpDataCount(){
        return UpTrainDataArray.size();
    }

    public int getDownDataCount(){
        return DownTrainDataArray.size();
    }

    public ArrayList<LatLng> getTrainStationLatLngArr(){

        ArrayList<LatLng> TempArr = new ArrayList<LatLng>();

        for(TrainStation trainStation: getTrainStations()){
            TempArr.add(trainStation.getLatLng());
        }

        return TempArr;

    }


    //++++++++++++++++++++++++++++++++++++++++++++++++++=

    private ArrayList<String> getUpTrainStopingStationIDsArray(){
        ArrayList<String> TempArray = new ArrayList<String>();

        for (TrainData trainData : getUpTrainDataArray()){
            TempArray.add(trainData.getStoppingStationIds());
        }
        return  TempArray;
    }

    private ArrayList<String> getDownTrainStopingStationIDsArray(){
        ArrayList<String> TempArray = new ArrayList<String>();

        for (TrainData trainData : getDownTrainDataArray()){
            TempArray.add(trainData.getStoppingStationIds());
        }
        return  TempArray;
    }

    private ArrayList<String> getUpTrainTimesArray(){
        ArrayList<String> TempArray = new ArrayList<String>();

        for (TrainData trainData : getUpTrainDataArray()){
            TempArray.add(trainData.getTimes());
        }
        return  TempArray;
    }

    private ArrayList<String> getDownTrainTimesArray(){
        ArrayList<String> TempArray = new ArrayList<String>();

        for (TrainData trainData : getDownTrainDataArray()){
            TempArray.add(trainData.getTimes());
        }
        return  TempArray;
    }

    private ArrayList<String> getUpTrainDaysArray(){
        ArrayList<String> TempArray = new ArrayList<String>();

        for (TrainData trainData : getUpTrainDataArray()){
            TempArray.add(trainData.getDays());
        }
        return  TempArray;
    }

    private ArrayList<String> getDownTrainDaysArray(){
        ArrayList<String> TempArray = new ArrayList<String>();

        for (TrainData trainData : getDownTrainDataArray()){
            TempArray.add(trainData.getDays());
        }
        return  TempArray;
    }

    //+++++++++++++++++++++++++++++++++++++++++++++++++++=

    public ArrayList<OptionData> getOptionDataForTrip(LatLng Start, LatLng End, int fromStationID , int toStationID){   // staionIDs should be DB ids (1,2,3..) not array IDs(0,1,2..)

        ArrayList<OptionData> TempArray = new ArrayList<OptionData>();

        Log.d("OPTTEST",  " change "+Integer.toString(toStationID-fromStationID));

        if((toStationID-fromStationID)>0){
            //For Up Train Trips
            ArrayList<String> trainData = getUpTrainStopingStationIDsArray();
            for(int i =0; i<trainData.size();i++ ){
                String stoppingStations = trainData.get(i);
                Log.d("OPTTEST",  " stops "+stoppingStations);
                Log.d("OPTTEST",  " index "+Integer.toString(stoppingStations.indexOf(Integer.toString(fromStationID))));
                Log.d("OPTTEST",  " index "+Integer.toString(stoppingStations.indexOf(Integer.toString(toStationID))));

                if((stoppingStations.indexOf(Integer.toString(fromStationID))!=-1)&&(stoppingStations.indexOf(Integer.toString(toStationID))!=-1)) {
                    TrainData TempData = getUpTrainDataByID(i);
                    String TrainTimes = getUpTrainTimesArray().get(i);
                    String StartTime = TrainTimes.split(",")[stoppingStations.indexOf(Integer.toString(fromStationID)) / 2];
                    String EndTime = TrainTimes.split(",")[stoppingStations.indexOf(Integer.toString(toStationID)) / 2];
                    String cost = "100 RS";
                    String days = getUpTrainDaysArray().get(i);

                    Log.d("OPTTEST",  "in");
                    ArrayList<LatLng> destinations = new ArrayList<LatLng>();

                    destinations.add(Start);
                    destinations.add(TrainStations.get(fromStationID-1).getLatLng());
                    destinations.add(TrainStations.get(toStationID-1).getLatLng());
                    destinations.add(End);


                    TempArray.add(new TrainOptionData(TempData.getTrainNo(), getTrainStationByID(fromStationID - 1).getName(), getTrainStationByID(toStationID - 1).getName(),destinations, StartTime, EndTime,MainActivity.costDataCalculator.getTrainTime(), MainActivity.costDataCalculator.getTrainCost() ,days));

                }
            }

        }else{
            //For Down Train Trips
            ArrayList<String> trainData = getDownTrainStopingStationIDsArray();
            for(int k =0; k<trainData.size();k++ ){
                String stoppingStations = trainData.get(k);
                Log.d("OPTTEST",  " stops "+stoppingStations);

                if((stoppingStations.indexOf(Integer.toString(fromStationID))!=-1)&&(stoppingStations.indexOf(Integer.toString(toStationID))!=-1)){
                    TrainData TempData = getDownTrainDataByID( k);
                    String TrainTimes = getDownTrainTimesArray().get(k);
                    String StartTime = TrainTimes.split(",")[stoppingStations.indexOf(Integer.toString(fromStationID))/2];
                    String EndTime = TrainTimes.split(",")[stoppingStations.indexOf(Integer.toString(toStationID))/2];
                    String cost = "100 RS";
                    String days = getDownTrainDaysArray().get(k);

                    ArrayList<LatLng> destinations = new ArrayList<LatLng>();
                    destinations.add(Start);
                    destinations.add(TrainStations.get(fromStationID-1).getLatLng());
                    destinations.add(TrainStations.get(toStationID-1).getLatLng());
                    destinations.add(End);

                    TempArray.add(new TrainOptionData(TempData.getTrainNo(),getTrainStationByID(fromStationID-1).getName(),getTrainStationByID(toStationID-1).getName(),destinations,StartTime,EndTime,MainActivity.costDataCalculator.getTrainTime(),MainActivity.costDataCalculator.getTrainCost(),days));
                }
            }

        }




        return TempArray;

    }



    public Float getTimeInMinsToNextTrain (int fromStationID , int toStationID){

        ArrayList<Long> TempArray = new ArrayList<Long>();
        Calendar rightNow = Calendar.getInstance();


        long offset = rightNow.get(Calendar.ZONE_OFFSET) +
                rightNow.get(Calendar.DST_OFFSET);

        long time = (rightNow.getTimeInMillis() + offset) %
                (24 * 60 * 60 * 1000);

        if((toStationID-fromStationID)>0){
            //For Up Train Trips
            ArrayList<String> trainData = getUpTrainStopingStationIDsArray();
            for(int i =0; i<trainData.size();i++ ){
                String stoppingStations = trainData.get(i);

                if((stoppingStations.indexOf(Integer.toString(fromStationID))!=-1)&&(stoppingStations.indexOf(Integer.toString(toStationID))!=-1)) {
                    String TrainTimes = getUpTrainTimesArray().get(i);
                    String TrainTime = TrainTimes.split(",")[stoppingStations.indexOf(Integer.toString(fromStationID)) / 2];
                    Long StartTime =  (Long.parseLong(TrainTime.split(":")[0])*3600000)  + (Long.parseLong(TrainTime.split(":")[1])*60000);
                    String days = getUpTrainDaysArray().get(i);

                    Calendar calendar = Calendar.getInstance();
                    int day = calendar.get(Calendar.DAY_OF_WEEK);

                    String today = "0";
                    switch (day) {

                        case Calendar.MONDAY:
                            today="1";
                            break;
                        case Calendar.TUESDAY:
                            today="2";
                            break;
                        case Calendar.WEDNESDAY:
                            today="3";
                            break;
                        case Calendar.THURSDAY:
                            today="4";
                            break;
                        case Calendar.FRIDAY:
                            today="5";
                            break;
                        case Calendar.SATURDAY:
                            today="7";
                            break;
                        case Calendar.SUNDAY:
                            today="7";
                            break;
                    }
                    Log.d("timetonext", Long.toString((StartTime-time)/60000));

                    if(( days.indexOf("0")!=-1 || days.indexOf(today)!=-1)&&(StartTime-time>=0)) {
                        TempArray.add(StartTime);
                    }

                }
            }

        }else{
            //For Down Train Trips
            ArrayList<String> trainData = getDownTrainStopingStationIDsArray();
            for(int k =0; k<trainData.size();k++ ){
                String stoppingStations = trainData.get(k);

                if((stoppingStations.indexOf(Integer.toString(fromStationID))!=-1)&&(stoppingStations.indexOf(Integer.toString(toStationID))!=-1)){
                    String TrainTimes = getDownTrainTimesArray().get(k);
                    String TrainTime = TrainTimes.split(",")[stoppingStations.indexOf(Integer.toString(fromStationID)) / 2];
                    Long StartTime =  (Long.parseLong(TrainTime.split(":")[0])*3600000)  + (Long.parseLong(TrainTime.split(":")[1])*60000);
                    String days = getDownTrainDaysArray().get(k);

                    Calendar calendar = Calendar.getInstance();
                    int day = calendar.get(Calendar.DAY_OF_WEEK);

                    String today = "0";
                    switch (day) {

                        case Calendar.MONDAY:
                            today="1";
                            break;
                        case Calendar.TUESDAY:
                            today="2";
                            break;
                        case Calendar.WEDNESDAY:
                            today="3";
                            break;
                        case Calendar.THURSDAY:
                            today="4";
                            break;
                        case Calendar.FRIDAY:
                            today="5";
                            break;
                        case Calendar.SATURDAY:
                            today="7";
                            break;
                        case Calendar.SUNDAY:
                            today="7";
                            break;
                    }

                    Log.d("timetonext", Long.toString((StartTime-time)/ 60000));

                    if(( days.indexOf("0")!=-1 || days.indexOf(today)!=-1)&&(StartTime-time>=0)) {
                        TempArray.add(StartTime);
                    }
                }
            }

        }

        Float TimeToNextTrain = Float.parseFloat("9999");
        if(TempArray.size()>0) {
            TimeToNextTrain = Float.parseFloat(Long.toString((TempArray.get(0) - time) / 60000));
        }


        Log.d("Timetonext",Float.toString(TimeToNextTrain));
        return TimeToNextTrain;

    }
}
