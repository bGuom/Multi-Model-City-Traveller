package com.app.smartcitytraveler.MediumData.Controllers;

import android.util.Log;

import com.app.smartcitytraveler.MainActivity;
import com.app.smartcitytraveler.MediumData.BusData;
import com.app.smartcitytraveler.MediumData.BusTripRouteHalts;
import com.app.smartcitytraveler.MediumData.OptionClasses.TrainOptionData;
import com.app.smartcitytraveler.MediumData.SuperClasses.OptionData;
import com.app.smartcitytraveler.MediumData.TrainData;
import com.app.smartcitytraveler.PathFinder.BusPathFinder;
import com.app.smartcitytraveler.Stations.BusHalt;
import com.app.smartcitytraveler.Stations.TrainStation;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;


public class BusDataController {

    private ArrayList<BusHalt> BusHalts = new ArrayList<BusHalt>();
    private ArrayList<BusData> BusDataArray = new ArrayList<BusData>();
    private ArrayList<String> BusRoutes = new ArrayList<String>();


    public BusDataController(ArrayList<BusHalt> busHalts ,  ArrayList<BusData> busDataArray, ArrayList<String> busRoutes) {
        BusHalts = busHalts;
        BusDataArray = busDataArray;
        BusRoutes =busRoutes;
    }

    public ArrayList<BusHalt> getBusHalts() {
        return BusHalts;
    }

    public ArrayList<String> getBusRoutes(){return BusRoutes;}

    public void setBusHalts(ArrayList<BusHalt> busHalts) {
        BusHalts = busHalts;
    }

    public ArrayList<BusData> getBusDataArray() {
        return BusDataArray;
    }

    public void setBusDataArray(ArrayList<BusData> busDataArray) {
        BusDataArray = busDataArray;
    }

    public int getBusHaltCount(){
        return BusHalts.size();
    }

    public  BusHalt getBusHaltByID(int ID, String route){

        ArrayList<BusHalt> routeBusHalts = getBusHaltsinRoute(route);

        return null;

    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++=

    public ArrayList<BusHalt> getBusHaltsinRoute(String routeID){
        ArrayList<BusHalt> TempArray = new ArrayList<BusHalt>();

        for (BusHalt busHalt : getBusHalts()){
            if(busHalt.getRouteID()==routeID) {
                TempArray.add(busHalt);
            }
        }
        return  TempArray;
    }

    //+++++++++++++++++++++++++++++++++++++++++++++++++++=


    /**
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
    **/

}
