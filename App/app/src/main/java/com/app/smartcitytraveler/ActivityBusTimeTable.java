package com.app.smartcitytraveler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.smartcitytraveler.Adapter.StepAdapter;
import com.app.smartcitytraveler.MediumData.BusData;
import com.app.smartcitytraveler.MediumData.BusTripRouteHalts;
import com.app.smartcitytraveler.MediumData.SuperClasses.OptionData;
import com.app.smartcitytraveler.PathFinder.Step;
import com.app.smartcitytraveler.PathFinder.TaxiPathFinder;
import com.app.smartcitytraveler.PathFinder.TrainPathFinder;
import com.app.smartcitytraveler.TestData.TestData;

import java.util.ArrayList;
import java.util.Collections;

public class ActivityBusTimeTable extends AppCompatActivity {

    private ImageView ivlogo;
    private TextView tvcost,tvtime;
    private LinearLayout btstart,btback;

    public static ArrayList<BusTripRouteHalts> optionData;

    private ArrayList<BusData> BusTimeTable;



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



        ivlogo.setImageResource(R.drawable.bus);

        int cost =0;
        Float time = Float.parseFloat("0");
        for(BusTripRouteHalts busTripRouteHalts: optionData ){
            cost+=busTripRouteHalts.getCost();
            time+=busTripRouteHalts.getTime();

        }

        String BusCost = Integer.toString(cost) + " RS" ;
        String BusTime = Integer.toString(Math.round(time)) + " Min";

        tvcost.setText(BusCost);
        tvtime.setText(BusTime);

        //if(optionData.get())
        BusTimeTable = MainActivity.costDataCalculator.getTestData().getDownBusData();




        //((ListView)findViewById(R.id.steplist)).setAdapter(new StepAdapter(StepArray, ActivityBusTimeTable.this));
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

              //  startTrip(optionData,StepArray);
            }
        });
    }


    private void startTrip(OptionData optionData, ArrayList<Step> stepArray){

        MapNavActivity.optionData=optionData;
        MapNavActivity.stepArray=stepArray;
        Intent i = new Intent(ActivityBusTimeTable.this, MapNavActivity.class);
        ActivityBusTimeTable.this.startActivity(i);
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
