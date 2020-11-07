package com.app.smartcitytraveler.PathFinder;

import android.location.Location;
import android.util.Log;

import com.app.smartcitytraveler.MainActivity;
import com.app.smartcitytraveler.MediumData.BusTripRouteHalts;
import com.app.smartcitytraveler.MediumData.Controllers.BusDataController;
import com.app.smartcitytraveler.MediumData.Controllers.TrainDataController;
import com.app.smartcitytraveler.MediumData.SuperClasses.OptionData;
import com.app.smartcitytraveler.Stations.BusHalt;
import com.app.smartcitytraveler.Stations.TrainStation;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;

public class BusPathFinder {



    private BusDataController busDataController;

    public BusPathFinder(BusDataController dataController) {
        busDataController = dataController;
    }

    public String getBestRoute(LatLng from,LatLng to){

        ArrayList<Float> DistanceArray = new ArrayList<Float>();

        ArrayList<String> busRouts = busDataController.getBusRoutes();

        for(String route : busRouts){

            ArrayList<Float> inRouteDistanceArrayFrom = new ArrayList<Float>();
            ArrayList<Float> inRouteDistanceArrayTo = new ArrayList<Float>();

            ArrayList<BusHalt> busHalts = busDataController.getBusHaltsinRoute(route);

            for(BusHalt halt : busHalts){
                float[] f = new float[10];
                Location.distanceBetween(from.latitude,from.longitude,halt.getLatLng().latitude,halt.getLatLng().longitude,f);
                float[] f2 = new float[10];
                Location.distanceBetween(from.latitude,from.longitude,halt.getLatLng().latitude,halt.getLatLng().longitude,f2);
                inRouteDistanceArrayFrom.add(f[0]);
                inRouteDistanceArrayFrom.add(f2[0]);
            }

            DistanceArray.add(Collections.min(inRouteDistanceArrayFrom)+Collections.min(inRouteDistanceArrayFrom));
        }
        float minDis = Collections.min(DistanceArray);
        return busRouts.get(DistanceArray.indexOf(minDis));

    }

    public BusHalt getClosestBusStationInRoute(LatLng location, String route){
        //Update LatLng Array of BusHalts
        ArrayList<LatLng> TempHaltLatLngArray = new ArrayList<LatLng>();
        ArrayList<BusHalt> routeBusHalts = busDataController.getBusHaltsinRoute(route);

        if(TempHaltLatLngArray.size()!= routeBusHalts.size()) {
            for (BusHalt busHalt : routeBusHalts) {
                TempHaltLatLngArray.add(busHalt.getLatLng());
            }
        }

        ArrayList<Float> DistanceArray = new ArrayList<Float>();

        for(LatLng HaltLatLng : TempHaltLatLngArray){
            float[] f = new float[10];
            Location.distanceBetween(location.latitude,location.longitude,HaltLatLng.latitude,HaltLatLng.longitude,f);
            Log.d("asdfg",Float.toString(f[0]));
            DistanceArray.add(f[0]);
        }

        float minDis = Collections.min(DistanceArray);

        return routeBusHalts.get(DistanceArray.indexOf(minDis));

    }

    public BusHalt getClosestBusStation(LatLng location){

        //Update LatLng Array of BusHalts
        ArrayList<LatLng> TempHaltLatLngArray = new ArrayList<LatLng>();
        ArrayList<BusHalt> BusHalts = busDataController.getBusHalts();

        if(TempHaltLatLngArray.size()!= BusHalts.size()) {
            for (BusHalt busHalt : BusHalts) {
                TempHaltLatLngArray.add(busHalt.getLatLng());
            }
        }

        ArrayList<Float> DistanceArray = new ArrayList<Float>();

        for(LatLng HaltLatLng : TempHaltLatLngArray){
            float[] f = new float[10];
            Location.distanceBetween(location.latitude,location.longitude,HaltLatLng.latitude,HaltLatLng.longitude,f);
            Log.d("asdfg",Float.toString(f[0]));
            DistanceArray.add(f[0]);
        }

        float minDis = Collections.min(DistanceArray);

        return BusHalts.get(DistanceArray.indexOf(minDis));

    }

