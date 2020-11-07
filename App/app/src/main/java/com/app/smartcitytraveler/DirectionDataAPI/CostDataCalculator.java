package com.app.smartcitytraveler.DirectionDataAPI;

import android.location.Location;
import android.util.Log;

import com.app.smartcitytraveler.MainActivity;
import com.app.smartcitytraveler.MediumData.BusTripRouteHalts;
import com.app.smartcitytraveler.MediumData.Controllers.BusDataController;
import com.app.smartcitytraveler.MediumData.Controllers.TrainDataController;
import com.app.smartcitytraveler.MediumData.Multi.BestMethodFinder;
import com.app.smartcitytraveler.PathFinder.BusPathFinder;
import com.app.smartcitytraveler.PathFinder.TaxiPathFinder;
import com.app.smartcitytraveler.PathFinder.TrainPathFinder;
import com.app.smartcitytraveler.Stations.BusHalt;
import com.app.smartcitytraveler.Stations.TaxiPark;
import com.app.smartcitytraveler.Stations.TrainStation;
import com.app.smartcitytraveler.TestData.TestData;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class CostDataCalculator implements AsyncResponseHandleable {

    public static LatLng From;
    public static LatLng To;

    private MainActivity parent;
    private int AllLoaded = 0;

    //===============================
    public static TestData testData = new TestData();
    //===============================

    //=========================================
    //=========================================

    //==============================================
    private static String BusCost = "";
    private static ArrayList<Integer> BusCostArray;
    private static String TrainCost = "";
    private static String TaxiCost = "";
    private static String MultiCost = "";

    private static String BusTime = "";
    private static ArrayList<Float> BusTimeArray;
    private static String TrainTime = "";
    private static String TaxiTime = "";
    private static String MultiTime = "";

    private static Float TimeToNextTrain =Float.parseFloat("9999");

    private  static ArrayList<String> BusPolyPaths;
    private  static ArrayList<String> TaxiPolyPaths;
    private  static ArrayList<String> MultiPolyPaths;
    //===============================================


    //========================================
    private static TrainStation FromStation;
    private static TrainStation EndStation;
    private static TaxiPark NearestTaxiPark;
    private static TaxiPark NearestTaxiParkToEndBusHalt;
    private static TaxiPark NearestTaxiParkToEndTrainStaion;
    private static BusHalt NearestBusHaltFrom;
    private static BusHalt NearestBusHaltTo;
    //========================================


    //========================
    private TrainDataController trainDataController;
    public static BusDataController busDataController;
    private TrainPathFinder trainPathFinder;
    private TaxiPathFinder taxiPathFinder;
    private BusPathFinder busPathFinder;
    //========================


    //========================
    public static BestMethodFinder bestMethodFinder;
    //========================

    //=======================
    private DirectionDataGetter directionDataGetter = DirectionDataGetter.getInstance();
    //=======================


    //=======================
    private static ArrayList<ArrayList<BusTripRouteHalts>> arrayOfArraysOfBusTripRouteHalt;
    //=======================


    public CostDataCalculator(MainActivity mainActivity) {
        parent = mainActivity;
        trainDataController = testData.getTestTrainData();
        busDataController = testData.getBusDataController();
        trainPathFinder = new TrainPathFinder(trainDataController);
        taxiPathFinder = new TaxiPathFinder(testData.getTestTaxiParksArray());
        busPathFinder = new BusPathFinder(busDataController);

    }

    public void updateFromTo(LatLng from, LatLng to) {
        AllLoaded=0;
        From = from;
        To = to;
        calculateTravelData();
    }

    public void calculateTravelData(){
        calculateBusData();
        calculateTrainData();
        calculateTaxiData();
        calculateMultiData();
    }

    private void calculateBusData(){
        BusCost = "";
        BusTime = "-";


        NearestBusHaltFrom = busPathFinder.getClosestBusStation(From);
        NearestBusHaltTo = busPathFinder.getClosestBusStation(To);


        arrayOfArraysOfBusTripRouteHalt = busPathFinder.getRouteOptionsForTrip(From,To);

        if(arrayOfArraysOfBusTripRouteHalt.size()==0){
            AllLoaded+=1;
            if(AllLoaded==4){
                parent.showbottombar();
            }
        }
        else{

            BusCostArray = new ArrayList<Integer>();
            BusTimeArray = new ArrayList<Float>();

            for (ArrayList<BusTripRouteHalts> busTripRouteHaltsArray: arrayOfArraysOfBusTripRouteHalt) {

                int cost =0;
                Float time = Float.parseFloat("0");
                for(BusTripRouteHalts busTripRouteHalts: busTripRouteHaltsArray ){
                    cost+=busTripRouteHalts.getCost();
                    time+=busTripRouteHalts.getTime();

                }
                Log.d("fghj","cost "+Integer.toString(cost));
                Log.d("fghj","time "+Float.toString(time));
                BusCostArray.add(cost);
                BusTimeArray.add(time);
            }

            Log.d("fghj","cost array" + BusCostArray.toString() );
            Log.d("fghj","time array" + BusTimeArray.toString());

            BusCost = Integer.toString(Collections.min(BusCostArray)) + " RS" ;
            BusTime = Integer.toString(Math.round(Collections.min(BusTimeArray))) + " Min";

            AllLoaded+=1;
            if(AllLoaded==4){
                parent.showbottombar();
            }


        }

    }
    private void calculateTrainData(){


        FromStation = trainPathFinder.getClosestTrainStaion(From);
        EndStation = trainPathFinder.getClosestTrainStaion(To);

        int dif =  Math.abs(FromStation.getStationID()-EndStation.getStationID());

        float walk_distance1[] = new float[10];
        float walk_distance2[] = new float[10];
        Location.distanceBetween(From.latitude,From.longitude,FromStation.getLatLng().latitude,FromStation.getLatLng().longitude,walk_distance1);
        Location.distanceBetween(To.latitude,To.longitude,EndStation.getLatLng().latitude,EndStation.getLatLng().longitude,walk_distance2);

        Float totalWalkDistance = walk_distance1[0]+walk_distance2[0];
        Float walkSpeed = testData.getWalkSpeed();

        Float walkTimeSecs = totalWalkDistance/walkSpeed;
        int walkMins = Math.round(walkTimeSecs/60);
        switch (dif){
            case 1:
                TrainCost="10 RS";
                TrainTime = Integer.toString(5 + walkMins) + " Min";
                break;
            case 2:
                TrainCost="10 RS";
                TrainTime = Integer.toString(10 + walkMins) + " Min";
                break;
            case 3:
                TrainCost="15 RS";
                TrainTime = Integer.toString(20 + walkMins) + " Min";
                break;
            case 4:
                TrainCost="20 RS";
                TrainTime = Integer.toString(30 + walkMins) + " Min";
                break;
            case 5:
                TrainCost="25 RS";
                TrainTime = Integer.toString(35 + walkMins) + " Min";
                break;
            case 6:
                TrainCost="25 RS";
                TrainTime = Integer.toString(40 + walkMins) + " Min";
                break;
            case 7:
                TrainCost="30 RS";
                TrainTime = Integer.toString(50 + walkMins) + " Min";
                break;
        }

        AllLoaded+=1;
        if(AllLoaded==4){
            parent.showbottombar();
        }


    }
    private void calculateTaxiData(){




        NearestTaxiPark = taxiPathFinder.getClosestTaxiPark(From);
        Log.d("poiu",NearestTaxiPark.getLatLng().toString());
        NearestTaxiParkToEndBusHalt = taxiPathFinder.getClosestTaxiPark(NearestBusHaltTo.getLatLng());
        NearestTaxiParkToEndTrainStaion = taxiPathFinder.getClosestTaxiPark(EndStation.getLatLng());

        float walk_distance[] = new float[10];
        Location.distanceBetween(From.latitude,From.longitude,NearestTaxiPark.getLatLng().latitude,NearestTaxiPark.getLatLng().longitude,walk_distance);

        Float walkSpeed = testData.getWalkSpeed();
        Float walkTimeSecs = walk_distance[0]/walkSpeed;
        int walkMins = Math.round(walkTimeSecs/60);
        TaxiTime = Integer.toString(walkMins);


        /**
        //FAKE DATA X-X-X-X-X-X-X-X-X-X-X-X-X-X-X-X-X
        float taxi_distance[] = new float[10];
        Location.distanceBetween(NearestTaxiPark.getLatLng().latitude,NearestTaxiPark.getLatLng().longitude,To.latitude,To.longitude,taxi_distance);


        float speed = Float.parseFloat("11.176");
        Float driveTimeSecs = taxi_distance[0]/speed;
        int driveMins = Math.round(driveTimeSecs/60);


        int totalTime = driveMins+walkMins;
        TaxiTime = Integer.toString(totalTime) + " Mins";

        float KM = taxi_distance[0]/1000;

        TaxiCost = Integer.toString(Math.round(testData.getTaxiFare()[0]+ (KM-1)*testData.getTaxiFare()[1])) + " RS";

        AllLoaded+=1;
        if(AllLoaded==4){
            parent.showbottombar();
        }
        //FAKE DATA X-X-X-X-X-X-X-X-X-X-X-X-X-X-X-X-X

         **/

        getDirectionalData1(NearestTaxiPark.getLatLng(),To,"DR");

    }
    private void calculateMultiData(){
        MultiCost="-";
        MultiTime="-";

       TimeToNextTrain =  trainDataController.getTimeInMinsToNextTrain(FromStation.getStationID(),EndStation.getStationID());


        AllLoaded+=1;
        if(AllLoaded==4){
            parent.showbottombar();
        }
    }


    //=================================================================================================


    public static LatLng getTo() {
        return To;
    }

    public static LatLng getFrom() {
        return From;
    }

    public static String getBusCost() {
        return BusCost;
    }

    public static String getTrainCost() {
        return TrainCost;
    }

    public static String getTaxiCost() {
        return TaxiCost;
    }

    public static String getMultiCost() {
        return MultiCost;
    }

    public static String getBusTime() {
        return BusTime;
    }

    public static String getTrainTime() {
        return TrainTime;
    }

    public static String getTaxiTime() {
        return TaxiTime;
    }

    public  static String getMultiTime() {
        return MultiTime;
    }

    public  static TestData getTestData() {
        return testData;
    }

    public static TrainStation getFromStation() {
        return FromStation;
    }

    public  static TrainStation getEndStation() {
        return EndStation;
    }

    public  static TaxiPark getNearestTaxiPark() {
        return NearestTaxiPark;
    }

    public static TaxiPark getNearestTaxiParkToEndBusHalt() {
        return NearestTaxiParkToEndBusHalt;
    }

    public static TaxiPark getNearestTaxiParkToEndTrainStaion() {
        return NearestTaxiParkToEndTrainStaion;
    }

    public  static BusHalt getNearestBusHaltFrom() {
        return NearestBusHaltFrom;
    }

    public static BusHalt getNearestBusHaltTo() {
        return NearestBusHaltTo;
    }

    public static ArrayList<String> getBusPolyPaths() {
        return BusPolyPaths;
    }

    public static ArrayList<String> getTaxiPolyPaths() {
        return TaxiPolyPaths;
    }

    public static ArrayList<String> getMultiPolyPaths() {
        return MultiPolyPaths;
    }

    public  TrainDataController getTrainDataController() {
        return trainDataController;
    }

    public TrainPathFinder getTrainPathFinder() {
        return trainPathFinder;
    }

    public BusPathFinder getBusPathFinder() {
        return busPathFinder;
    }

    public TaxiPathFinder getTaxiPathFinder() {
        return taxiPathFinder;
    }

    public static ArrayList<ArrayList<BusTripRouteHalts>> getArrayOfArraysOfBusTripRouteHalt() {
        return arrayOfArraysOfBusTripRouteHalt;
    }

    public static ArrayList<Integer> getBusCostArray() {
        return BusCostArray;
    }

    public static ArrayList<Float> getBusTimeArray() {
        return BusTimeArray;
    }

    public static Float getTimeToNextTrain() {
        return TimeToNextTrain;
    }

    //=================================================================================================
    private void getDirectionalData1(LatLng start, LatLng end, String method){


        Object[] dataTransfer = new Object[1];
        String url = (directionDataGetter.getDirectionUrl(start,end,method));
        dataTransfer[0]=url;
        GetAsyncDirectionsData getAsyncDirectionsData = new GetAsyncDirectionsData();
        getAsyncDirectionsData.Parent=this;
        getAsyncDirectionsData.execute(dataTransfer);

    }

    private void getDirectionalData2(LatLng start, LatLng end, String method){

        Object[] dataTransfer = new Object[1];
        String url = (directionDataGetter.getDirectionUrl(start,end,method));
        dataTransfer[0]=url;
        GetAsyncDirectionsData getAsyncDirectionsData2 = new GetAsyncDirectionsData();
        getAsyncDirectionsData2.Parent=this;
        getAsyncDirectionsData2.execute(dataTransfer);

    }

    private void getDirectionalData3(LatLng start, LatLng end, String method){


        Object[] dataTransfer = new Object[1];
        String url = (directionDataGetter.getDirectionUrl(start,end,method));
        dataTransfer[0]=url;
        GetAsyncDirectionsData getAsyncDirectionsData3 = new GetAsyncDirectionsData();
        getAsyncDirectionsData3.Parent=this;
        getAsyncDirectionsData3.execute(dataTransfer);

    }



    //=====================================================================================================
    @Override
    public void processFinish(HashMap<String, String> directionDataHash, ArrayList<String> pollyPaths) {
        //Handle Results Here;
        TaxiPolyPaths = pollyPaths;
        String time = directionDataHash.get("duration");
        String distance = directionDataHash.get("distance");

        int totalTime = Integer.parseInt(time)+Integer.parseInt(TaxiTime);
        int totalMins = Math.round(totalTime/60);
        TaxiTime = Integer.toString(totalMins) + " Mins";

        float KM = Float.parseFloat(distance)/1000;

        TaxiCost = Integer.toString(Math.round(testData.getTaxiFare()[0]+ (KM-1)*testData.getTaxiFare()[1])) + " RS";

        AllLoaded+=1;
        if(AllLoaded==4){
            parent.showbottombar();
        }

    }

    @Override
    public void processFinish2(HashMap<String, String> directionDataHash, ArrayList<String> pollyPaths) {
        //Handle Results Here;

    }

    @Override
    public void processFinish3(HashMap<String, String> directionDataHash, ArrayList<String> pollyPaths) {
        //Handle Results Here;

    }

}
