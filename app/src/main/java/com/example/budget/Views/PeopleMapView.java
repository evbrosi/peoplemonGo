package com.example.budget.Views;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.budget.Components.Constants;
import com.example.budget.MainActivity;
import com.example.budget.Models.User;
import com.example.budget.Network.RestClient;
import com.example.budget.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
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

    @Bind(R.id.map)
    public MapView mapView;

    @Bind(R.id.check_in_button)
    public FloatingActionButton checkInButton;

    @Bind(R.id.catch_pokemon_button)
    public FloatingActionButton catchPokemonButton;

    @Bind(R.id.i_dont_know_yet)
    public FloatingActionButton unDecidedVoter;

    public GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;

    //this is the list of people who are nearby.
    ArrayList<User> pokemon;

    ArrayList<User> pokemonCaught;

    Place currentPlace = null;

//    private GoogleApiClient mGoogleApiClient;

    //   public static final String TAG = MainActivity.class.getSimpleName();

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

    public PeopleMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.context,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        LatLng latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        Toast.makeText(context, "You caught " + marker.getSnippet(), Toast.LENGTH_SHORT).show();
        // We remove the ability to catch them again.
        marker.remove();
        User pokemonCatch = new User(marker.getSnippet(), Constants.radiusInMeters);
        RestClient restClient = new RestClient();
        restClient.getApiService().catchemAll(pokemonId, Constants.radiusInMeters).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "IT WORKEDDDDDD DUDED!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "NOOOOOOOoooooooo!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "help me you loser!", Toast.LENGTH_LONG).show();
            }
        });
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //we set up the button.
        mMap.getUiSettings().isMyLocationButtonEnabled();
        // set the button to actually work.
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        // calls the location change method.

        mMap.setOnMyLocationChangeListener(myLocationChangeLister);

        try {
            mMap.setMyLocationEnabled(true);
        } catch (SecurityException e) {
        }
//        mMap.clear();
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    private GoogleMap.OnMyLocationChangeListener myLocationChangeLister = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            LatLng loc = new LatLng(location.getLongitude(), location.getLatitude());

            mMap.addMarker(new MarkerOptions().position(loc));
                    //.icon(BitmapDescriptorFactory.fromResource(R.mipmap.banana_gram));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));

            final RestClient restClient = new RestClient();
            // we call a Void because we aren't getting information- just giving info.
            restClient.getApiService().CheckIn(loc).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(context, "DUDE- IT FREAKING WORKED", Toast.LENGTH_SHORT).show();
                        // now that are check in works- we check to see if there's any programmers nearby.
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
    };

    public void checkForNearby (){
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
                        LatLng loc = new LatLng(nearby.getLatitude(), nearby.getLongitude());
                        mMap.addMarker(new MarkerOptions().title(pokemonName).position(loc));
                      catchThemAll();
                    }
                }
            }
            @Override
            public void onFailure(Call<User[]> call, Throwable t) {
                Toast.makeText(context, "YOU ARE A FAILURE!!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void catchThemAll(){
         mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // We remove the ability to catch them again.
                RestClient restClient = new RestClient();
                restClient.getApiService().catchemAll(pokemonId, Constants.radiusInMeters).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(context, "Got'em!", Toast.LENGTH_SHORT).show();
                            letsSeeThem();
                        } else {
                            Toast.makeText(context, "NOOOOOOOoooooooo!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(context, "help me you loser!", Toast.LENGTH_LONG).show();
                    }
                });
                marker.remove();
                return true;
            }
        });
    }

    public void letsSeeThem(){
        RestClient restClient = new RestClient();
        restClient.getApiService().caught().enqueue(new Callback<User[]>() {
            @Override
            public void onResponse(Call<User[]> call, Response<User[]> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "YOU DID IT!!!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "OH MY GOD!!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User[]> call, Throwable t) {
                Toast.makeText(context, "OH NOOOOOOO", Toast.LENGTH_SHORT).show();
            }
        });
    }
}