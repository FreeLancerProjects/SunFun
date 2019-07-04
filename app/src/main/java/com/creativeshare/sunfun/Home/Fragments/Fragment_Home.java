package com.creativeshare.sunfun.Home.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.creativeshare.sunfun.Home.Activity.HomeActivity;
import com.creativeshare.sunfun.R;

public class Fragment_Home extends Fragment {
    private HomeActivity homeActivity;
    private AHBottomNavigation ah_bottom_nav;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        homeActivity = (HomeActivity) getActivity();
        ah_bottom_nav = view.findViewById(R.id.ah_bottom_nav);
        setUpBottomNavigation();
        ah_bottom_nav.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch(position){
                    case 0:
                        homeActivity.DisplayFragmentMain();
                        break;
                    case 1:
                        homeActivity.DisplayFragmentorders();
                        break;
                    case 2:
                        homeActivity.DisplayFragmentnotifications();
                        break;
                    case 3:
                        homeActivity.DisplayFragmentMore();
                        break;
                }
                return false;
            }
        });
    }

    private void setUpBottomNavigation() {

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(getString(R.string.home), R.drawable.ic_house);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(getString(R.string.notifications), R.drawable.ic_orders);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(getString(R.string.orders), R.drawable.ic_notification);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem(getString(R.string.more), R.drawable.ic_more);

        ah_bottom_nav.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        ah_bottom_nav.setDefaultBackgroundColor(ContextCompat.getColor(homeActivity, R.color.white));
        ah_bottom_nav.setTitleTextSizeInSp(14, 12);
        ah_bottom_nav.setForceTint(true);
        ah_bottom_nav.setAccentColor(ContextCompat.getColor(homeActivity, R.color.colorAccent));
        ah_bottom_nav.setInactiveColor(ContextCompat.getColor(homeActivity, R.color.gray4));

        ah_bottom_nav.addItem(item1);
        ah_bottom_nav.addItem(item3);
        ah_bottom_nav.addItem(item4);
        ah_bottom_nav.addItem(item5);


    }

    public void updateBottomNavigationPosition(int pos) {
        ah_bottom_nav.setCurrentItem(pos, false);
    }

    public static Fragment_Home newInstance() {
        return new Fragment_Home();
    }

}
