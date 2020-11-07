package com.app.smartcitytraveler.MediumData;

import android.util.Log;

import com.app.smartcitytraveler.DirectionDataAPI.CostDataCalculator;
import com.app.smartcitytraveler.MainActivity;
import com.app.smartcitytraveler.Stations.BusHalt;

import java.util.ArrayList;

public class BusTripRouteHalts {
    private String RouteID;
    private BusHalt GetInHalt;
    private BusHalt GetDownHalt;

    public BusTripRouteHalts(String routeID, BusHalt getInHalt, BusHalt getDownHalt) {
        RouteID = routeID;
        GetInHalt = getInHalt;
        GetDownHalt = getDownHalt;
    }

    public String getRouteID() {
        return RouteID;
    }

    public BusHalt getGetInHalt() {
        return GetInHalt;
    }

    public BusHalt getGetDownHalt() {
        return GetDownHalt;
    }

    public int getCost(){
        int Dif = Math.abs(GetDownHalt.getOrder()-GetInHalt.getOrder());
        return MainActivity.costDataCalculator.testData.getBusFareArray().get(Dif);

    }

    public Float getTime(){

        ArrayList<BusHalt> busHalts = MainActivity.costDataCalculator.busDataController.getBusHaltsinRoute(RouteID);
        int start = 0;
        int end = 0;
        int in = 0;
        int down = 0;
        for (int i = 0; i < busHalts.size() ; i++) {
            if(busHalts.get(i).getID()==GetInHalt.getID()){
                in=i;
            }
            if(busHalts.get(i).getID()==GetDownHalt.getID()){
                down=i;
            }
        }

        if(in<down){
            start= in;
            end=down;
        }else if(in>down){
            end= in;
            start=down;
        }
        int TotalDis =0;
        for (int i = start; i <end ; i++) {
            TotalDis+= Integer.parseInt(busHalts.get(i).getDistance());
            Log.d("fghj",GetInHalt.getHaltName()+" "+GetDownHalt.getHaltName()+ Integer.toString(TotalDis));
        }

        Float time = (TotalDis/Float.parseFloat("4.16667"))/60;
        Log.d("fghj",Float.toString(time));
        return time;

    }


}
