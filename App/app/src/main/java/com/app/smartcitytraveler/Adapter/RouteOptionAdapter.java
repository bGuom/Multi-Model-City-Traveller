package com.app.smartcitytraveler.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.smartcitytraveler.ActivityStepDetails;
import com.app.smartcitytraveler.DirectionDataAPI.CostDataCalculator;
import com.app.smartcitytraveler.MainActivity;
import com.app.smartcitytraveler.MediumData.BusTripRouteHalts;
import com.app.smartcitytraveler.MediumData.OptionClasses.BusOptionData;
import com.app.smartcitytraveler.MediumData.SuperClasses.OptionData;
import com.app.smartcitytraveler.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;

public class RouteOptionAdapter extends BaseAdapter {


    private ArrayList<ArrayList<BusTripRouteHalts>> OptionArray;
    private LayoutInflater inflater;
    Activity activity;
    private String BusCost;
    private String BusTime;


    public RouteOptionAdapter( ArrayList<ArrayList<BusTripRouteHalts>> optionArray, Activity activity) {
        OptionArray = optionArray;
        this.activity = activity;
    }

    @Override
        public int getCount() {
            return OptionArray.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override

        public View getView(final int i, View view, ViewGroup viewGroup) {

            if (this.inflater == null) {
                this.inflater = (LayoutInflater) this.activity.getLayoutInflater();
                // getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            if (view == null) {
                view = this.inflater.inflate(R.layout.row_routes, null);
            }

            LinearLayout cdOption = (LinearLayout) view.findViewById(R.id.cdoption);
            LinearLayout cdSecondRoute = (LinearLayout) view.findViewById(R.id.secondroute);
            TextView route1 = (TextView)view.findViewById(R.id.tvroute1);
            TextView route2 = (TextView)view.findViewById(R.id.tvroute2);
            TextView route1Start = (TextView)view.findViewById(R.id.tvroute1start);
            TextView route2Start = (TextView)view.findViewById(R.id.tvroute2start);
            TextView route1End = (TextView)view.findViewById(R.id.tvroute1end);
            TextView route2End = (TextView)view.findViewById(R.id.tvroute2end);
            TextView Time =(TextView)view.findViewById(R.id.tvtime);
            TextView Cost =(TextView)view.findViewById(R.id.tvcost);




           if(OptionArray.get(i).size()==1){
               cdSecondRoute.setVisibility(View.GONE);
               route1.setText("Route " + OptionArray.get(i).get(0).getRouteID());
               route1Start.setText("Get In at " +OptionArray.get(i).get(0).getGetInHalt().getHaltName());
               route1End.setText("Get Down at " +OptionArray.get(i).get(0).getGetDownHalt().getHaltName());
           }else if(OptionArray.get(i).size()==2){
               route1.setText("Route " +OptionArray.get(i).get(0).getRouteID());
               route2.setText("Route " +OptionArray.get(i).get(1).getRouteID());
               route1Start.setText("Get In at " +OptionArray.get(i).get(0).getGetInHalt().getHaltName());
               route2Start.setText("Get In at " + OptionArray.get(i).get(1).getGetInHalt().getHaltName());
               route1End.setText("Get Down at "+OptionArray.get(i).get(0).getGetDownHalt().getHaltName());
               route2End.setText("Get Down at "+OptionArray.get(i).get(1).getGetDownHalt().getHaltName());

           }



            BusCost = Integer.toString(MainActivity.costDataCalculator.getBusCostArray().get(i)) + " RS" ;
            Log.d("fghj","list cost "+Integer.toString(MainActivity.costDataCalculator.getBusCostArray().get(i)));
            Cost.setText(BusCost);

            BusTime = Integer.toString(Math.round(MainActivity.costDataCalculator.getBusTimeArray().get(i))) + " Min";
            Time.setText(BusTime);



            cdOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    showSteps(i);

                }
            });

            return view;

        }


        private void showSteps(int k){
            Log.d("optclick","show");
            BusOptionData busOption = new BusOptionData("","","",null,"","",Integer.toString(Math.round(MainActivity.costDataCalculator.getBusTimeArray().get(k)))+ " Min",Integer.toString(MainActivity.costDataCalculator.getBusCostArray().get(k))+" RS");

            ActivityStepDetails.optionData=busOption;
            ActivityStepDetails.busTripRouteHaltsAray=OptionArray.get(k);

            Intent i = new Intent(activity, ActivityStepDetails.class);
            activity.startActivity(i);
        }


    }