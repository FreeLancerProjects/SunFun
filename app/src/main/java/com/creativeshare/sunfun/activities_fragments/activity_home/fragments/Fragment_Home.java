package com.creativeshare.sunfun.activities_fragments.activity_home.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.activities_fragments.activity_create_event.activity.Create_Event_Activity;
import com.creativeshare.sunfun.activities_fragments.activity_home.activity.HomeActivity;
import com.creativeshare.sunfun.databinding.FragmentHomeBinding;
import com.creativeshare.sunfun.models.UserModel;
import com.creativeshare.sunfun.preferences.Preferences;
import com.creativeshare.sunfun.tags.Tags;

import io.paperdb.Paper;

public class Fragment_Home extends Fragment {
    private HomeActivity activity;
    private FragmentHomeBinding binding;
    private Preferences preferences;
    private UserModel userModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeActivity) getActivity();
        Paper.init(activity);
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        if (userModel==null)
        {
            binding.fab.setVisibility(View.GONE);
        }else
            {
                if (userModel.getUser().getUser_type().equals(Tags.type_company))
                {
                    binding.fab.setVisibility(View.VISIBLE);

                }else
                    {
                        binding.fab.setVisibility(View.GONE);

                    }
            }


        setUpBottomNavigation();
        binding.ahBottomNav.setOnTabSelectedListener((position, wasSelected) -> {
            switch (position) {
                case 0:
                    activity.DisplayFragmentMain();
                    break;
                case 1:
                    if (userModel!=null)
                    {
                        activity.DisplayFragmentNotifications();

                    }else
                    {
                        activity.CreateNoSignAlertDialog();
                    }

                    break;
                case 2:
                    if (userModel!=null)
                    {
                        activity.DisplayFragmentOrders();

                    }else
                    {
                        activity.CreateNoSignAlertDialog();

                    }

                    break;
                case 3:
                    activity.DisplayFragmentMore();
                    break;

            }
            return false;
        });
        binding.fab.setOnClickListener(view -> {
            Intent intent = new Intent(activity, Create_Event_Activity.class);
            startActivity(intent);
        });
    }

    private void setUpBottomNavigation() {

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(getString(R.string.home), R.drawable.ic_nav_home);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(getString(R.string.notifications), R.drawable.ic_nav_notification);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(getString(R.string.orders), R.drawable.ic_nav_cart);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(getString(R.string.more), R.drawable.ic_more);

        binding.ahBottomNav.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        binding.ahBottomNav.setDefaultBackgroundColor(ContextCompat.getColor(activity, R.color.white));
        binding.ahBottomNav.setTitleTextSizeInSp(14, 12);
        binding.ahBottomNav.setForceTint(true);
        binding.ahBottomNav.setAccentColor(ContextCompat.getColor(activity, R.color.colorAccent));
        binding.ahBottomNav.setInactiveColor(ContextCompat.getColor(activity, R.color.black));

        binding.ahBottomNav.addItem(item1);
        binding.ahBottomNav.addItem(item2);
        binding.ahBottomNav.addItem(item3);
        binding.ahBottomNav.addItem(item4);


    }

    public void updateBottomNavigationPosition(int pos) {
        binding.ahBottomNav.setCurrentItem(pos, false);
    }

    public static Fragment_Home newInstance() {
        return new Fragment_Home();
    }

}
