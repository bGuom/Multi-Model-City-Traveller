package com.app.smartcitytraveler;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.smartcitytraveler.Adapter.StepAdapter;
import com.app.smartcitytraveler.DirectionDataAPI.BusDirectionDataCollector;
import com.app.smartcitytraveler.DirectionDataAPI.CostDataCalculator;
import com.app.smartcitytraveler.DirectionDataAPI.DownloadUrl;
import com.app.smartcitytraveler.DirectionDataAPI.JsonDataParser;
import com.app.smartcitytraveler.MediumData.OptionClasses.TaxiOptionData;
import com.app.smartcitytraveler.MediumData.SuperClasses.OptionData;
import com.app.smartcitytraveler.PathFinder.Step;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.android.PolyUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class  MapNavActivity extends AppCompatActivity
        implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener, OnMapReadyCallback {


    public static ArrayList<Step> stepArray;
    public static OptionData optionData;
    public static ArrayList<ArrayList<String>> TaxiPolyArray;
    private int Taxipolycount= 0;

    private static final int MY_LOCATION_REQUEST_CODE = 1;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;



    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationManager locationManager;
    private LocationListener listener;
    private LatLng MyLatLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapnav_layout);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                MyLatLng = new LatLng(location.getLatitude(), location.getLongitude());

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };


        ImageView menubar = (ImageView)findViewById(R.id.menubutton);


        menubar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);

        try {
            boolean success = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this,R.raw.map_style_3));
            if (!success) {
                Log.e("err", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("err", "Can't find style. Error: ", e);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(false);
            startNavigation();

        } else {
            // Show rationale and request permission.
            enableMyLocation();

        }

    }


    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }

    @Override
    public boolean onMyLocationButtonClick() {

        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.

            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_PERMISSION_REQUEST_CODE);
            getMyLocation();

        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationButtonClickListener(this);
            mMap.setOnMyLocationClickListener(this);
            getMyLocation();
        }
    }

    private void startNavigation(){

        ArrayList<LatLng> Destination = optionData.getDestinations();

        addStartMarker(Destination.get(0),"Start");
        addEndMarker(Destination.get(Destination.size()-1),"Destination");

        if(optionData.getStepMedium().equals("MM")){
            for (int i = 1; i < Destination.size() - 1; i++) {
                addStepMarker(Destination.get(i), stepArray.get(i).getStepName(), i);
            }

        }else {
            for (int i = 1; i < Destination.size() - 1; i++) {
                addStepMarker(Destination.get(i), stepArray.get(i - 1).getStepName(), i);
            }
        }

        moveCamera(Destination.get(0),15);

        if(optionData.getStepMedium().equals("TX")){
                drawTaxiRoute();
        }

        if(optionData.getStepMedium().equals("TR")){
            drawTrainRoute();
        }

        if(optionData.getStepMedium().equals("BS")){
            drawBusRoute();
        }

        if(optionData.getStepMedium().equals("MM")){
            drawMMRoute();
        }

    }

    private void drawTaxiRoute(){

        ArrayList<String> polyLines = MainActivity.costDataCalculator.getTaxiPolyPaths();

        PolylineOptions polylineOptions = new PolylineOptions();

        for(int k =0; k<polyLines.size();k++) {
            polylineOptions.color(this.getResources().getColor(R.color.taxiroute));
            polylineOptions.width(12);
            polylineOptions.addAll(PolyUtil.decode(polyLines.get(k)));
        }

        mMap.addPolyline(polylineOptions);

        PolylineOptions walkPolylineOptions = new PolylineOptions();
        walkPolylineOptions.color(this.getResources().getColor(R.color.walkroute));
        walkPolylineOptions.width(12);
        walkPolylineOptions.add(stepArray.get(0).getStartLatLng());
        walkPolylineOptions.add(stepArray.get(0).getEndLatLng());

        List<PatternItem> pattern = Arrays.<PatternItem>asList(
                new Dot(), new Gap(10), new Dash(30), new Gap(10));
        walkPolylineOptions.pattern(pattern);

        mMap.addPolyline(walkPolylineOptions);






    }

    private void drawTrainRoute(){

        ArrayList<LatLng> polylineRoute = new ArrayList<LatLng>();

        ArrayList<LatLng> trainStationLatLngArr = MainActivity.costDataCalculator.getTrainDataController().getTrainStationLatLngArr();

        int start = MainActivity.costDataCalculator.getFromStation().getStationID()-1;
        int end = MainActivity.costDataCalculator.getEndStation().getStationID()-1;

        for(int r=start;r<end+1;r++){
            polylineRoute.add(trainStationLatLngArr.get(r));
        }


        PolylineOptions walkPolylineOptions = new PolylineOptions();
        walkPolylineOptions.color(this.getResources().getColor(R.color.walkroute));
        walkPolylineOptions.width(12);
        walkPolylineOptions.add(stepArray.get(0).getStartLatLng());
        walkPolylineOptions.add(stepArray.get(0).getEndLatLng());

        List<PatternItem> pattern = Arrays.<PatternItem>asList(
                new Dot(), new Gap(10), new Dash(30), new Gap(10));
        walkPolylineOptions.pattern(pattern);

        mMap.addPolyline(walkPolylineOptions);

        PolylineOptions walkPolylineOptions2 = new PolylineOptions();
        walkPolylineOptions2.color(this.getResources().getColor(R.color.walkroute));
        walkPolylineOptions2.width(12);
        walkPolylineOptions2.add(stepArray.get(2).getStartLatLng());
        walkPolylineOptions2.add(stepArray.get(2).getEndLatLng());

        walkPolylineOptions2.pattern(pattern);

        mMap.addPolyline(walkPolylineOptions2);

        PolylineOptions trainPolylineOptions = new PolylineOptions();
        trainPolylineOptions.color(this.getResources().getColor(R.color.trainroute));
        trainPolylineOptions.width(12);
        trainPolylineOptions.addAll(polylineRoute);
        mMap.addPolyline(trainPolylineOptions
        );



    }

    private void drawBusRoute(){

        PolylineOptions walkPolylineOptions = new PolylineOptions();
        walkPolylineOptions.color(this.getResources().getColor(R.color.walkroute));
        walkPolylineOptions.width(12);
        walkPolylineOptions.add(stepArray.get(0).getStartLatLng());
        walkPolylineOptions.add(stepArray.get(0).getEndLatLng());

        List<PatternItem> pattern = Arrays.<PatternItem>asList(
                new Dot(), new Gap(10), new Dash(30), new Gap(10));
        walkPolylineOptions.pattern(pattern);

        mMap.addPolyline(walkPolylineOptions);

        PolylineOptions walkPolylineOptions2 = new PolylineOptions();
        walkPolylineOptions2.color(this.getResources().getColor(R.color.walkroute));
        walkPolylineOptions2.width(12);
        walkPolylineOptions2.add(stepArray.get(stepArray.size()-1).getStartLatLng());
        walkPolylineOptions2.add(stepArray.get(stepArray.size()-1).getEndLatLng());

        walkPolylineOptions2.pattern(pattern);

        mMap.addPolyline(walkPolylineOptions2);

        for(int i = 1; i < stepArray.size()-1;i++){
            Step step = stepArray.get(i);
            if(step.getStepMedium().equals("BS")){

                StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
                stringBuilder.append("origin="+Double.toString( step.getStartLatLng().latitude)+","+Double.toString(step.getStartLatLng().longitude));
                stringBuilder.append("&destination="+Double.toString(step.getEndLatLng().latitude)+","+Double.toString(step.getEndLatLng().longitude));
                stringBuilder.append("&key="+MainActivity.GOOGLE_MAPS_API_KEY);



                new AsyncTaskx().execute(stringBuilder.toString());


            }
        }



    }

    private void drawMMRoute(){

        ArrayList<Step> BusStepArray = new ArrayList<Step>();

        for(Step step:stepArray){
            if(step.getStepMedium().equals("TR")){
                ArrayList<LatLng> polylineRoute = new ArrayList<LatLng>();

                ArrayList<LatLng> trainStationLatLngArr = MainActivity.costDataCalculator.getTrainDataController().getTrainStationLatLngArr();

                int start = MainActivity.costDataCalculator.getFromStation().getStationID()-1;
                int end = MainActivity.costDataCalculator.getEndStation().getStationID()-1;

                for(int r=start;r<end+1;r++){
                    polylineRoute.add(trainStationLatLngArr.get(r));
                }

                PolylineOptions trainPolylineOptions = new PolylineOptions();
                trainPolylineOptions.color(this.getResources().getColor(R.color.trainroute));
                trainPolylineOptions.width(12);
                trainPolylineOptions.addAll(polylineRoute);
                mMap.addPolyline(trainPolylineOptions
                );


            }

            if(step.getStepMedium().equals("WK")){

                PolylineOptions walkPolylineOptions = new PolylineOptions();
                walkPolylineOptions.color(this.getResources().getColor(R.color.walkroute));
                walkPolylineOptions.width(12);
                walkPolylineOptions.add(step.getStartLatLng());
                walkPolylineOptions.add(step.getEndLatLng());

                List<PatternItem> pattern = Arrays.<PatternItem>asList(
                        new Dot(), new Gap(10), new Dash(30), new Gap(10));
                walkPolylineOptions.pattern(pattern);

                mMap.addPolyline(walkPolylineOptions);


            }

            if(step.getStepMedium().equals("TX")){

                ArrayList<String> polyLines = TaxiPolyArray.get(Taxipolycount);
                PolylineOptions polylineOptions = new PolylineOptions();

                for(int k =0; k<polyLines.size();k++) {
                    polylineOptions.color(this.getResources().getColor(R.color.taxiroute));
                    polylineOptions.width(12);
                    polylineOptions.addAll(PolyUtil.decode(polyLines.get(k)));
                }

                mMap.addPolyline(polylineOptions);
                Taxipolycount+=1;
            }

            if(step.getStepMedium().equals("BS")){
                BusStepArray.add(step);
            }
        }

        for(int i = 1; i < BusStepArray.size()-1;i++){
            Step step = BusStepArray.get(i);
                StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
                stringBuilder.append("origin="+Double.toString( step.getStartLatLng().latitude)+","+Double.toString(step.getStartLatLng().longitude));
                stringBuilder.append("&destination="+Double.toString(step.getEndLatLng().latitude)+","+Double.toString(step.getEndLatLng().longitude));
                stringBuilder.append("&key="+MainActivity.GOOGLE_MAPS_API_KEY);

                new AsyncTaskx().execute(stringBuilder.toString());

        }


    }

    private void showBusRoute(ArrayList<String> TempPath){
        PolylineOptions polylineOptions = new PolylineOptions();

        for(int k =0; k<TempPath.size();k++) {
            polylineOptions.color(this.getResources().getColor(R.color.busroute));
            polylineOptions.width(12);
            polylineOptions.addAll(PolyUtil.decode(TempPath.get(k)));
        }

        mMap.addPolyline(polylineOptions);
    }


    public void getMyLocation(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                LatLng me = new LatLng(location.getLatitude(), location.getLongitude());
                                MyLatLng=me;
                                addMyMarker(me,"You are here");
                                moveCamera(me,15);
                            }
                        }
                    });
            locationManager.requestLocationUpdates("gps", 5000, 0, listener);
        } else {
            // Show rationale and request permission.
            enableMyLocation();

        }

    }

    public void moveCamera(LatLng latLng, int zoomSize){
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(latLng, zoomSize);
        mMap.animateCamera(location);
    }

    public Marker addMyMarker(LatLng latLng, String title){
        return mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(title)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

    }

    public Marker addStartMarker(LatLng latLng, String title){
        return mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(title)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.startmarker)));

    }
    public Marker addEndMarker(LatLng latLng, String title){
        return mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(title)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.endmarker)));

    }

    public Marker addStepMarker(LatLng latLng, String title, int stepnumber){

        BitmapDescriptor ico = BitmapDescriptorFactory.fromResource(R.drawable.marker);
        switch (stepnumber) {
            case 1:
                ico = BitmapDescriptorFactory.fromResource(R.drawable.step1);
                break;
            case 2:
                ico = BitmapDescriptorFactory.fromResource(R.drawable.step2);
                break;
            case 3:
                ico = BitmapDescriptorFactory.fromResource(R.drawable.step3);
                break;
            case 4:
                ico = BitmapDescriptorFactory.fromResource(R.drawable.step4);
                break;
            case 5:
                ico = BitmapDescriptorFactory.fromResource(R.drawable.step5);
                break;
            case 6:
                ico = BitmapDescriptorFactory.fromResource(R.drawable.step6);
                break;
            case 7:
                ico = BitmapDescriptorFactory.fromResource(R.drawable.step7);
                break;
            case 8:
                ico = BitmapDescriptorFactory.fromResource(R.drawable.step8);
                break;
            case 9:
                ico = BitmapDescriptorFactory.fromResource(R.drawable.step9);
                break;
            case 10:
                ico = BitmapDescriptorFactory.fromResource(R.drawable.step10);
                break;

        }
        return mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(title)
                .icon(ico));

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    public class AsyncTaskx extends AsyncTask<Object,String,String> {

        String URL;
        String GoogleDirectionData;

        //public GetAsyncDirectionsData(AsyncResponseHandleable parent){
        //    Parent = parent;
        //}

        @Override
        protected String doInBackground(Object... objects) {

            URL = (String)objects[0];

            DownloadUrl downloadUrl = new DownloadUrl();

            try {
                GoogleDirectionData = downloadUrl.readUrl(URL);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return GoogleDirectionData;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            HashMap<String,String> TempHash;
            ArrayList<String> TempPath;
            JsonDataParser jsonDataParser = new JsonDataParser();
            TempPath = jsonDataParser.getPathsArray(s);

            showBusRoute(TempPath);

        }





    }



}
