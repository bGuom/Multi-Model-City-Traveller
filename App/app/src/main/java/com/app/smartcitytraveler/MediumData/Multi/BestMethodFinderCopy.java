package com.app.smartcitytraveler.MediumData.Multi;

import android.location.Location;
import android.os.AsyncTask;

import com.app.smartcitytraveler.DirectionDataAPI.CostDataCalculator;
import com.app.smartcitytraveler.DirectionDataAPI.DownloadUrl;
import com.app.smartcitytraveler.DirectionDataAPI.JsonDataParser;
import com.app.smartcitytraveler.MainActivity;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class BestMethodFinderCopy {

    private  static CostDataCalculator CC = MainActivity.costDataCalculator;
    private  static LatLng From = CC.getFrom();
    private  static LatLng To = CC.getTo();
    private  static Float walkSpeed = CC.testData.getWalkSpeed();
    private static int Finished = 0;


    public  static Float Cost_StartWalkToTaxi = Float.parseFloat("0");
    public  static  Float Cost_StartWalkToBus = Float.parseFloat("0");
    public  static Float Cost_StartWalkToTrain = Float.parseFloat("0");
    public  static Float Cost_StartTaxitoBus = Float.parseFloat("9999");
    public  static Float Cost_StartTaxitoTrain = Float.parseFloat("9999");
    public  static Float Cost_StartTaxitoEnd = Float.parseFloat("9999");
    public  static Float Cost_StartBustoEndBus = Float.parseFloat("9999");
    public  static Float Cost_StartTraintoEndTrain = Float.parseFloat("9999");
    public  static Float Cost_EndWalkFromTraintoEnd = Float.parseFloat("0");
    public  static Float Cost_EndWalkFromTraintoTaxi = Float.parseFloat("0");
    public  static Float Cost_EndWalkFromTraintoBus = Float.parseFloat("0");
    public  static Float Cost_EndWalkFromBustoEnd = Float.parseFloat("0");
    public  static Float Cost_EndWalkFromBustoTaxi = Float.parseFloat("0");
    public  static Float Cost_EndTrainTaxitoEnd = Float.parseFloat("9999");
    public  static Float Cost_EndBusTaxitoEnd = Float.parseFloat("9999");

    public  static Float Time_StartWalkToTaxi = Float.parseFloat("9999");
    public  static Float Time_StartWalkToBus = Float.parseFloat("9999");
    public  static Float Time_StartWalkToTrain = Float.parseFloat("9999");
    public  static Float Time_StartTaxitoBus = Float.parseFloat("9999");
    public  static Float Time_StartTaxitoTrain = Float.parseFloat("9999");
    public  static Float Time_StartTaxitoEnd = Float.parseFloat("9999");
    public  static Float Time_StartBustoEndBus = Float.parseFloat("9999");
    public  static Float Time_StartTraintoEndTrain = Float.parseFloat("9999");
    public  static Float Time_EndWalkFromTraintoEnd = Float.parseFloat("9999");
    public  static Float Time_EndWalkFromTraintoTaxi = Float.parseFloat("9999");
    public  static Float Time_EndWalkFromTraintoBus = Float.parseFloat("9999");
    public  static Float Time_EndWalkFromBustoEnd = Float.parseFloat("9999");
    public  static Float Time_EndWalkFromBustoTaxi = Float.parseFloat("9999");
    public  static Float Time_EndTrainTaxitoEnd = Float.parseFloat("9999");
    public  static Float Time_EndBusTaxitoEnd = Float.parseFloat("9999");


    public static  void updateWeights(){

        LatLng TaxiParkLatLng = CC.getNearestTaxiPark().getLatLng();
        LatLng FromBusHaltLatLng = CC.getNearestBusHaltFrom().getLatLng();
        LatLng FromTrainHaltLatLng = CC.getFromStation().getLatLng();
        LatLng ToBusHaltLatLng = CC.getNearestBusHaltTo().getLatLng();
        LatLng ToTrainHaltLatLng = CC.getEndStation().getLatLng();
        LatLng TaxiParkNearEndBusHalt = CC.getNearestTaxiParkToEndBusHalt().getLatLng();
        LatLng TaxiParkNearEndTrainStation = CC.getNearestTaxiParkToEndTrainStaion().getLatLng();

        float walk_distance[] = new float[10];
        Location.distanceBetween(From.latitude,From.longitude,TaxiParkLatLng.latitude,TaxiParkLatLng.longitude,walk_distance);
        Float DistanceToFromTaxiPark = walk_distance[0];
        Time_StartWalkToTaxi=((DistanceToFromTaxiPark/walkSpeed)/60);
        if(DistanceToFromTaxiPark>CC.getTestData().getMaxWalkDistance()){
            Cost_StartWalkToTaxi = Float.parseFloat("9999");
        }


        float walk_distance1[] = new float[10];
        Location.distanceBetween(From.latitude,From.longitude,FromBusHaltLatLng.latitude,FromBusHaltLatLng.longitude,walk_distance1);
        Float DistanceToFromBusHalt = walk_distance1[0];
        Time_StartWalkToBus=((DistanceToFromBusHalt/walkSpeed)/60);
        if(DistanceToFromBusHalt>CC.getTestData().getMaxWalkDistance()){
            Cost_StartWalkToBus = Float.parseFloat("9999");
        }

        float walk_distance2[] = new float[10];
        Location.distanceBetween(From.latitude,From.longitude,FromTrainHaltLatLng.latitude,FromTrainHaltLatLng.longitude,walk_distance2);
        Float DistanceToFromTrainStation = walk_distance2[0];
        Time_StartWalkToTrain=((DistanceToFromTrainStation/walkSpeed)/60);
        if(DistanceToFromTrainStation>CC.getTestData().getMaxWalkDistance()){
            Cost_StartWalkToTrain = Float.parseFloat("9999");
        }

        float walk_distance3[] = new float[10];
        Location.distanceBetween(To.latitude,To.longitude,ToBusHaltLatLng.latitude,ToBusHaltLatLng.longitude,walk_distance3);
        Float DistanceToToBusHalt = walk_distance3[0];
        Time_EndWalkFromBustoEnd=((DistanceToToBusHalt/walkSpeed)/60);
        if(DistanceToToBusHalt>CC.getTestData().getMaxWalkDistance()){
            Cost_EndWalkFromBustoEnd = Float.parseFloat("9999");
        }

        float walk_distance4[] = new float[10];
        Location.distanceBetween(From.latitude,From.longitude,ToTrainHaltLatLng.latitude,ToTrainHaltLatLng.longitude,walk_distance4);
        Float DistanceToToTrainStation = walk_distance4[0];
        Time_EndWalkFromTraintoEnd=((DistanceToToTrainStation/walkSpeed)/60);
        if(DistanceToToTrainStation>CC.getTestData().getMaxWalkDistance()){
            Cost_EndWalkFromTraintoEnd = Float.parseFloat("9999");
        }

        float walk_distance5[] = new float[10];
        Location.distanceBetween(ToBusHaltLatLng.latitude,ToBusHaltLatLng.longitude,ToTrainHaltLatLng.latitude,ToTrainHaltLatLng.longitude,walk_distance5);
        Float DistanceToToTrainStationToToBusHalt = walk_distance5[0];
        Time_EndWalkFromTraintoBus=((DistanceToToTrainStationToToBusHalt/walkSpeed)/60);
        if(DistanceToToTrainStationToToBusHalt>CC.getTestData().getMaxWalkDistance()){
            Cost_EndWalkFromTraintoBus = Float.parseFloat("9999");
        }

        float walk_distance6[] = new float[10];
        Location.distanceBetween(ToTrainHaltLatLng.latitude,ToTrainHaltLatLng.longitude,TaxiParkNearEndTrainStation.latitude,TaxiParkNearEndTrainStation.longitude,walk_distance6);
        Float DistanceToEndTrainStationToTaxiPark = walk_distance6[0];
        Time_EndWalkFromTraintoTaxi=((DistanceToEndTrainStationToTaxiPark/walkSpeed)/60);
        if(DistanceToEndTrainStationToTaxiPark>CC.getTestData().getMaxWalkDistance()){
            Cost_EndWalkFromTraintoTaxi = Float.parseFloat("9999");
        }

        float walk_distance7[] = new float[10];
        Location.distanceBetween(ToBusHaltLatLng.latitude,ToBusHaltLatLng.longitude,TaxiParkNearEndBusHalt.latitude,TaxiParkNearEndBusHalt.longitude,walk_distance7);
        Float DistanceToEndBusHaltToTaxiPark = walk_distance7[0];
        Time_EndWalkFromBustoTaxi=((DistanceToEndBusHaltToTaxiPark/walkSpeed)/60);
        if(DistanceToEndBusHaltToTaxiPark>CC.getTestData().getMaxWalkDistance()){
            Cost_EndWalkFromBustoTaxi = Float.parseFloat("9999");
        }



        Time_StartTaxitoEnd = Float.parseFloat(CC.getTaxiTime().split(" ")[0])-Time_StartWalkToTaxi;
        Time_StartBustoEndBus = Float.parseFloat(CC.getBusTime().split(" ")[0]);
        Time_StartTraintoEndTrain = Float.parseFloat(CC.getTrainTime().split(" ")[0])-Time_StartWalkToTrain-Time_EndWalkFromTraintoEnd;

        Cost_StartTaxitoEnd = Float.parseFloat(CC.getTaxiCost().split(" ")[0]);
        Cost_StartBustoEndBus = Float.parseFloat(CC.getBusCost().split(" ")[0]);
        Cost_StartTraintoEndTrain = Float.parseFloat(CC.getTrainCost().split(" ")[0]);


        new AsyncTask1().execute(getDirectionUrl(TaxiParkLatLng,FromBusHaltLatLng));
        new AsyncTask2().execute(getDirectionUrl(TaxiParkLatLng,FromTrainHaltLatLng));
        new AsyncTask3().execute(getDirectionUrl(TaxiParkLatLng,To));
        new AsyncTask4().execute(getDirectionUrl(TaxiParkNearEndBusHalt,To));



    }

    private static void finishLoad(){
        Finished+=1;
        if(Finished==4){

        }
    }

    private static String getDirectionUrl(LatLng start, LatLng end){

        StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
        stringBuilder.append("origin="+Double.toString( start.latitude)+","+Double.toString(start.longitude));
        stringBuilder.append("&destination="+Double.toString(end.latitude)+","+Double.toString(end.longitude));
        stringBuilder.append("&key="+MainActivity.GOOGLE_MAPS_API_KEY);

        return stringBuilder.toString();

    }

    public static class AsyncTask1 extends AsyncTask<Object,String,String> {

        String URL;
        String GoogleDirectionData;


        @Override
        protected String doInBackground(Object... objects) {

            URL = (String)objects[0];

            DownloadUrl downloadUrl = new DownloadUrl();

            try {
                GoogleDirectionData = downloadUrl.readUrl(URL);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return GoogleDirectionData;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            HashMap<String,String> TempHash;
            ArrayList<String> TempPath;
            JsonDataParser jsonDataParser = new JsonDataParser();
            TempHash = jsonDataParser.getDirectionData(s);

            String time = TempHash.get("duration");
            String distance = TempHash.get("distance");


            int totalTime = Integer.parseInt(time);
            int totalMins = Math.round(totalTime/60);
            Time_StartTaxitoBus = Float.parseFloat(Integer.toString(totalMins));

            float KM = Float.parseFloat(distance)/1000;

            Cost_StartTaxitoBus = Float.parseFloat(Integer.toString(Math.round(CC.testData.getTaxiFare()[0]+ (KM-1)*CC.testData.getTaxiFare()[1])));
            finishLoad();
        }





    }
    public static class AsyncTask2 extends AsyncTask<Object,String,String> {

        String URL;
        String GoogleDirectionData;


        @Override
        protected String doInBackground(Object... objects) {

            URL = (String)objects[0];

            DownloadUrl downloadUrl = new DownloadUrl();

            try {
                GoogleDirectionData = downloadUrl.readUrl(URL);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return GoogleDirectionData;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            HashMap<String,String> TempHash;
            ArrayList<String> TempPath;
            JsonDataParser jsonDataParser = new JsonDataParser();
            TempHash = jsonDataParser.getDirectionData(s);

            String time = TempHash.get("duration");
            String distance = TempHash.get("distance");


            int totalTime = Integer.parseInt(time);
            int totalMins = Math.round(totalTime/60);
            Time_StartTaxitoTrain = Float.parseFloat(Integer.toString(totalMins));

            float KM = Float.parseFloat(distance)/1000;

            Cost_StartTaxitoTrain = Float.parseFloat(Integer.toString(Math.round(CC.testData.getTaxiFare()[0]+ (KM-1)*CC.testData.getTaxiFare()[1])));
            finishLoad();

        }





    }
    public static class AsyncTask3 extends AsyncTask<Object,String,String> {

        String URL;
        String GoogleDirectionData;


        @Override
        protected String doInBackground(Object... objects) {

            URL = (String)objects[0];

            DownloadUrl downloadUrl = new DownloadUrl();

            try {
                GoogleDirectionData = downloadUrl.readUrl(URL);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return GoogleDirectionData;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            HashMap<String,String> TempHash;
            ArrayList<String> TempPath;
            JsonDataParser jsonDataParser = new JsonDataParser();
            TempHash = jsonDataParser.getDirectionData(s);
            String time = TempHash.get("duration");
            String distance = TempHash.get("distance");


            int totalTime = Integer.parseInt(time);
            int totalMins = Math.round(totalTime/60);
            Time_EndTrainTaxitoEnd = Float.parseFloat(Integer.toString(totalMins));

            float KM = Float.parseFloat(distance)/1000;

            Cost_EndTrainTaxitoEnd = Float.parseFloat(Integer.toString(Math.round(CC.testData.getTaxiFare()[0]+ (KM-1)*CC.testData.getTaxiFare()[1])));
            finishLoad();



        }





    }
    public static class AsyncTask4 extends AsyncTask<Object,String,String> {

        String URL;
        String GoogleDirectionData;


        @Override
        protected String doInBackground(Object... objects) {

            URL = (String)objects[0];

            DownloadUrl downloadUrl = new DownloadUrl();

            try {
                GoogleDirectionData = downloadUrl.readUrl(URL);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return GoogleDirectionData;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            HashMap<String,String> TempHash;
            ArrayList<String> TempPath;
            JsonDataParser jsonDataParser = new JsonDataParser();
            TempHash = jsonDataParser.getDirectionData(s);
            String time = TempHash.get("duration");
            String distance = TempHash.get("distance");


            int totalTime = Integer.parseInt(time);
            int totalMins = Math.round(totalTime/60);
            Time_EndBusTaxitoEnd = Float.parseFloat(Integer.toString(totalMins));

            float KM = Float.parseFloat(distance)/1000;

            Cost_EndBusTaxitoEnd = Float.parseFloat(Integer.toString(Math.round(CC.testData.getTaxiFare()[0]+ (KM-1)*CC.testData.getTaxiFare()[1])));
            finishLoad();



        }





    }



}
