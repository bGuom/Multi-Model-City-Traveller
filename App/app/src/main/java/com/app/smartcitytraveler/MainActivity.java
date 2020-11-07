package com.app.smartcitytraveler;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.smartcitytraveler.DirectionDataAPI.BusDirectionDataCollector;
import com.app.smartcitytraveler.DirectionDataAPI.CostDataCalculator;
import com.app.smartcitytraveler.DirectionDataAPI.DirectionDataGetter;
import com.app.smartcitytraveler.DirectionDataAPI.DownloadUrl;
import com.app.smartcitytraveler.DirectionDataAPI.JsonDataParser;
import com.app.smartcitytraveler.MediumData.Multi.BestMethodFinder;
import com.app.smartcitytraveler.MediumData.OptionClasses.TaxiOptionData;
import com.app.smartcitytraveler.PathFinder.ShortestPathFinder;
import com.app.smartcitytraveler.PathFinder.TaxiPathFinder;
import com.app.smartcitytraveler.TestData.TestData;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener, OnMapReadyCallback {

    public static CostDataCalculator costDataCalculator;
    public static String GOOGLE_MAPS_API_KEY = "XXXXX";

    private static final int MY_LOCATION_REQUEST_CODE = 1;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    public static HashMap<String,String> DirectionDataHash;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationManager locationManager;
    private LocationListener listener;
    private LatLng MyLatLng;

    private  PlaceAutocompleteFragment autocompleteFragment;
    private  PlaceAutocompleteFragment autocompleteFragment2;

    private CardView bottombar;
    private boolean isFromPlaceSelected , isToPlaceSelected = false;

    //-------------------------------------------------------------------

    private CardView cdBus;
    private  CardView cdTrain;
    private CardView cdTaxi;
    private CardView cdMulti;

    private TextView fareBus,fareTrain,fareTaxi,fareMulti;

    //--------------------------------------------------------------------

    private LatLng FromLatLng , ToLatLng;
    private Marker StartMarker,EndMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        costDataCalculator = new CostDataCalculator(this);
        //BusDirectionDataCollector busDirectionDataCollector = new BusDirectionDataCollector();
        //busDirectionDataCollector.getData();


        CardView cdlocate = (CardView)findViewById(R.id.cdme);

        CardView cdme1 = (CardView)findViewById(R.id.cdme1);
        CardView cdme2 = (CardView)findViewById(R.id.cdme2);
        bottombar = (CardView)findViewById(R.id.bottombar);

        cdBus = (CardView)findViewById(R.id.cdbus);
        cdTrain = (CardView)findViewById(R.id.cdtrain);
        cdTaxi =(CardView)findViewById(R.id.cdtaxi);
        cdMulti =(CardView)findViewById(R.id.cdmulti);

        fareBus = (TextView)findViewById(R.id.fareBus);
        fareTrain=(TextView)findViewById(R.id.fareTrain);
        fareTaxi=(TextView) findViewById(R.id.fareTaxi);
        fareMulti=(TextView)findViewById(R.id.fareMulti);


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
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(Gravity.START);




            }
        });

        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment2 = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment2);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("err", "Place: " + place.getName());
                FromLatLng=place.getLatLng();
                if(StartMarker!=null){
                    StartMarker.remove();
                }
                StartMarker = addStartMarker(FromLatLng,"From : "+place.getName().toString());
                isFromPlaceSelected=true;
                openbottom();
                moveCamera(FromLatLng,15);
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("err", "An error occurred: " + status);
                isFromPlaceSelected=false;
            }
        });

        autocompleteFragment2.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("err", "Place: " + place.getName());
                ToLatLng=place.getLatLng();
                if(EndMarker!=null){
                    EndMarker.remove();
                }
                EndMarker = addEndMarker(ToLatLng,"To : "+place.getName().toString());
                isToPlaceSelected=true;
                openbottom();
                moveCamera(ToLatLng,15);

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("err", "An error occurred: " + status);
                isToPlaceSelected=false;
            }

        });

        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setCountry("LK")
                .build();

        autocompleteFragment.setFilter(typeFilter);
        autocompleteFragment2.setFilter(typeFilter);

        ((EditText)autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input)).setTextSize(14.0f);
        ((EditText)autocompleteFragment2.getView().findViewById(R.id.place_autocomplete_search_input)).setTextSize(14.0f);

        cdlocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMyLocation();
            }
        });
        cdme1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((EditText)autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input)).setText("My Location");
                if(StartMarker!=null){
                    StartMarker.remove();
                }
                FromLatLng=MyLatLng;
                StartMarker = addStartMarker(MyLatLng,"From : My Location");
                moveCamera(MyLatLng,15);
                isFromPlaceSelected=true;
                openbottom();
            }
        });

        cdme2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((EditText)autocompleteFragment2.getView().findViewById(R.id.place_autocomplete_search_input)).setText("My Location");
                if(EndMarker!=null){
                    EndMarker.remove();
                }
                ToLatLng=MyLatLng;
                EndMarker = addEndMarker(MyLatLng,"To : My Location");
                moveCamera(MyLatLng,15);
                isToPlaceSelected=true;
                openbottom();
            }
        });

        autocompleteFragment.getView().findViewById(R.id.place_autocomplete_clear_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FromLatLng=null;
                        if(StartMarker!=null){
                            StartMarker.remove();
                        }
                        ((EditText)autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input)).setText("");
                        closeBottom();
                    }
                });

        autocompleteFragment2.getView().findViewById(R.id.place_autocomplete_clear_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ToLatLng=null;
                        if(EndMarker!=null){
                            EndMarker.remove();
                        }
                        ((EditText)autocompleteFragment2.getView().findViewById(R.id.place_autocomplete_search_input)).setText("");
                        closeBottom();
                    }
                });

        cdBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ActivityRouteOptionDetails.class);
                startActivity(i);
            }
        });

        cdTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, ActivityOptionDetails.class);
                i.putExtra("From", (LatLng)FromLatLng);
                i.putExtra("To", (LatLng)ToLatLng);
                startActivity(i);


            }
        });

        cdTaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<LatLng> Destinations = new ArrayList<LatLng>();
                Destinations.add(FromLatLng);
                Destinations.add(costDataCalculator.getNearestTaxiPark().getLatLng());
                Destinations.add(ToLatLng);

                TaxiOptionData taxiOptionData = new TaxiOptionData(Destinations,costDataCalculator.getTaxiTime(),costDataCalculator.getTaxiCost());

                ActivityStepDetails.optionData=taxiOptionData;

                Intent i = new Intent(MainActivity.this, ActivityStepDetails.class);
                MainActivity.this.startActivity(i);
            }
        });

        cdMulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, ActivityMultiOption.class);
                MainActivity.this.startActivity(i);

            }
        });


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
            getMyLocation();

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
        }
        return mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(title)
                .icon(ico));

    }




    public void openbottom(){
        if(isFromPlaceSelected && isToPlaceSelected) {
            costDataCalculator.updateFromTo(FromLatLng,ToLatLng);

        }

    }

    public void showbottombar(){
        costDataCalculator.bestMethodFinder= new BestMethodFinder();
        costDataCalculator.bestMethodFinder.updateWeights();
        fareBus.setText(costDataCalculator.getBusCost()+" / "+costDataCalculator.getBusTime());
        fareTrain.setText(costDataCalculator.getTrainCost()+" / "+costDataCalculator.getTrainTime());
        fareTaxi.setText(costDataCalculator.getTaxiCost()+" / "+costDataCalculator.getTaxiTime());
        fareMulti.setText(costDataCalculator.getMultiCost()+" / "+costDataCalculator.getMultiTime());
        bottombar.setVisibility(View.VISIBLE);
        animateBar();
    }



    public void animateBar(){
        float barh = 500;
        ValueAnimator slideAnimator;
        slideAnimator = ValueAnimator
                .ofInt(0, Math.round(barh))
                .setDuration(300);

        slideAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // get the value the interpolator is at
                Integer value = (Integer) animation.getAnimatedValue();
                // I'm going to set the layout's height 1:1 to the tick
                bottombar.getLayoutParams().height = value.intValue();
                // force all layouts to see which ones are affected by
                // this layouts height change
                bottombar.requestLayout();
            }
        });

