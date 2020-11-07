package com.app.smartcitytraveler.PathFinder;

import android.location.Location;
import android.util.Log;

import com.app.smartcitytraveler.MediumData.SuperClasses.OptionData;
import com.app.smartcitytraveler.Stations.TaxiPark;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;

public class TaxiPathFinder {



    private static ArrayList<LatLng> ParksLatLngArray = new ArrayList<LatLng>();
    private ArrayList<TaxiPark> TaxiParks = new ArrayList<TaxiPark>();

    public TaxiPathFinder(ArrayList<TaxiPark> taxiParks) {
        TaxiParks = taxiParks;
    }



    public TaxiPark getClosestTaxiPark(LatLng location){
        //Update LatLng Array of Taxi Parks
        if(ParksLatLngArray.size()!= TaxiParks.size()) {
            for (TaxiPark taxiPark : TaxiParks) {
                ParksLatLngArray.add(taxiPark.getLatLng());
            }
        }

        ArrayList<Float> DistanceArray = new ArrayList<Float>();

        for(LatLng ParkLatLng : ParksLatLngArray){
            /**
            float X = (float)(Location.latitude-ParkLatLng.latitude);

            float Y = (float) (Location.longitude-ParkLatLng.longitude);
            Float Distance = ((X*X)+(Y*Y));
            DistanceArray.add(Distance);
             **/

            float[] f = new float[10];
            Location.distanceBetween(location.latitude,location.longitude,ParkLatLng.latitude,ParkLatLng.longitude,f);
            Log.d("asdfg",Float.toString(f[0]));
            DistanceArray.add(f[0]);
        }

        return TaxiParks.get(DistanceArray.indexOf(Collections.min(DistanceArray)));



    }

    public ArrayList<Step> getTaxiTravelSteps (OptionData optionData){

        ArrayList<Step> StepArray = new ArrayList<Step>();

        LatLng Start = optionData.getStartLatLng();
        LatLng End = optionData.getEndLatLng();
        LatLng Park = optionData.getDestinationByID(1);



        float walk_distance[] = new float[10];
        Location.distanceBetween(Start.latitude,Start.longitude,Park.latitude,Park.longitude,walk_distance);

        StepArray.add(new Step(1,"WK","Walk "+ Integer.toString(Math.round(Math.abs(walk_distance[0])))+"m to Taxi park","Get in to a Taxi ",Start,Park,optionData.getStartTime()));
        StepArray.add(new Step(2,"TX","Travel by Taxi","Pay " + optionData.getCost() +" at your destination",Park,End,optionData.getEndTime()));

        return StepArray;

    }
}
