package com.example.budget.Views;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.util.Base64;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.budget.Components.Constants;
import com.example.budget.MainActivity;
import com.example.budget.Models.User;
import com.example.budget.Network.RestClient;
import com.example.budget.PokemonApplication;
import com.example.budget.R;
import com.example.budget.Stages.UsersCaughtStage;
import com.example.budget.Stages.UsersNearbyStage;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import flow.History;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by eaglebrosi on 10/31/16.
 */
//couldn't name it MapView
public class PeopleMapView extends RelativeLayout implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener {
    // IT HAS to get the context that it's calling.
    private Context context = getContext();
    public String pokemonName;
    public String pokemonId;
    protected Location location;
    public LatLng loc;
    public GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    private Bitmap customAvatar;
    Handler handler = new Handler();
    // on marker click override the on marker click
    //this is the list of people who are nearby.
    ArrayList<User> pokemon;
    ArrayList<User> pokemonCaught;
    Place currentPlace = null;

    @Bind(R.id.map)
    public MapView mapView;

    @Bind(R.id.check_in_button)
    public FloatingActionButton checkInButton;

    @Bind(R.id.check_names)
    public FloatingActionButton catchPokemonButton;

    @Bind(R.id.i_dont_know_yet)
    public FloatingActionButton unDecidedVoter;