// create a new animationset
        AnimatorSet set = new AnimatorSet();
// since this is the only animation we are going to run we just use
// play
        set.play(slideAnimator);
// this is how you set the parabola which controls acceleration
        set.setInterpolator(new AccelerateDecelerateInterpolator());
// start the animation
        set.start();
    }

    public void closeBottom(){
        float barh = 0;
        ValueAnimator slideAnimator;
        slideAnimator = ValueAnimator
                .ofInt(0, Math.round(barh))
                .setDuration(300);

        slideAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // get the value the interpolator is at
                Integer value = (Integer) animation.getAnimatedValue();
                // I'm going to set the layout's height 1:1 to the tick
                bottombar.getLayoutParams().height = value.intValue();
                // force all layouts to see which ones are affected by
                // this layouts height change
                bottombar.requestLayout();
            }
        });

// create a new animationset
        AnimatorSet set = new AnimatorSet();
// since this is the only animation we are going to run we just use
// play
        set.play(slideAnimator);
// this is how you set the parabola which controls acceleration
        set.setInterpolator(new AccelerateDecelerateInterpolator());
// start the animation
        set.start();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_manage) {

        }  else if (id == R.id.nav_send) {
            aboutAction(this);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static void aboutAction(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("About");

        TextView tv = new TextView(activity);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        tv.setText(Html.fromHtml(activity.getString(R.string.about_text)));

        LinearLayout lyt = new LinearLayout(activity);
        int padding = dip2px(activity, 20);
        lyt.setPadding(padding, padding, padding, padding);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lyt.setLayoutParams(params);
        lyt.setLayoutParams(params);
        lyt.addView(tv);

        builder.setView(lyt);
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}
