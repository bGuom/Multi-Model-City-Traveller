package com.app.smartcitytraveler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.smartcitytraveler.Adapter.StepAdapter;
import com.app.smartcitytraveler.MediumData.BusTripRouteHalts;
import com.app.smartcitytraveler.MediumData.SuperClasses.OptionData;
import com.app.smartcitytraveler.PathFinder.BusPathFinder;
import com.app.smartcitytraveler.PathFinder.Step;
import com.app.smartcitytraveler.PathFinder.TaxiPathFinder;
import com.app.smartcitytraveler.PathFinder.TrainPathFinder;
import com.app.smartcitytraveler.TestData.TestData;
import com.google.android.gms.maps.model.LatLng;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ActivityStepDetails extends AppCompatActivity {

    private ImageView ivlogo;
    private TextView tvcost,tvtime;
    private LinearLayout btstart,btback;

    public static OptionData optionData;
    public static ArrayList<BusTripRouteHalts> busTripRouteHaltsAray;

    public static ArrayList<Step> StepArray;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_layout);

        ProgressBar pbr = (ProgressBar)findViewById(R.id.pbr);
        ivlogo = (ImageView)findViewById(R.id.ivstepheadlogo);
        tvcost = (TextView)findViewById(R.id.tvstepheadcost);
        tvtime = (TextView)findViewById(R.id.tvstepheadtime);
        btstart = (LinearLayout)findViewById(R.id.btstart);
        btback = (LinearLayout)findViewById(R.id.btback);


        if(optionData.getStepMedium().equals("WK")){
            ivlogo.setImageResource(R.drawable.walk);
        }else if(optionData.getStepMedium().equals("TR")){
            ivlogo.setImageResource(R.drawable.train);
        }else if(optionData.getStepMedium().equals("BS")){
            ivlogo.setImageResource(R.drawable.bus);
        }else if(optionData.getStepMedium().equals("TX")){
            ivlogo.setImageResource(R.drawable.taxi);
        }else if(optionData.getStepMedium().equals("MM")){
            ivlogo.setImageResource(R.drawable.multi);
        }

        tvcost.setText(optionData.getCost());
        tvtime.setText(optionData.getTotalTime());

        if(optionData.getStepMedium().equals("TR")) {
            TrainPathFinder trainPathFinder = MainActivity.costDataCalculator.getTrainPathFinder();
            StepArray = trainPathFinder.getTrainTravelSteps(optionData);
        }else if (optionData.getStepMedium().equals("TX")){
            TaxiPathFinder taxiPathFinder = MainActivity.costDataCalculator.getTaxiPathFinder();
            StepArray = taxiPathFinder.getTaxiTravelSteps(optionData);
        }else if (optionData.getStepMedium().equals("BS")){
            BusPathFinder busPathFinder = MainActivity.costDataCalculator.getBusPathFinder();
            StepArray = busPathFinder.getBusTravelSteps(busTripRouteHaltsAray);
            ArrayList<LatLng> Destinations = new ArrayList<LatLng>();
            Destinations.add(MainActivity.costDataCalculator.getFrom());
            for(BusTripRouteHalts busTripRouteHalts : busTripRouteHaltsAray){
                Destinations.add(busTripRouteHalts.getGetInHalt().getLatLng());
                Destinations.add(busTripRouteHalts.getGetDownHalt().getLatLng());
            }
            Destinations.add(MainActivity.costDataCalculator.getTo());
            optionData.setDestinations(Destinations);
        }

        ((ListView)findViewById(R.id.steplist)).setAdapter(new StepAdapter(StepArray,ActivityStepDetails.this));
        pbr.setVisibility(View.GONE);

        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startTrip(optionData,StepArray);
            }
        });
    }


    private void startTrip(OptionData optionData, ArrayList<Step> stepArray){

        MapNavActivity.optionData=optionData;
        MapNavActivity.stepArray=stepArray;
        Intent i = new Intent(ActivityStepDetails.this, MapNavActivity.class);
        ActivityStepDetails.this.startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
