package com.app.smartcitytraveler.MediumData.Multi;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.app.smartcitytraveler.DirectionDataAPI.CostDataCalculator;
import com.app.smartcitytraveler.DirectionDataAPI.DownloadUrl;
import com.app.smartcitytraveler.DirectionDataAPI.JsonDataParser;
import com.app.smartcitytraveler.MainActivity;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class BestMethodFinder {


    private   LatLng From;
    private   LatLng To;
    private   Float walkSpeed;
    private  int Finished = 0;

    public static int[][] MatrixCost;
    public static int[][] MatrixTime;
    public boolean Loaded = false;

    private LatLng TaxiParkLatLng;
    private LatLng FromBusHaltLatLng;
    private LatLng FromTrainHaltLatLng;
    private LatLng ToBusHaltLatLng;
    private LatLng ToTrainHaltLatLng;
    public static LatLng BusHaltNearEndTrain;
    private LatLng TaxiParkNearEndBusHalt;
    public static LatLng TaxiParkNearEndTrainStation;



    public   Float Cost_StartWalkToTaxi = Float.parseFloat("1");
    public    Float Cost_StartWalkToBus = Float.parseFloat("1");
    public   Float Cost_StartWalkToTrain = Float.parseFloat("1");
    public   Float Cost_StartTaxitoBus = Float.parseFloat("9999");
    public   Float Cost_StartTaxitoTrain = Float.parseFloat("9999");
    public   Float Cost_StartTaxitoEnd = Float.parseFloat("9999");
    public   Float Cost_StartBustoEndBus = Float.parseFloat("9999");
    public   Float Cost_StartTraintoEndTrain = Float.parseFloat("9999");
    public   Float Cost_EndWalkFromTraintoEnd = Float.parseFloat("1");
    public   Float Cost_EndWalkFromTraintoTaxi = Float.parseFloat("1");
    public   Float Cost_EndWalkFromTraintoTrainBus = Float.parseFloat("1");
    public   Float Cost_EndTrainTaxitoTrainBus = Float.parseFloat("9999");
    public   Float Cost_EndTrainBustoEndBus = Float.parseFloat("9999");
    public   Float Cost_EndWalkFromBustoEnd = Float.parseFloat("1");
    public   Float Cost_EndWalkFromBustoTaxi = Float.parseFloat("1");
    public   Float Cost_EndTrainTaxitoEnd = Float.parseFloat("9999");
    public   Float Cost_EndBusTaxitoEnd = Float.parseFloat("9999");

    public   Float Time_StartWalkToTaxi = Float.parseFloat("9999");
    public   Float Time_StartWalkToBus = Float.parseFloat("9999");
    public   Float Time_StartWalkToTrain = Float.parseFloat("9999");
    public   Float Time_StartTaxitoBus = Float.parseFloat("9999");
    public   Float Time_StartTaxitoTrain = Float.parseFloat("9999");
    public   Float Time_StartTaxitoEnd = Float.parseFloat("9999");
    public   Float Time_StartBustoEndBus = Float.parseFloat("9999");
    public   Float Time_StartTraintoEndTrain = Float.parseFloat("9999");
    public   Float Time_EndWalkFromTraintoEnd = Float.parseFloat("9999");
    public   Float Time_EndWalkFromTraintoTaxi = Float.parseFloat("9999");
    public   Float Time_EndWalkFromTraintoTrainBus = Float.parseFloat("9999");
    public   Float Time_EndTrainTaxitoTrainBus = Float.parseFloat("9999");
    public   Float Time_EndTrainBustoEndBus = Float.parseFloat("9999");
    public   Float Time_EndWalkFromBustoEnd = Float.parseFloat("9999");
    public   Float Time_EndWalkFromBustoTaxi = Float.parseFloat("9999");
    public   Float Time_EndTrainTaxitoEnd = Float.parseFloat("9999");
    public   Float Time_EndBusTaxitoEnd = Float.parseFloat("9999");


    public static ArrayList<String> poly1;
    public static ArrayList<String> poly2;
    public static ArrayList<String> poly3;
    public static ArrayList<String> poly4;
    public static ArrayList<String> poly5;
    public static ArrayList<String> poly6;

    public BestMethodFinder(){

        From = MainActivity.costDataCalculator.getFrom();
        To = MainActivity.costDataCalculator.getTo();
        walkSpeed = MainActivity.costDataCalculator.testData.getWalkSpeed();

        TaxiParkLatLng = MainActivity.costDataCalculator.getNearestTaxiPark().getLatLng();
        Log.d("poiu",TaxiParkLatLng.toString());
        FromBusHaltLatLng = MainActivity.costDataCalculator.getNearestBusHaltFrom().getLatLng();
        FromTrainHaltLatLng = MainActivity.costDataCalculator.getFromStation().getLatLng();
        ToBusHaltLatLng = MainActivity.costDataCalculator.getNearestBusHaltTo().getLatLng();
        ToTrainHaltLatLng = MainActivity.costDataCalculator.getEndStation().getLatLng();
        BusHaltNearEndTrain = MainActivity.costDataCalculator.getBusPathFinder().getClosestBusStation(ToTrainHaltLatLng).getLatLng();
        TaxiParkNearEndBusHalt = MainActivity.costDataCalculator.getNearestTaxiParkToEndBusHalt().getLatLng();
        TaxiParkNearEndTrainStation = MainActivity.costDataCalculator.getNearestTaxiParkToEndTrainStaion().getLatLng();


    }
    
    public   void updateWeights(){


        float walk_distance[] = new float[10];
        Location.distanceBetween(From.latitude,From.longitude,TaxiParkLatLng.latitude,TaxiParkLatLng.longitude,walk_distance);
        Float DistanceToFromTaxiPark = walk_distance[0];
        Time_StartWalkToTaxi=((DistanceToFromTaxiPark/walkSpeed)/60);
        if(DistanceToFromTaxiPark>MainActivity.costDataCalculator.getTestData().getMaxWalkDistance()){
            Cost_StartWalkToTaxi = Float.parseFloat("9999");
        }


        float walk_distance1[] = new float[10];
        Location.distanceBetween(From.latitude,From.longitude,FromBusHaltLatLng.latitude,FromBusHaltLatLng.longitude,walk_distance1);
        Float DistanceToFromBusHalt = walk_distance1[0];
        Time_StartWalkToBus=((DistanceToFromBusHalt/walkSpeed)/60);
        if(DistanceToFromBusHalt>MainActivity.costDataCalculator.getTestData().getMaxWalkDistance()){
            Cost_StartWalkToBus = Float.parseFloat("9999");
        }

        float walk_distance2[] = new float[10];
        Location.distanceBetween(From.latitude,From.longitude,FromTrainHaltLatLng.latitude,FromTrainHaltLatLng.longitude,walk_distance2);
        Float DistanceToFromTrainStation = walk_distance2[0];
        Time_StartWalkToTrain=((DistanceToFromTrainStation/walkSpeed)/60);
        if(DistanceToFromTrainStation>MainActivity.costDataCalculator.getTestData().getMaxWalkDistance()){
            Cost_StartWalkToTrain = Float.parseFloat("9999");
        }

        float walk_distance3[] = new float[10];
        Location.distanceBetween(To.latitude,To.longitude,ToBusHaltLatLng.latitude,ToBusHaltLatLng.longitude,walk_distance3);
        Float DistanceToToBusHalt = walk_distance3[0];
        Time_EndWalkFromBustoEnd=((DistanceToToBusHalt/walkSpeed)/60);
        if(DistanceToToBusHalt>MainActivity.costDataCalculator.getTestData().getMaxWalkDistance()){
            Cost_EndWalkFromBustoEnd = Float.parseFloat("9999");
        }

        float walk_distance4[] = new float[10];
        Location.distanceBetween(To.latitude,To.longitude,ToTrainHaltLatLng.latitude,ToTrainHaltLatLng.longitude,walk_distance4);
        Float DistanceToToTrainStation = walk_distance4[0];
        Time_EndWalkFromTraintoEnd=((DistanceToToTrainStation/walkSpeed)/60);
        if(DistanceToToTrainStation>MainActivity.costDataCalculator.getTestData().getMaxWalkDistance()){
            Cost_EndWalkFromTraintoEnd = Float.parseFloat("9999");
        }


        float walk_distance5[] = new float[10];
        Location.distanceBetween(BusHaltNearEndTrain.latitude,BusHaltNearEndTrain.longitude,ToTrainHaltLatLng.latitude,ToTrainHaltLatLng.longitude,walk_distance5);
        Float DistanceToToTrainStationToToBusHalt = walk_distance5[0];
        Time_EndWalkFromTraintoTrainBus=((DistanceToToTrainStationToToBusHalt/walkSpeed)/60);
        if(DistanceToToTrainStationToToBusHalt>MainActivity.costDataCalculator.getTestData().getMaxWalkDistance()){
            Cost_EndWalkFromTraintoTrainBus = Float.parseFloat("9999");
        }
        if(BusHaltNearEndTrain.toString().equals(ToBusHaltLatLng.toString())){
            Time_EndWalkFromTraintoTrainBus = Float.parseFloat("9999");
            Cost_EndWalkFromTraintoTrainBus = Float.parseFloat("9999");
        }


        float walk_distance6[] = new float[10];
        Location.distanceBetween(ToTrainHaltLatLng.latitude,ToTrainHaltLatLng.longitude,TaxiParkNearEndTrainStation.latitude,TaxiParkNearEndTrainStation.longitude,walk_distance6);
        Float DistanceToEndTrainStationToTaxiPark = walk_distance6[0];
        Time_EndWalkFromTraintoTaxi=((DistanceToEndTrainStationToTaxiPark/walkSpeed)/60);
        if(DistanceToEndTrainStationToTaxiPark>MainActivity.costDataCalculator.getTestData().getMaxWalkDistance()){
            Cost_EndWalkFromTraintoTaxi = Float.parseFloat("9999");
        }



        float walk_distance7[] = new float[10];
        Location.distanceBetween(ToBusHaltLatLng.latitude,ToBusHaltLatLng.longitude,TaxiParkNearEndBusHalt.latitude,TaxiParkNearEndBusHalt.longitude,walk_distance7);
        Float DistanceToEndBusHaltToTaxiPark = walk_distance7[0];
        Time_EndWalkFromBustoTaxi=((DistanceToEndBusHaltToTaxiPark/walkSpeed)/60);
        if(DistanceToEndBusHaltToTaxiPark>MainActivity.costDataCalculator.getTestData().getMaxWalkDistance()){
            Cost_EndWalkFromBustoTaxi = Float.parseFloat("9999");
        }



        Time_StartTaxitoEnd = Float.parseFloat(MainActivity.costDataCalculator.getTaxiTime().split(" ")[0])-Time_StartWalkToTaxi;

        Cost_StartTaxitoEnd = Float.parseFloat(MainActivity.costDataCalculator.getTaxiCost().split(" ")[0]);

        if(FromBusHaltLatLng.toString().equals(ToBusHaltLatLng.toString())){
            Cost_StartBustoEndBus = Float.parseFloat("9999");
            Time_StartBustoEndBus = Float.parseFloat("9999");
        }else if(MainActivity.costDataCalculator.getBusCost()!="-" && MainActivity.costDataCalculator.getBusTime()!="-"){
            Cost_StartBustoEndBus = Float.parseFloat(MainActivity.costDataCalculator.getBusCost().split(" ")[0]);
            Time_StartBustoEndBus = Float.parseFloat(MainActivity.costDataCalculator.getBusTime().split(" ")[0]);
        }else{
            Cost_StartBustoEndBus = Float.parseFloat("9999");
            Time_StartBustoEndBus = Float.parseFloat("9999");
        }
        if(FromTrainHaltLatLng.toString().equals(ToTrainHaltLatLng.toString())){
            Cost_StartTraintoEndTrain = Float.parseFloat("9999");
            Time_StartTraintoEndTrain = Float.parseFloat("9999");

        }else {
            Cost_StartTraintoEndTrain = Float.parseFloat(MainActivity.costDataCalculator.getTrainCost().split(" ")[0]);
            Time_StartTraintoEndTrain = Float.parseFloat(MainActivity.costDataCalculator.getTrainTime().split(" ")[0]) - Time_StartWalkToTrain - Time_EndWalkFromTraintoEnd + MainActivity.costDataCalculator.getTimeToNextTrain();
        }

        new AsyncTask1().execute(getDirectionUrl(TaxiParkLatLng,FromBusHaltLatLng));
        new AsyncTask2().execute(getDirectionUrl(TaxiParkLatLng,FromTrainHaltLatLng));
        new AsyncTask3().execute(getDirectionUrl(TaxiParkLatLng,To));
        new AsyncTask4().execute(getDirectionUrl(TaxiParkNearEndBusHalt,To));
        new AsyncTask5().execute(getDirectionUrl(TaxiParkNearEndTrainStation,BusHaltNearEndTrain));
        new AsyncTask6().execute(getDirectionUrl(BusHaltNearEndTrain,ToBusHaltLatLng));



    }

    private  void finishLoad(){
        Finished+=1;
        if(Finished==6){

            int[][] adjacencyMatrixCost = { { 0, Math.round(Cost_StartWalkToTaxi), Math.round(Cost_StartWalkToBus), Math.round(Cost_StartWalkToTrain), 0, 0, 0, 0, 0, 0 },
                                            { Math.round(Cost_StartWalkToTaxi), 0, Math.round(Cost_StartTaxitoBus), Math.round(Cost_StartTaxitoTrain), 0, 0, 0, 0,0, Math.round(Cost_StartTaxitoEnd) },
                                            { Math.round(Cost_StartWalkToBus), Math.round(Cost_StartTaxitoBus), 0, 0, Math.round(Cost_StartBustoEndBus), 0, 0, 0,0, 0 },
                                            { Math.round(Cost_StartWalkToTrain),  Math.round(Cost_StartTaxitoTrain), 0, 0, 0, Math.round(Cost_StartTraintoEndTrain), 0, 0,0, 0 },
                                            { 0, 0, Math.round(Cost_StartBustoEndBus), 0, 0, 0, 0, Math.round(Cost_EndWalkFromBustoTaxi),Math.round(Cost_EndTrainBustoEndBus), Math.round(Cost_EndWalkFromBustoEnd) },
                                            { 0, 0, 0, Math.round(Cost_StartTraintoEndTrain), 0, 0, Math.round(Cost_EndWalkFromTraintoTaxi), 0,Math.round(Cost_EndTrainTaxitoTrainBus), Math.round(Cost_EndWalkFromTraintoEnd) },
                                            { 0, 0, 0, 0, 0, Math.round(Cost_EndWalkFromTraintoTaxi), 0, 0,Math.round(Cost_EndWalkFromTraintoTrainBus), Math.round(Cost_EndTrainTaxitoEnd) },
                                            { 0, 0, 0, 0, Math.round(Cost_EndWalkFromBustoTaxi), 0, 0, 0,0, Math.round(Cost_EndBusTaxitoEnd) },
                                            { 0, 0, 0, 0, Math.round(Cost_EndTrainBustoEndBus) , Math.round(Cost_EndTrainTaxitoTrainBus) , Math.round(Cost_EndWalkFromTraintoTrainBus), 0,0 ,0 },
                                            { 0, Math.round(Cost_StartTaxitoEnd), 0, 0, Math.round(Cost_EndWalkFromBustoEnd), Math.round(Cost_EndWalkFromTraintoEnd), Math.round(Cost_EndTrainTaxitoEnd), Math.round(Cost_EndBusTaxitoEnd),0, 0 } };

            MatrixCost = adjacencyMatrixCost;

            Log.d("dijek", Arrays.deepToString(MatrixCost));

            int[][] adjacencyMatrixTime = { { 0, Math.round(Time_StartWalkToTaxi), Math.round(Time_StartWalkToBus), Math.round(Time_StartWalkToTrain), 0, 0, 0, 0, 0,0 },
                                            { Math.round(Time_StartWalkToTaxi), 0, Math.round(Time_StartTaxitoBus), Math.round(Time_StartTaxitoTrain), 0, 0, 0, 0,0, Math.round(Time_StartTaxitoEnd) },
                                            { Math.round(Time_StartWalkToBus), Math.round(Time_StartTaxitoBus), 0, 0, Math.round(Time_StartBustoEndBus), 0, 0, 0,0, 0 },
                                            { Math.round(Time_StartWalkToTrain),  Math.round(Time_StartTaxitoTrain), 0, 0, 0, Math.round(Time_StartTraintoEndTrain), 0, 0,0, 0 },
                                            { 0, 0, Math.round(Time_StartBustoEndBus), 0, 0, 0, 0, Math.round(Time_EndWalkFromBustoTaxi),Math.round(Time_EndTrainBustoEndBus), Math.round(Time_EndWalkFromBustoEnd) },
                                            { 0, 0, 0, Math.round(Time_StartTraintoEndTrain), 0, 0, Math.round(Time_EndWalkFromTraintoTaxi), 0,Math.round(Time_EndTrainTaxitoTrainBus), Math.round(Time_EndWalkFromTraintoEnd) },
                                            { 0, 0, 0, 0, 0, Math.round(Time_EndWalkFromTraintoTaxi), 0, 0,Math.round(Time_EndWalkFromTraintoTrainBus), Math.round(Time_EndTrainTaxitoEnd) },
                                            { 0, 0, 0, 0, Math.round(Time_EndWalkFromBustoTaxi), 0, 0, 0,0, Math.round(Time_EndBusTaxitoEnd) },
                                            { 0, 0, 0, 0, Math.round(Time_EndTrainBustoEndBus) , Math.round(Time_EndTrainTaxitoTrainBus) , Math.round(Time_EndWalkFromTraintoTrainBus), 0,0 ,0 },
                                            { 0, Math.round(Time_StartTaxitoEnd), 0, 0, Math.round(Time_EndWalkFromBustoEnd), Math.round(Time_EndWalkFromTraintoEnd), Math.round(Time_EndTrainTaxitoEnd), Math.round(Time_EndBusTaxitoEnd),0, 0 } };

            MatrixTime = adjacencyMatrixTime;

            Log.d("dijek", Arrays.deepToString(MatrixTime));

            Loaded=true;
            
        }
    }

    private  String getDirectionUrl(LatLng start, LatLng end){

        StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
        stringBuilder.append("origin="+Double.toString( start.latitude)+","+Double.toString(start.longitude));
        stringBuilder.append("&destination="+Double.toString(end.latitude)+","+Double.toString(end.longitude));
        stringBuilder.append("&key="+MainActivity.GOOGLE_MAPS_API_KEY);

        return stringBuilder.toString();

    }

    public  class AsyncTask1 extends AsyncTask<Object,String,String> {

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
            poly1=jsonDataParser.getPathsArray(s);

            String time = TempHash.get("duration");
            String distance = TempHash.get("distance");

            float KM = Math.round(Float.parseFloat(distance)/1000);
            if(KM>0) {
                int totalTime = Integer.parseInt(time);
                int totalMins = Math.round(totalTime / 60);
                Time_StartTaxitoBus = Float.parseFloat(Integer.toString(totalMins));


                Cost_StartTaxitoBus = Float.parseFloat(Integer.toString(Math.round(MainActivity.costDataCalculator.testData.getTaxiFare()[0] + (KM - 1) * MainActivity.costDataCalculator.testData.getTaxiFare()[1])));
            }else{
                Cost_StartTaxitoBus = Float.parseFloat("9999");
                Time_StartTaxitoBus = Float.parseFloat("9999");
            }
            finishLoad();
        }





    }
    public  class AsyncTask2 extends AsyncTask<Object,String,String> {

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
            poly2 =jsonDataParser.getPathsArray(s);

            String time = TempHash.get("duration");
            String distance = TempHash.get("distance");


            float KM = Math.round(Float.parseFloat(distance)/1000);

            if(KM>0) {

                int totalTime = Integer.parseInt(time);
                int totalMins = Math.round(totalTime / 60);
                Time_StartTaxitoTrain = Float.parseFloat(Integer.toString(totalMins));


                Cost_StartTaxitoTrain = Float.parseFloat(Integer.toString(Math.round(MainActivity.costDataCalculator.testData.getTaxiFare()[0] + (KM - 1) * MainActivity.costDataCalculator.testData.getTaxiFare()[1])));
            }else{
                Cost_StartTaxitoTrain = Float.parseFloat("9999");
                Time_StartTaxitoTrain = Float.parseFloat("9999");

            }
            finishLoad();

        }





    }
    public  class AsyncTask3 extends AsyncTask<Object,String,String> {

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
            poly3=jsonDataParser.getPathsArray(s);


            float KM = Math.round(Float.parseFloat(distance)/1000);
            if(KM>0) {
                int totalTime = Integer.parseInt(time);
                int totalMins = Math.round(totalTime / 60);
                Time_EndTrainTaxitoEnd = Float.parseFloat(Integer.toString(totalMins));


                Cost_EndTrainTaxitoEnd = Float.parseFloat(Integer.toString(Math.round(MainActivity.costDataCalculator.testData.getTaxiFare()[0] + (KM - 1) * MainActivity.costDataCalculator.testData.getTaxiFare()[1])));
            }else{
                Cost_EndTrainTaxitoEnd = Float.parseFloat("9999");
                Time_EndTrainTaxitoEnd = Float.parseFloat("9999");
            }
            finishLoad();



        }





    }
    public  class AsyncTask4 extends AsyncTask<Object,String,String> {

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
            poly4=jsonDataParser.getPathsArray(s);

            float KM = Math.round(Float.parseFloat(distance)/1000);

            if(KM>0) {
                int totalTime = Integer.parseInt(time);
                int totalMins = Math.round(totalTime / 60);
                Time_EndBusTaxitoEnd = Float.parseFloat(Integer.toString(totalMins));


                Cost_EndBusTaxitoEnd = Float.parseFloat(Integer.toString(Math.round(MainActivity.costDataCalculator.testData.getTaxiFare()[0] + (KM - 1) * MainActivity.costDataCalculator.testData.getTaxiFare()[1])));
            }else{
                Time_EndBusTaxitoEnd = Float.parseFloat("9999");
                Cost_EndBusTaxitoEnd = Float.parseFloat("9999");
            }finishLoad();



        }





    }
    public  class AsyncTask5 extends AsyncTask<Object,String,String> {

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
            poly5=jsonDataParser.getPathsArray(s);

            float KM = Math.round(Float.parseFloat(distance)/1000);

            float walk_distance[] = new float[10];
            Location.distanceBetween(TaxiParkNearEndTrainStation.latitude,TaxiParkNearEndTrainStation.longitude,BusHaltNearEndTrain.latitude,BusHaltNearEndTrain.longitude,walk_distance);
            Float Distance =  walk_distance[0];

            Log.d("fuckdist",Float.toString(Distance));

            if(KM>0 && Distance > MainActivity.costDataCalculator.testData.getMaxWalkDistance()) {
                int totalTime = Integer.parseInt(time);
                int totalMins = Math.round(totalTime / 60);

                Time_EndTrainTaxitoTrainBus = Float.parseFloat(Integer.toString(totalMins));


                Cost_EndTrainTaxitoTrainBus = Float.parseFloat(Integer.toString(Math.round(MainActivity.costDataCalculator.testData.getTaxiFare()[0] + (KM - 1) * MainActivity.costDataCalculator.testData.getTaxiFare()[1])));
            }else{
                Time_EndTrainTaxitoTrainBus = Float.parseFloat("9999");
                Cost_EndTrainTaxitoTrainBus = Float.parseFloat("9999");


            }
            finishLoad();



        }





    }
    public  class AsyncTask6 extends AsyncTask<Object,String,String> {

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
            Time_EndTrainBustoEndBus = Float.parseFloat(Integer.toString(totalMins));

            float KM = Math.round(Float.parseFloat(distance)/1000);

            Cost_EndTrainBustoEndBus = (KM*2);
            finishLoad();



        }





    }


}
