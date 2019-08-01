package com.creativeshare.sunfun.activities_fragments.activity_home.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.activities_fragments.activity_book_event.BookEventActivity;
import com.creativeshare.sunfun.activities_fragments.activity_home.activity.HomeActivity;
import com.creativeshare.sunfun.adapter.ActivitiesAdapter;
import com.creativeshare.sunfun.adapter.EventSliderAdapter;
import com.creativeshare.sunfun.databinding.FragmentEventDetialsBinding;
import com.creativeshare.sunfun.models.EventDataModel;
import com.creativeshare.sunfun.models.UserModel;
import com.creativeshare.sunfun.preferences.Preferences;
import com.creativeshare.sunfun.share.Common;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import io.paperdb.Paper;

public class Fragment_Event_Details extends Fragment implements OnMapReadyCallback {
    private static final String TAG = "DATA";
    private HomeActivity activity;
    private String current_language;
    private FragmentEventDetialsBinding binding;
    private EventDataModel.EventModel eventModel;
    private GoogleMap mMap;
    private final float zoom = 16.5f;
    private List<String> images;
    private EventSliderAdapter sliderAdapter;
    private Timer timer;
    private TimerTask timerTask;
    private LinearLayoutManager manager;
    private ActivitiesAdapter adapter;
    private List<EventDataModel.EventModel.ActivityModel> activityModelList;
    private Preferences preferences;
    private UserModel userModel;

    public static Fragment_Event_Details newInstance(EventDataModel.EventModel eventModel) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAG, eventModel);
        Fragment_Event_Details fragment_event_details = new Fragment_Event_Details();
        fragment_event_details.setArguments(bundle);
        return fragment_event_details;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_detials, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        initMap();
        activityModelList = new ArrayList<>();
        images = new ArrayList<>();
        activity = (HomeActivity) getActivity();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        Paper.init(activity);
        current_language = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(current_language);
        binding.tab.setupWithViewPager(binding.pager);

        Bundle bundle = getArguments();
        if (bundle != null) {
            eventModel = (EventDataModel.EventModel) bundle.getSerializable(TAG);
            binding.setEvent(eventModel);
        }

        manager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        activityModelList.addAll(eventModel.getActivities());
        binding.recView.setLayoutManager(manager);
        adapter = new ActivitiesAdapter(activityModelList, activity, this);
        binding.recView.setAdapter(adapter);



        if (eventModel.getImage1() != null && !eventModel.getImage1().isEmpty() && !eventModel.getImage1().equals("0")) {
            images.add(eventModel.getImage1());
        }

        if (eventModel.getImage2() != null && !eventModel.getImage2().isEmpty() && !eventModel.getImage2().equals("0")) {
            images.add(eventModel.getImage2());
        }


        if (userModel != null) {
            if (Integer.parseInt(eventModel.getCompany_id()) == userModel.getUser().getId()) {
                binding.btnBook.setVisibility(View.GONE);
            } else {
                binding.btnBook.setVisibility(View.VISIBLE);


            }
        } else {
            binding.btnBook.setVisibility(View.GONE);

        }

        sliderAdapter = new EventSliderAdapter(images, activity);
        binding.pager.setAdapter(sliderAdapter);

        if (images.size() > 1) {
            for (int i = 0; i < binding.tab.getTabCount() - 1; i++) {

                View view = ((ViewGroup) binding.tab.getChildAt(0)).getChildAt(i);
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                params.setMargins(5, 0, 5, 0);
                binding.tab.requestLayout();
            }

            timer = new Timer();
            timerTask = new MyTimerTask();
            timer.scheduleAtFixedRate(timerTask, 6000, 6000);
        }





        binding.appBar.addOnOffsetChangedListener((AppBarLayout.BaseOnOffsetChangedListener) (appBarLayout, verticalOffset) -> {
            int total_rang = appBarLayout.getTotalScrollRange();
            if ((total_rang + verticalOffset) > 70) {

                if (userModel != null) {
                    if (Integer.parseInt(eventModel.getCompany_id()) == userModel.getUser().getId()) {
                        binding.btnBook.setVisibility(View.GONE);
                    } else {
                        binding.btnBook.setVisibility(View.VISIBLE);


                    }
                } else {
                    binding.btnBook.setVisibility(View.GONE);

                }

            } else {
                binding.btnBook.setVisibility(View.GONE);

            }
        });
        binding.arrow.setOnClickListener(view -> activity.Back());

        binding.btnBook.setOnClickListener(view -> {
            if (userModel != null) {
                Intent intent = new Intent(activity, BookEventActivity.class);
                intent.putExtra("data", eventModel);
                startActivity(intent);
            } else {
                Common.CreateNoSignAlertDialog(activity);
            }

        });

    }


    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);

        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setBuildingsEnabled(false);
        googleMap.setIndoorEnabled(true);
        googleMap.setTrafficEnabled(false);
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(activity, R.raw.maps));
        addMarker();
    }

    private void addMarker() {
        Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(eventModel.getLatitude()), Double.parseDouble(eventModel.getLongitude()))));
        if (eventModel.getAddress() != null) {
            marker.setTitle(eventModel.getAddress());
        }

        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(eventModel.getLatitude()), Double.parseDouble(eventModel.getLongitude())), zoom));
    }


    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            activity.runOnUiThread(() -> {

                if (binding.pager.getCurrentItem() < binding.pager.getAdapter().getCount() - 1) {
                    binding.pager.setCurrentItem(binding.pager.getCurrentItem() + 1, true);
                } else {
                    binding.pager.setCurrentItem(0);
                }
            });
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (timer != null) {
            timer.purge();
            timer.cancel();
        }
        if (timerTask != null) {
            timerTask.cancel();
        }
    }
}