    public ArrayList<BusHalt> getCrossHalts(String route1, String route2){

        ArrayList<BusHalt> CrossHalts = new ArrayList<BusHalt>();

        ArrayList<BusHalt> route1Halts = busDataController.getBusHaltsinRoute(route1);
        ArrayList<BusHalt> route2Halts = busDataController.getBusHaltsinRoute(route2);


        for (BusHalt halt1 : route1Halts) {
            for (BusHalt halt2 : route2Halts) {
                if (halt1.getID()==halt2.getID()){
                    CrossHalts.add(halt1);
                    CrossHalts.add(halt2);
                    break;
                }
            }
        }
        return  CrossHalts;

        /**


        ArrayList<Float> DistanceArray1 = new ArrayList<Float>();

        int x;
        ArrayList<Integer> y = new ArrayList<Integer>();
        for (BusHalt halt1 : route1Halts){
            ArrayList<Float> DistanceArray2 = new ArrayList<Float>();
            for(BusHalt halt2 : route2Halts){

                float[] f = new float[10];
                Location.distanceBetween(halt1.getLatLng().latitude,halt1.getLatLng().longitude,halt2.getLatLng().latitude,halt2.getLatLng().longitude,f);
                DistanceArray2.add(f[0]);

            }
            float minDis = Collections.min(DistanceArray2);
            DistanceArray1.add(minDis);
            y.add(DistanceArray2.indexOf(minDis));

        }
        float minDis = Collections.min(DistanceArray1);
        x=DistanceArray1.indexOf(minDis);

        CrossHalts[0] = route1Halts.get(x);
        CrossHalts[1] = route2Halts.get(y.get(x));

        return CrossHalts;
         **/
    }

    public ArrayList<String> getAllRoutesForHalt(int busHaltID){

        ArrayList<String> TempRouteArray = new ArrayList<String>();

        for(BusHalt busHalt : busDataController.getBusHalts()){
            if(busHalt.getID()==busHaltID){
                TempRouteArray.add(busHalt.getRouteID());
            }
        }

        return TempRouteArray;
    }

    public ArrayList<ArrayList<BusTripRouteHalts>> getRouteOptionsForTrip(LatLng Start, LatLng End){
        ArrayList<OptionData> TempArray = new ArrayList<OptionData>();
        BusPathFinder busPathFinder = this;

        BusHalt FromHalt = getClosestBusStation(Start);
        BusHalt ToHalt = MainActivity.costDataCalculator.getNearestBusHaltTo();

        ArrayList<ArrayList<BusTripRouteHalts>> arrayOfArraysOfBusTripRouteHalt = new ArrayList<ArrayList<BusTripRouteHalts>>();

        if(FromHalt.getID()!=ToHalt.getID()) {


            ArrayList<String> allRoutesFrom = busPathFinder.getAllRoutesForHalt(FromHalt.getID());
            ArrayList<String> allRoutesTo = busPathFinder.getAllRoutesForHalt(ToHalt.getID());
            Log.d("rtyu",allRoutesFrom.toString());
            Log.d("rtyu",allRoutesTo.toString());


            for (String fromRoute : allRoutesFrom) {
                for (String toRoute : allRoutesTo) {
                    ArrayList<BusTripRouteHalts> busTripRouteHaltsArray = new ArrayList<BusTripRouteHalts>();
                    if(fromRoute.equals(toRoute)){
                        busTripRouteHaltsArray.add(new BusTripRouteHalts(fromRoute,FromHalt,ToHalt));
                        Log.d("rtyu","same");
                    }else {
                        Log.d("rtyu","else");
                        Log.d("rtyu",fromRoute);
                        Log.d("rtyu",toRoute);
                        ArrayList<BusHalt> crossHalts = busPathFinder.getCrossHalts(fromRoute, toRoute);
                        Log.d("rtyu",Integer.toString(crossHalts.size()));
                        if (crossHalts.size() == 2) {
                            if (FromHalt.getID() != crossHalts.get(0).getID() && ToHalt.getID() != crossHalts.get(1).getID()) {
                                busTripRouteHaltsArray.add(new BusTripRouteHalts(fromRoute, FromHalt, crossHalts.get(0)));
                                busTripRouteHaltsArray.add(new BusTripRouteHalts(toRoute, crossHalts.get(1), ToHalt));
                                Log.d("rtyu","added");
                            }
                        }
                    }
                    if(busTripRouteHaltsArray.size()>0){
                        Log.d("rtyu","options : " + Integer.toString(busTripRouteHaltsArray.size()));
                        arrayOfArraysOfBusTripRouteHalt.add(busTripRouteHaltsArray);
                    }
                }


            }
            Log.d("rtyu","Total : " + Integer.toString(arrayOfArraysOfBusTripRouteHalt.size()));

        }
        return arrayOfArraysOfBusTripRouteHalt;
    }


