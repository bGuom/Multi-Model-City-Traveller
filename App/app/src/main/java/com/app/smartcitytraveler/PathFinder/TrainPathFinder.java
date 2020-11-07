package com.app.smartcitytraveler.PathFinder;

import android.location.Location;
import android.util.Log;

import com.app.smartcitytraveler.MediumData.Controllers.TrainDataController;
import com.app.smartcitytraveler.MediumData.SuperClasses.OptionData;
import com.app.smartcitytraveler.Stations.TrainStation;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;

public class TrainPathFinder {



    private TrainDataController TrainDataController;
    private static ArrayList<LatLng> StationLatLngArray = new ArrayList<LatLng>();

    public TrainPathFinder(TrainDataController trainDataController) {
        TrainDataController = trainDataController;
    }

    public void setTrainDataController(TrainDataController trainDataController) {
        TrainDataController = trainDataController;
    }

    public TrainStation getClosestTrainStaion(LatLng location){
        //Update LatLng Array of Train Staions
        if(StationLatLngArray.size()!= TrainDataController.getStationCount()) {
            for (TrainStation trainStation : TrainDataController.getTrainStations()) {
                StationLatLngArray.add(trainStation.getLatLng());
            }
        }

        ArrayList<Float> DistanceArray = new ArrayList<Float>();

        for(LatLng StationLatLng : StationLatLngArray){
            float[] f = new float[10];
            Location.distanceBetween(location.latitude,location.longitude,StationLatLng.latitude,StationLatLng.longitude,f);
            Log.d("asdfg",Float.toString(f[0]));
            DistanceArray.add(f[0]);
        }

        return TrainDataController.getTrainStationByID(DistanceArray.indexOf(Collections.min(DistanceArray)));



    }

    public ArrayList<Step> getTrainTravelSteps(OptionData optionData){

        ArrayList<Step> StepArray = new ArrayList<Step>();

        LatLng Start = optionData.getStartLatLng();
        LatLng End = optionData.getEndLatLng();
        LatLng FromStaion = optionData.getDestinationByID(1);
        LatLng ToStation = optionData.getDestinationByID(2);

        float start_walk_distance[] = new float[10];
        Location.distanceBetween(Start.latitude,Start.longitude,FromStaion.latitude,FromStaion.longitude,start_walk_distance);

        float end_walk_distance[] = new float[10];
        Location.distanceBetween(ToStation.latitude,ToStation.longitude,End.latitude,End.longitude,end_walk_distance);

        StepArray.add(new Step(1,"WK","Walk "+ Integer.toString(Math.round(Math.abs(start_walk_distance[0])))+"m to "+optionData.getStart(),"Get in to the train " +optionData.getNo() + " at "+ optionData.getStartTime(),Start,FromStaion,optionData.getEndTime()));
        StepArray.add(new Step(2,"TR","Get down at "+optionData.getEnd(),"You will reach at " + optionData.getEndTime(),FromStaion,ToStation,optionData.getEndTime()));
        StepArray.add(new Step(3,"WK","Walk " + Integer.toString(Math.round(Math.abs(end_walk_distance[0])))+" m to your destination","Reach your destination",ToStation,End,""));

        return StepArray;

    }
}
