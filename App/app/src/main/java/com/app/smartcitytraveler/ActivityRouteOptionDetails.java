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
import com.app.smartcitytraveler.Adapter.RouteOptionAdapter;
import com.app.smartcitytraveler.DirectionDataAPI.CostDataCalculator;
import com.app.smartcitytraveler.MediumData.Controllers.TrainDataController;
import com.app.smartcitytraveler.MediumData.SuperClasses.OptionData;
import com.app.smartcitytraveler.Stations.TrainStation;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class ActivityRouteOptionDetails extends AppCompatActivity {


    private ImageView btback;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_layout);

        ProgressBar pbr = (ProgressBar)findViewById(R.id.pbr);
        btback =(ImageView)findViewById(R.id.backbutton);
        ((TextView)findViewById(R.id.title)).setText("Routes");

        pbr.setVisibility(View.GONE);
        if(MainActivity.costDataCalculator.getArrayOfArraysOfBusTripRouteHalt().size()>0) {
            ((ListView) findViewById(R.id.optionlist)).setAdapter(new RouteOptionAdapter(MainActivity.costDataCalculator.getArrayOfArraysOfBusTripRouteHalt(), ActivityRouteOptionDetails.this));

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