    public PeopleMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        //Yo DAWG I had to create a varible called savedInstanceState in my main to do this Mayne.
        mapView.onCreate(((MainActivity) getContext()).savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this.context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
//        if (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.context,
//                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
        handler.post(locationCheck);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
//        Toast.makeText(context, "You caught " + marker.getTitle(), Toast.LENGTH_SHORT).show();
        // We remove the ability to catch them again.
        marker.remove();
//        User pokemonCatch = new User(marker.getTitle(), Constants.radiusInMeters);
        RestClient restClient = new RestClient();
        restClient.getApiService().catchemAll(marker.getTitle(), Constants.radiusInMeters).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
//                    Toast.makeText(context, "IT WORKEDDDDDD DUDED!!", Toast.LENGTH_SHORT).show();
//                    letsSeeThem();



                    // todo animate for 5 seconds.
                    final Circle circle = mMap.addCircle(new CircleOptions().center(loc)
                            .strokeColor(Color.RED).radius(50));
                    ValueAnimator vAnimator = new ValueAnimator();
                    vAnimator.setRepeatCount(ValueAnimator.INFINITE);
                    vAnimator.setRepeatMode(ValueAnimator.RESTART);  /* PULSE */
                    vAnimator.setIntValues(80, 0);
                    vAnimator.setDuration(500);
                    vAnimator.setEvaluator(new IntEvaluator());
                    vAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                    vAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            float animatedFraction = valueAnimator.getAnimatedFraction();
                            // Log.e("", "" + animatedFraction);
                            circle.setRadius(animatedFraction * 80);
                        }
                    });
                    vAnimator.start();

                } else {
                    Toast.makeText(context, "This user has ceased to be active!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
            }
        });
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        RestClient restClient = new RestClient();
        restClient.getApiService().getUserInfo().enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    if ((user.getAvatar64() == null) || (user.getAvatar64().length() <= 50)) {
                        try {
                            mMap.setMyLocationEnabled(true);
                        } catch (SecurityException e) {
                            Toast.makeText(context, "error, location services failed.", Toast.LENGTH_SHORT).show();
                        }
                        mMap.clear();
//                        mMap.setOnMarkerClickListener(this);
                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    } else {
                        String encodedImage = user.getAvatar64();
                        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        decodedByte = Bitmap.createScaledBitmap(decodedByte, 160, 160, false);
                        customAvatar = decodedByte;
                        try {
                            mMap.setMyLocationEnabled(true);
                        } catch (SecurityException e) {
                            Toast.makeText(context, "error, location services failed.", Toast.LENGTH_SHORT).show();
                        }
                        if(loc != null) {
                            mMap.clear();
                            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                            mMap.addMarker(new MarkerOptions()
                                    .position(loc)
                                    .title("Me")
                                    .icon(BitmapDescriptorFactory.fromBitmap(decodedByte)));
                        } else {
                            loc = new LatLng(37.8163, -82.8095);
                        }
                    }
                } else {
                    Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

//        //we set up the button.
//        mMap.getUiSettings().isMyLocationButtonEnabled();
//        // set the button to actually work.
//        mMap.getUiSettings().setMyLocationButtonEnabled(true);
//        // calls the location change method.




    Runnable locationCheck = new Runnable() {
        @Override
        public void run() {
            try {
                try {
                    mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

                    if (mLastLocation != null) {
                        // instead of constantly updating location every few seconds I put it on the right hand pink button.
                     //  updateLocation();
                    }
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            } finally {
                handler.postDelayed(this, 8000);
            }
        }
    };

    // set my check in to a the bottom right button!
    @OnClick(R.id.check_in_button)
    public void updateLocation() {
        if (location != null) {
            updateLocation();
        } else {
            loc = new LatLng(mLastLocation.getLongitude(), mLastLocation.getLatitude());
            mMap.addMarker(new MarkerOptions().position(loc));
            //.icon(BitmapDescriptorFactory.fromResource(R.mipmap.banana_gram));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
            final RestClient restClient = new RestClient();
            // we call a Void because we aren't getting information- just giving info.
            restClient.getApiService().CheckIn(loc).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        // now that are check in works- we check to see if there's any programmers nearby.
                        Toast.makeText(context, "You checked in!", Toast.LENGTH_SHORT).show();
                        // sets the radar on the hit.
                        GroundOverlayOptions radar = new GroundOverlayOptions()
                                .image(BitmapDescriptorFactory.fromResource(R.mipmap.radar))
                                .position(loc, 200f, 200f);
                        GroundOverlay imageOverlay = mMap.addGroundOverlay(radar);
                        // now we provide an animated circle for the radar.
                        final Circle circle = mMap.addCircle(new CircleOptions().center(loc)
                                .strokeColor(Color.BLUE).radius(500));
                        ValueAnimator vAnimator = new ValueAnimator();
                        vAnimator.setRepeatCount(ValueAnimator.INFINITE);
                        vAnimator.setRepeatMode(ValueAnimator.RESTART);  /* PULSE */
                        vAnimator.setIntValues(80, 0);
                        vAnimator.setDuration(2500);
                        vAnimator.setEvaluator(new IntEvaluator());
                        vAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                        vAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                float animatedFraction = valueAnimator.getAnimatedFraction();
                                // Log.e("", "" + animatedFraction);
                                circle.setRadius(animatedFraction * 80);
                            }
                        });
                        vAnimator.start();
                        //now we move back the business of checking for others.
                        checkForNearby();
                    } else {
                        Toast.makeText(context, "we got problems", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(context, "DIDNT' WORKK", Toast.LENGTH_SHORT).show();
                }
            });
            mMap.clear();
        }
    }

    public void checkForNearby() {
        final RestClient restClient = new RestClient();
        restClient.getApiService().nearby(Constants.radiusInMeters).enqueue(new Callback<User[]>() {
            @Override
            public void onResponse(Call<User[]> call, Response<User[]> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Careful there are other programmers nearby", Toast.LENGTH_SHORT).show();
                    pokemon = new ArrayList<User>(Arrays.asList(response.body()));
                    for (User nearby : pokemon) {
                        pokemonId = nearby.getUserId();
                        pokemonName = nearby.getNotAnEmail();

                        //Okay, I'm really proud of this code, people were putting "string" or "null" or whatever as
                        //their avatar64 and i got tired of checking for it- so it the length is less than 50 we assume
                        //that they don't have a nice image.
                        if ((nearby.getAvatar64() == null) || (nearby.getAvatar64().length() <= 50)){
                            //if they don't have a cool image- they get a dumb thing.
                            LatLng loc = new LatLng(nearby.getLatitude(), nearby.getLongitude());
                            mMap.addMarker(new MarkerOptions().title(pokemonId).position(loc));
                            //   then we go to our onmarkerclick
                        } else {

                            String encodedImage = nearby.getAvatar64();
                            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                            // this makes the images smaller, where they need to be.
                            // decodedByte = Bitmap.createScaledBitmap(decodedByte, 120, 120, false);
                            try {
                                decodedByte = Bitmap.createScaledBitmap(decodedByte, 120, 120, false);

                                LatLng loc = new LatLng(nearby.getLatitude(), nearby.getLongitude());
                                //            mMap.addMarker(new MarkerOptions().title(pokemonId).position(loc));
                                mMap.addMarker(new MarkerOptions().title(pokemonId)
                                        .icon(BitmapDescriptorFactory.fromBitmap(decodedByte))
                                        .snippet(nearby.getUserId())
                                        .position(loc));
                            } catch (Exception e) {
//                                Log.e(PeopleMapView.class.getSimpleName(), e.toString());
                            }
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<User[]> call, Throwable t) {
                Toast.makeText(context, "YOU ARE A FAILURE!!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.i_dont_know_yet)
    public void showNearbyView() {
        Flow flow = PokemonApplication.getMainFlow();
        History newHistory = flow.getHistory().buildUpon()
                .push(new UsersNearbyStage())
                .build();
        flow.setHistory(newHistory, Flow.Direction.FORWARD);
    }

    @OnClick(R.id.check_names)
    public void showAddCategoryView(){
        Flow flow = PokemonApplication.getMainFlow();
        History newHistory = flow.getHistory().buildUpon()
                .push(new UsersCaughtStage())
                .build();
        flow.setHistory(newHistory, Flow.Direction.FORWARD);
    }
}
