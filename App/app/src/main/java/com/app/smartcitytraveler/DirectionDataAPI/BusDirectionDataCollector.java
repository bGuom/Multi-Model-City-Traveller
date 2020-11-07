package com.app.smartcitytraveler.DirectionDataAPI;

import android.os.AsyncTask;
import android.util.Log;

import com.app.smartcitytraveler.MainActivity;
import com.app.smartcitytraveler.MediumData.Controllers.BusDataController;
import com.app.smartcitytraveler.Stations.BusHalt;
import com.app.smartcitytraveler.TestData.TestData;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

public class BusDirectionDataCollector  {


    private BusDataController busDataController = MainActivity.costDataCalculator.testData.getBusDataController();
    ArrayList<BusHalt> busHalts = busDataController.getBusHaltsinRoute("5");
    ArrayList<String> Distances = new ArrayList<String>();
    ArrayList<ArrayList<String>> Polys = new ArrayList<ArrayList<String>>();


    public void getData() {
        for (int i = 0; i < busHalts.size()-1; i++) {
            LatLng start = busHalts.get(i).getLatLng();
            LatLng end = busHalts.get(i+1).getLatLng();

            StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
            stringBuilder.append("origin="+Double.toString( start.latitude)+","+Double.toString(start.longitude));
            stringBuilder.append("&destination="+Double.toString(end.latitude)+","+Double.toString(end.longitude));
            stringBuilder.append("&key="+ MainActivity.GOOGLE_MAPS_API_KEY);



            new AsyncTaskx().execute(stringBuilder.toString(),Integer.toString(busHalts.get(i).getID()),Integer.toString(busHalts.get(i+1).getID()));

        }
    }

    private void addData(HashMap<String,String> dis,ArrayList<String> poly,String h1, String h2){
        Distances.add(dis.get("distance"));
        Polys.add(poly);


        //ArrayList<String> BusHaltDistanceArray = new ArrayList<String>();
        //BusHaltDistanceArray.add();
        //ArrayList<String> TempPolyLine = new ArrayList<String>();
        //TempPolyLine.add()

        String polystring = poly.toString();


        Log.d("zxcv",dis.get("distance") + " " + h1 + " to "+ h2);







    }

    public class AsyncTaskx extends AsyncTask<Object,String,String> {


        String Halt1;
        String Halt2;
        String URL;
        String GoogleDirectionData;

        //public GetAsyncDirectionsData(AsyncResponseHandleable parent){
        //    Parent = parent;
        //}

        @Override
        protected String doInBackground(Object... objects) {

            URL = (String)objects[0];
            Halt1 = (String)objects[1];
            Halt2 = (String)objects[2];


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
            TempPath = jsonDataParser.getPathsArray(s);

            addData(TempHash,TempPath,Halt1,Halt2);



        }





    }

}