    public ArrayList<Step> getBusTravelSteps(ArrayList<BusTripRouteHalts> busTripRouteHaltsArray){

        ArrayList<Step> StepArray = new ArrayList<Step>();

        LatLng Start = MainActivity.costDataCalculator.getFrom();
        LatLng End = MainActivity.costDataCalculator.getTo();

        BusHalt FromStaion = busTripRouteHaltsArray.get(0).getGetInHalt();
        BusHalt ToStation = busTripRouteHaltsArray.get(busTripRouteHaltsArray.size()-1).getGetDownHalt();
        BusHalt MidStation1 = busTripRouteHaltsArray.get(0).getGetDownHalt();
        BusHalt MidStation2 = busTripRouteHaltsArray.get(busTripRouteHaltsArray.size()-1).getGetInHalt();


        float start_walk_distance[] = new float[10];
        Location.distanceBetween(Start.latitude,Start.longitude,FromStaion.getLatLng().latitude,FromStaion.getLatLng().longitude,start_walk_distance);

        float end_walk_distance[] = new float[10];
        Location.distanceBetween(ToStation.getLatLng().latitude,ToStation.getLatLng().longitude,End.latitude,End.longitude,end_walk_distance);

        float mid_walk_distance[] = new float[10];
        Location.distanceBetween(MidStation1.getLatLng().latitude,MidStation1.getLatLng().longitude,MidStation2.getLatLng().latitude,MidStation2.getLatLng().longitude,mid_walk_distance);


        if(busTripRouteHaltsArray.size()==1){

            StepArray.add(new Step(1,"WK","Walk "+ Float.toString(Math.abs(start_walk_distance[0]))+"m to "+FromStaion.getHaltName() + " bus halt","Get in to a bus in route no " + FromStaion.getRouteID() ,Start,FromStaion.getLatLng(),""));
            StepArray.add(new Step(2,"BS","Buy tickets to "+ToStation.getHaltName()," Pay " + busTripRouteHaltsArray.get(0).getCost() + " RS",FromStaion.getLatLng(),ToStation.getLatLng(),""));
            StepArray.add(new Step(3,"WK","Get down at " + ToStation.getHaltName() + " bus halt","Walk "+ Float.toString(Math.abs(end_walk_distance[0]))+" m to your destination",ToStation.getLatLng(),End,""));

        }


        if(busTripRouteHaltsArray.size()==2){

            StepArray.add(new Step(1,"WK","Walk "+ Float.toString(Math.abs(start_walk_distance[0]))+"m to "+FromStaion.getHaltName() + " bus halt","Get in to the bus in route no " + FromStaion.getRouteID() ,Start,FromStaion.getLatLng(),""));
            StepArray.add(new Step(2,"BS","Buy tickets to "+MidStation1.getHaltName()," Pay " + busTripRouteHaltsArray.get(0).getCost() + " RS",FromStaion.getLatLng(),MidStation1.getLatLng(),""));
            StepArray.add(new Step(3,"WK","Get down at " + MidStation1.getHaltName() + " bus halt","Walk "+ Float.toString(Math.abs(mid_walk_distance[0]))+" m to " + MidStation2.getHaltName() + " bus halt",MidStation1.getLatLng(),MidStation2.getLatLng(),""));
            StepArray.add(new Step(4,"BS","Get in to the bus in route no " + MidStation2.getRouteID(),"Buy tickets to "+ToStation.getHaltName() + " & Pay " + busTripRouteHaltsArray.get(1).getCost() + " RS",MidStation2.getLatLng(),ToStation.getLatLng(),""));
            StepArray.add(new Step(5,"WK","Get down at " + ToStation.getHaltName() + " bus halt","Walk "+ Float.toString(Math.abs(end_walk_distance[0]))+" m to your destination",ToStation.getLatLng(),End,""));

        }


        return StepArray;

    }

}
