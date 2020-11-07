package com.app.smartcitytraveler.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.smartcitytraveler.ActivityOptionDetails;
import com.app.smartcitytraveler.ActivityStepDetails;
import com.app.smartcitytraveler.MainActivity;
import com.app.smartcitytraveler.MediumData.SuperClasses.OptionData;
import com.app.smartcitytraveler.MediumData.TrainData;
import com.app.smartcitytraveler.PathFinder.Step;
import com.app.smartcitytraveler.R;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OptionAdapter extends BaseAdapter {


    private ArrayList<OptionData> OptionArray;
    private LayoutInflater inflater;
    Activity activity;

    public OptionAdapter(ArrayList<OptionData> optionArray, Activity activity) {
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
                view = this.inflater.inflate(R.layout.row_options, null);
            }

            LinearLayout cdOption = (LinearLayout) view.findViewById(R.id.cdoption);
            ImageView IVoptionlogo = (ImageView)view.findViewById(R.id.ivoptionlogo);
            TextView OptionID = (TextView)view.findViewById(R.id.tvoptionID);
            TextView StartStation = (TextView)view.findViewById(R.id.tvstartstaionname);
            TextView StartTime = (TextView)view.findViewById(R.id.tvstarttime);
            TextView EndStation = (TextView)view.findViewById(R.id.tvendstaionname);
            TextView EndTime = (TextView)view.findViewById(R.id.tvendtime);
            TextView Time =(TextView)view.findViewById(R.id.tvtime);
            TextView Cost =(TextView)view.findViewById(R.id.tvcost);

            TextView Day1 = (TextView)view.findViewById(R.id.day1);
            TextView Day2 = (TextView)view.findViewById(R.id.day2);
            TextView Day3 = (TextView)view.findViewById(R.id.day3);
            TextView Day4 = (TextView)view.findViewById(R.id.day4);
            TextView Day5 = (TextView)view.findViewById(R.id.day5);
            TextView Day6 = (TextView)view.findViewById(R.id.day6);
            TextView Day7 = (TextView)view.findViewById(R.id.day7);


            if(OptionArray.get(i).getStepMedium()=="WK"){
                IVoptionlogo.setImageResource(R.drawable.walk);
            }else if(OptionArray.get(i).getStepMedium()=="TR"){
                IVoptionlogo.setImageResource(R.drawable.train);
            }else if(OptionArray.get(i).getStepMedium()=="BS"){
                IVoptionlogo.setImageResource(R.drawable.bus);
            }else if(OptionArray.get(i).getStepMedium()=="TX"){
                IVoptionlogo.setImageResource(R.drawable.taxi);
            }else if(OptionArray.get(i).getStepMedium()=="MM"){
                IVoptionlogo.setImageResource(R.drawable.multi);
            }

            OptionID.setText(OptionArray.get(i).getNo());
            StartStation.setText(OptionArray.get(i).getStart());
            StartTime.setText(OptionArray.get(i).getStartTime());
            EndStation.setText(OptionArray.get(i).getEnd());
            EndTime.setText(OptionArray.get(i).getEndTime());
            Cost.setText(OptionArray.get(i).getCost());

            Time.setText(OptionArray.get(i).getTotalTime());

            for(String day : OptionArray.get(i).getDays().split(",")){

                switch (day){

                    case "0":
                        Day1.setVisibility(View.VISIBLE);
                        Day2.setVisibility(View.VISIBLE);
                        Day3.setVisibility(View.VISIBLE);
                        Day4.setVisibility(View.VISIBLE);
                        Day5.setVisibility(View.VISIBLE);
                        Day6.setVisibility(View.VISIBLE);
                        Day7.setVisibility(View.VISIBLE);
                        break;

                    case "1":
                        Day1.setVisibility(View.VISIBLE);
                        break;
                    case "2":
                        Day2.setVisibility(View.VISIBLE);
                        break;
                    case "3":
                        Day3.setVisibility(View.VISIBLE);
                        break;
                    case "4":
                        Day4.setVisibility(View.VISIBLE);
                        break;
                    case "5":
                        Day5.setVisibility(View.VISIBLE);
                        break;
                    case "6":
                        Day6.setVisibility(View.VISIBLE);
                        break;
                    case "7":
                        Day7.setVisibility(View.VISIBLE);
                        break;



                }

            }


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

            ActivityStepDetails.optionData=OptionArray.get(k);

            Intent i = new Intent(activity, ActivityStepDetails.class);
            activity.startActivity(i);
        }


    }