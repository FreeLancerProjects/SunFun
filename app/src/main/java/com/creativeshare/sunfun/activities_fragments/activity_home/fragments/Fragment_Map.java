package com.creativeshare.sunfun.activities_fragments.activity_home.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.activities_fragments.activity_home.activity.HomeActivity;
import com.creativeshare.sunfun.databinding.FragmentMapBinding;
import com.creativeshare.sunfun.models.PlaceGeocodeData;
import com.creativeshare.sunfun.models.PlaceMapDetailsData;
import com.creativeshare.sunfun.models.SelectedLocation;
import com.creativeshare.sunfun.remote.Api;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import java.io.IOException;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Map extends Fragment implements OnMapReadyCallback ,GoogleApiClient.OnConnectionFailedListener,GoogleApiClient.ConnectionCallbacks, LocationListener {
    private static final String TAG1 = "FROM";
    private final String gps_perm = Manifest.permission.ACCESS_FINE_LOCATION;
    private final int gps_req = 22;
    private HomeActivity activity;
    private String current_language;
    private double lat = 0.0, lng = 0.0;
    private String address="";
    private GoogleMap mMap;
    private Marker marker;
    private float zoom = 15.6f;
    private String from="";
    private FragmentMapBinding binding;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;



    public static Fragment_Map newInstance(String from) {
        Fragment_Map fragment_map = new Fragment_Map();
        Bundle bundle = new Bundle();
        bundle.putString(TAG1, from);
        fragment_map.setArguments(bundle);
        return fragment_map;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_map,container,false);
        initView();
        return binding.getRoot();
    }



    private void initView() {
        activity = (HomeActivity) getActivity();
        Paper.init(activity);
        current_language = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(current_language);

        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        binding.fab.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(address))
            {
                SelectedLocation selectedLocation = new SelectedLocation(address,lat,lng);
                activity.listenForMapLocation(from,selectedLocation);
            }
        });
        binding.llBack.setOnClickListener(v -> activity.Back());

        binding.edtSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String query = binding.edtSearch.getText().toString();
                if (!TextUtils.isEmpty(query)) {
                    Search(query);
                    return true;
                }
            }
            return false;
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            from = bundle.getString(TAG1);
            updateUI();
        }
        CheckPermission();



    }



    private void updateUI() {

        SupportMapFragment fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (googleMap != null) {
            mMap = googleMap;
            mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(activity, R.raw.maps));
            mMap.setTrafficEnabled(false);
            mMap.setBuildingsEnabled(false);
            mMap.setIndoorEnabled(true);


            mMap.setOnMapClickListener(latLng -> {
                lat = latLng.latitude;
                lng = latLng.longitude;
                getGeoData(lat,lng);
                AddMarker(latLng.latitude,latLng.longitude);
            });

        }
    }

    private void Search(String query) {

        binding.imagePin.setVisibility(View.GONE);
        binding.progBar.setVisibility(View.VISIBLE);

        String fields = "id,place_id,name,geometry,formatted_address";

        Api.getService("https://maps.googleapis.com/maps/api/")
                .searchOnMap("textquery", query, fields, current_language, getString(R.string.map_api_key))
                .enqueue(new Callback<PlaceMapDetailsData>() {
                    @Override
                    public void onResponse(Call<PlaceMapDetailsData> call, Response<PlaceMapDetailsData> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            binding.imagePin.setVisibility(View.VISIBLE);
                            binding.progBar.setVisibility(View.GONE);

                            if (response.body().getCandidates().size() > 0) {

                                address = response.body().getCandidates().get(0).getFormatted_address().replace("Unnamed Road,","");
                                binding.tvAddress.setText(address+"");
                                AddMarker(response.body().getCandidates().get(0).getGeometry().getLocation().getLat(),response.body().getCandidates().get(0).getGeometry().getLocation().getLng());
                            }
                        }
                        else
                        {

                            binding.imagePin.setVisibility(View.VISIBLE);
                            binding.progBar.setVisibility(View.GONE);
                            try {
                                Log.e("error_code", response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<PlaceMapDetailsData> call, Throwable t) {
                        try {


                            binding.imagePin.setVisibility(View.VISIBLE);
                            binding.progBar.setVisibility(View.GONE);
                            Toast.makeText(activity, getString(R.string.something), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {

                        }
                    }
                });
    }

    private void getGeoData(final double lat, double lng)
    {
        binding.imagePin.setVisibility(View.GONE);
        binding.progBar.setVisibility(View.VISIBLE);

        String location = lat+","+lng;
        Api.getService("https://maps.googleapis.com/maps/api/")
                .getGeoData(location,current_language,getString(R.string.map_api_key))
                .enqueue(new Callback<PlaceGeocodeData>() {
                    @Override
                    public void onResponse(Call<PlaceGeocodeData> call, Response<PlaceGeocodeData> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            binding.imagePin.setVisibility(View.VISIBLE);
                            binding.progBar.setVisibility(View.GONE);

                            if (response.body().getResults().size()>0)
                            {
                                address =response.body().getResults().get(0).getFormatted_address().replace("Unnamed Road,","");
                                binding.tvAddress.setText(address+"");
                                AddMarker(response.body().getResults().get(0).getGeometry().getLocation().getLat(),response.body().getResults().get(0).getGeometry().getLocation().getLng());
                            }
                        }
                        else
                        {

                            binding.imagePin.setVisibility(View.VISIBLE);
                            binding.progBar.setVisibility(View.GONE);
                            try {
                                Log.e("error_code", response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<PlaceGeocodeData> call, Throwable t) {
                        try {


                            binding.imagePin.setVisibility(View.VISIBLE);
                            binding.progBar.setVisibility(View.GONE);
                            Toast.makeText(activity, getString(R.string.something), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {

                        }
                    }
                });
    }

    private void AddMarker(double lat, double lng) {

        this.lat = lat;
        this.lng = lng;

        if (marker == null) {
            IconGenerator iconGenerator = new IconGenerator(activity);
            iconGenerator.setBackground(null);
            View view = LayoutInflater.from(activity).inflate(R.layout.map_you_icon, null);
            iconGenerator.setContentView(view);
            marker = mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).icon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon())).anchor(iconGenerator.getAnchorU(), iconGenerator.getAnchorV()));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng),zoom));
        } else {
            marker.setPosition(new LatLng(lat, lng));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng),zoom));


        }
    }

    private void CheckPermission()
    {
        if (ActivityCompat.checkSelfPermission(activity, gps_perm) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{gps_perm}, gps_req);
        } else {
            Log.e("sss","dddd");
            initGoogleApiClient();

        }
    }
    private void initGoogleApiClient()
    {
        googleApiClient = new GoogleApiClient.Builder(activity)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }
    private void intLocationRequest()
    {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(30000);
        locationRequest.setFastestInterval(30000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());

        result.setResultCallback(result1 -> {

            Status status = result1.getStatus();
            switch (status.getStatusCode())
            {
                case LocationSettingsStatusCodes.SUCCESS:
                    startLocationUpdate();
                    break;
                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                    try {
                        status.startResolutionForResult(activity,1255);
                    }catch (Exception e)
                    {
                    }
                    break;
                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                    Log.e("not available","not available");
                    break;
            }
        });

    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdate()
    {

        locationCallback = new LocationCallback()
        {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                onLocationChanged(locationResult.getLastLocation());
            }
        };
        LocationServices.getFusedLocationProviderClient(activity)
                .requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper());
    }

    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        intLocationRequest();

    }

    @Override
    public void onConnectionSuspended(int i) {
        if (googleApiClient!=null)
        {
            googleApiClient.connect();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();
        getGeoData(lat,lng);
        if (googleApiClient!=null)
        {
            googleApiClient.disconnect();
        }
        if (locationCallback!=null)
        {
            LocationServices.getFusedLocationProviderClient(activity).removeLocationUpdates(locationCallback);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {

        if (requestCode == gps_req && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initGoogleApiClient();

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1255&&resultCode== Activity.RESULT_OK)
        {
            startLocationUpdate();
        }
    }
}
