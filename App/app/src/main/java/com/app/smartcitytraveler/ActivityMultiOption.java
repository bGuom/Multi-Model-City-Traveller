package com.app.smartcitytraveler;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.smartcitytraveler.MediumData.BusTripRouteHalts;
import com.app.smartcitytraveler.MediumData.OptionClasses.BusOptionData;
import com.app.smartcitytraveler.MediumData.OptionClasses.MultiModelOptionData;
import com.app.smartcitytraveler.MediumData.SuperClasses.OptionData;
import com.app.smartcitytraveler.PathFinder.ShortestPathFinder;
import com.app.smartcitytraveler.PathFinder.Step;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;


public class ActivityMultiOption extends AppCompatActivity {



    private ImageView btback;
    private TextView time,cost;

    private CardView cdCheap,cdQuick;

    private String CheapString,QuickString;

    private String[] CheapArray , QuickArray;

    private ArrayList<Step> GraphStepArray = new ArrayList<Step>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multioption);

        btback = (ImageView) findViewById(R.id.menubutton);
        time = (TextView) findViewById(R.id.time);
        cost=(TextView)findViewById(R.id.cost);

        cdCheap = (CardView)findViewById(R.id.cdcheap);
        cdQuick = (CardView)findViewById(R.id.cdquick);

        fillStepArray();

        if(MainActivity.costDataCalculator.bestMethodFinder.Loaded){
            ShortestPathFinder shortestPathFinder = new ShortestPathFinder();
            CheapString = shortestPathFinder.findShortestPath(MainActivity.costDataCalculator.bestMethodFinder.MatrixCost);
            QuickString = shortestPathFinder.findShortestPath(MainActivity.costDataCalculator.bestMethodFinder.MatrixTime);

            cost.setText( Integer.toString(Math.round(Integer.parseInt(CheapString.split(" ")[0])/10)*10) + " RS");
            time.setText(QuickString.split(" ")[0] + " Min");

            CheapArray = CheapString.split(" ");
            QuickArray = QuickString.split(" ");

        }else{

            this.finish();
        }




        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        cdCheap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityStepDetails.StepArray = getStepArray(CheapArray);

                ArrayList<LatLng> Destinations = new ArrayList<LatLng>();

                for(Step step : ActivityStepDetails.StepArray){
                    Destinations.add(step.getStartLatLng());
                }
                Destinations.add(MainActivity.costDataCalculator.To);


                MultiModelOptionData option = new MultiModelOptionData(Destinations);
                ActivityStepDetails.optionData=option;

                Intent i = new Intent(ActivityMultiOption.this, ActivityStepDetails.class);
                ActivityMultiOption.this.startActivity(i);

            }
        });

        cdQuick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityStepDetails.StepArray = getStepArray(QuickArray);

                ArrayList<LatLng> Destinations = new ArrayList<LatLng>();

                for(Step step : ActivityStepDetails.StepArray){
                    Destinations.add(step.getStartLatLng());
                }
                Destinations.add(MainActivity.costDataCalculator.To);

                MultiModelOptionData option = new MultiModelOptionData(Destinations);
                ActivityStepDetails.optionData=option;

                Intent i = new Intent(ActivityMultiOption.this, ActivityStepDetails.class);
                ActivityMultiOption.this.startActivity(i);

            }
        });


    }


    private void fillStepArray(){

        GraphStepArray.add(new Step(1,"TX","Taxi Park","Take a Taxi from here",MainActivity.costDataCalculator.getNearestTaxiPark().getLatLng(),null,""));
        GraphStepArray.add(new Step(2,"BS","Bus Halt","Get into a Bus",MainActivity.costDataCalculator.getNearestBusHaltFrom().getLatLng(),MainActivity.costDataCalculator.getNearestBusHaltTo().getLatLng(),""));
        GraphStepArray.add(new Step(3,"TR", MainActivity.costDataCalculator.getFromStation().getName()+ " Train Station","Get into a Train",MainActivity.costDataCalculator.getFromStation().getLatLng(),MainActivity.costDataCalculator.getEndStation().getLatLng(),""));
        GraphStepArray.add(new Step(4,"BS","Bus Halt","Get down from Bus",MainActivity.costDataCalculator.getNearestBusHaltTo().getLatLng(),null,""));
        GraphStepArray.add(new Step(5,"TR", MainActivity.costDataCalculator.getEndStation().getName()+ " Train Station","Get down from the Train",MainActivity.costDataCalculator.getEndStation().getLatLng(),null,""));
        GraphStepArray.add(new Step(6,"TX","Taxi Park","Take a Taxi from here",MainActivity.costDataCalculator.getNearestTaxiParkToEndTrainStaion().getLatLng(),null,""));
        GraphStepArray.add(new Step(7,"TX","Taxi Park","Take a Taxi from here",MainActivity.costDataCalculator.getNearestTaxiParkToEndBusHalt().getLatLng(),MainActivity.costDataCalculator.To,""));
        GraphStepArray.add(new Step(8,"BS","Bus Halt","Get into a Bus",MainActivity.costDataCalculator.bestMethodFinder.BusHaltNearEndTrain,MainActivity.costDataCalculator.getNearestBusHaltTo().getLatLng(),""));

    }

    private ArrayList<Step> getStepArray(String[] arr){

        boolean busFound =false;

        ArrayList<Step> stepArr = new ArrayList<Step>();


        stepArr.add(new Step(1,"WK","Walk ","",MainActivity.costDataCalculator.From,null,""));

        ArrayList<ArrayList<String>> TempPolyArr = new ArrayList<ArrayList<String>>();

        for(int i=2;i<arr.length-1;i++){
            int node = Integer.parseInt(arr[i])-1;
            Step step = GraphStepArray.get(node);

            switch (step.getStepNum()){
                case 1:
                    if(arr[i+1].equals("3")){
                        step.setEndLatLng(MainActivity.costDataCalculator.getFromStation().getLatLng());
                    }else if(arr[i+1].equals("9")){
                        step.setEndLatLng(MainActivity.costDataCalculator.To);
                    }
                    break;

                case 4:
                    if(arr[i+1].equals("7")){
                        step.setEndLatLng(MainActivity.costDataCalculator.getNearestTaxiParkToEndBusHalt().getLatLng());
                    }else if(arr[i+1].equals("9")){
                        step.setEndLatLng(MainActivity.costDataCalculator.To);
                    }
                    break;
                case 6:
                    if(arr[i+1].equals("8")){
                        step.setEndLatLng(MainActivity.costDataCalculator.bestMethodFinder.TaxiParkNearEndTrainStation);
                    }else if(arr[i+1].equals("9")){
                        step.setEndLatLng(MainActivity.costDataCalculator.To);
                    }
                    break;
            }


            step.setStepNum(i+1);

            stepArr.add(step);





            if(arr[i].equals("2") && arr[i-1].equals("1")){
                TempPolyArr.add(MainActivity.costDataCalculator.bestMethodFinder.poly1);
            }
            if(arr[i].equals("3") && arr[i-1].equals("1")){
                TempPolyArr.add(MainActivity.costDataCalculator.bestMethodFinder.poly2);
            }
            if(arr[i].equals("9") && arr[i-1].equals("6")){
                TempPolyArr.add(MainActivity.costDataCalculator.bestMethodFinder.poly3);
            }
            if(arr[i].equals("9") && arr[i-1].equals("7")){
                TempPolyArr.add(MainActivity.costDataCalculator.bestMethodFinder.poly4);
            }
            if(arr[i].equals("8") && arr[i-1].equals("6")){
                TempPolyArr.add(MainActivity.costDataCalculator.bestMethodFinder.poly5);
            }


            if(!busFound && step.getStepMedium().equals("BS")){
                busFound=true;

                LatLng Start = step.getStartLatLng();
                LatLng End = step.getEndLatLng();

                 ArrayList<ArrayList<BusTripRouteHalts>> arrayOfArraysOfBusTripRouteHalt;
                arrayOfArraysOfBusTripRouteHalt = MainActivity.costDataCalculator.getBusPathFinder().getRouteOptionsForTrip(Start,End);

                ArrayList<Integer> BusCostArray = new ArrayList<Integer>();


                for (ArrayList<BusTripRouteHalts> busTripRouteHaltsArray: arrayOfArraysOfBusTripRouteHalt) {

                    int cost =0;
                    for(BusTripRouteHalts busTripRouteHalts: busTripRouteHaltsArray ){
                        cost+=busTripRouteHalts.getCost();
                    }


                    BusCostArray.add(cost);

                }


                int minBusCost = (Collections.min(BusCostArray));

                ArrayList<BusTripRouteHalts> busTripRouteHaltsArray = arrayOfArraysOfBusTripRouteHalt.get(BusCostArray.indexOf(minBusCost));

                ArrayList<Step> busStepArr = MainActivity.costDataCalculator.getBusPathFinder().getBusTravelSteps(busTripRouteHaltsArray);

                for(int k = 1;k<busStepArr.size()-1;k++){
                    stepArr.add(busStepArr.get(k));
                }
            }


        }

        if(stepArr.size()==2 && stepArr.get(1).getStepMedium().equals("TX")){
            TempPolyArr.add(MainActivity.costDataCalculator.getTaxiPolyPaths());
        }

        MapNavActivity.TaxiPolyArray = TempPolyArr;

        Step tempstep = stepArr.get(0);
        tempstep.setStepName("Walk towards " + stepArr.get(1).getStepName());
        tempstep.setEndLatLng(stepArr.get(1).getStartLatLng());
        stepArr.set(0,tempstep);

        tempstep = stepArr.get(stepArr.size()-1);
        tempstep.setStepName(tempstep.getStepDetails());
        tempstep.setStepDetails("Reach your Destination ");
        stepArr.set(stepArr.size()-1,tempstep);








        return stepArr;

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



}
