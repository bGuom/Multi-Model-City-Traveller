package com.app.smartcitytraveler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.smartcitytraveler.Adapter.OptionAdapter;
import com.app.smartcitytraveler.MediumData.SuperClasses.OptionData;
import com.app.smartcitytraveler.MediumData.Controllers.TrainDataController;
import com.app.smartcitytraveler.PathFinder.TrainPathFinder;
import com.app.smartcitytraveler.Stations.TrainStation;
import com.app.smartcitytraveler.TestData.TestData;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class ActivityOptionDetails extends AppCompatActivity {


    private ImageView btback;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_layout);

        ProgressBar pbr = (ProgressBar)findViewById(R.id.pbr);
        btback =(ImageView)findViewById(R.id.backbutton);
        if(MainActivity.costDataCalculator.getTimeToNextTrain() != Float.parseFloat("9999")) {
            ((TextView) findViewById(R.id.title)).setText("Next train in " + Integer.toString(Math.round(MainActivity.costDataCalculator.getTimeToNextTrain())) + " Mins");
        }else{
            ((TextView) findViewById(R.id.title)).setText("No train available");
        }

        Intent i = getIntent();
        LatLng FromLatLng = (LatLng) i.getParcelableExtra("From");
        LatLng ToLatLng = (LatLng) i.getParcelableExtra("To");

        TrainDataController trainDataController = MainActivity.costDataCalculator.getTestData().getTestTrainData();

        TrainStation FromStation = MainActivity.costDataCalculator.getFromStation();
        TrainStation EndStation = MainActivity.costDataCalculator.getEndStation();

        Log.d("OPTTEST",  Integer.toString(FromStation.getStationID())+" to "+Integer.toString(EndStation.getStationID()));
        ArrayList<OptionData> tripoptions = trainDataController.getOptionDataForTrip(FromLatLng,ToLatLng,FromStation.getStationID(),EndStation.getStationID());

        pbr.setVisibility(View.GONE);
        if(tripoptions.size()>0) {
            ((ListView) findViewById(R.id.optionlist)).setAdapter(new OptionAdapter(tripoptions, ActivityOptionDetails.this));

        }else{
            ((RelativeLayout)findViewById(R.id.viewnodata)).setVisibility(View.VISIBLE);
        }

        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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
