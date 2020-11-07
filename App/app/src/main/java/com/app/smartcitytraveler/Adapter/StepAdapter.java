package com.app.smartcitytraveler.Adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.smartcitytraveler.PathFinder.Step;
import com.app.smartcitytraveler.R;

import java.util.ArrayList;

public class StepAdapter extends BaseAdapter {


    private ArrayList<Step> StepArray;
    private LayoutInflater inflater;
    Activity activity;

    public StepAdapter(ArrayList<Step> stepArray, Activity activity) {
        StepArray = stepArray;
        this.activity = activity;
    }

    @Override
        public int getCount() {
            return StepArray.size();
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
                view = this.inflater.inflate(R.layout.row_steps, null);
            }
            LinearLayout Step = (LinearLayout)view.findViewById(R.id.cdstep);
            ImageView IVStepNum = (ImageView)view.findViewById(R.id.ivstep);
            ImageView IVStepMedium = (ImageView)view.findViewById(R.id.ivstepmedium);
            TextView StepNum = (TextView)view.findViewById(R.id.tvstepnumber);
            TextView StepName = (TextView)view.findViewById(R.id.tvstepname);
            TextView StepDetails = (TextView)view.findViewById(R.id.tvstepdetails);

            if (i==0){
                IVStepNum.setImageResource(R.drawable.guidestepstart);
            }else if(i==StepArray.size()-1){
                IVStepNum.setImageResource(R.drawable.guidestepend);
            }else{
                IVStepNum.setImageResource(R.drawable.guidestepmid);
            }

            if(StepArray.get(i).getStepMedium().equals("WK")){
                IVStepMedium.setImageResource(R.drawable.walk);
            }else if(StepArray.get(i).getStepMedium().equals("TR")){
                IVStepMedium.setImageResource(R.drawable.train);
            }else if(StepArray.get(i).getStepMedium().equals("BS")){
                IVStepMedium.setImageResource(R.drawable.bus);
            }else if(StepArray.get(i).getStepMedium().equals("TX")){
                IVStepMedium.setImageResource(R.drawable.taxi);
            }else if(StepArray.get(i).getStepMedium().equals("MM")){
                IVStepMedium.setImageResource(R.drawable.multi);
            }

            StepNum.setText(Integer.toString(i+1));
            StepName.setText(StepArray.get(i).getStepName());
            StepDetails.setText(StepArray.get(i).getStepDetails());

            return view;

        }


    }