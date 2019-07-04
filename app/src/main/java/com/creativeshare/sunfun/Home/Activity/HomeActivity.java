package com.creativeshare.sunfun.Home.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.creativeshare.sunfun.Home.Fragments.Fragment_Home;
import com.creativeshare.sunfun.R;

public class HomeActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private int fragment_count=0;
    private Fragment_Home fragment_home;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        initView();

        if (savedInstanceState == null) {
            DisplayFragmentHome();
        }

    }

    private void initView() {
        fragmentManager = getSupportFragmentManager();
    }
    public void DisplayFragmentHome()
    {
        fragment_count+=1;
        if (fragment_home == null) {
            fragment_home = Fragment_Home.newInstance();
        }

        if (fragment_home.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_home).commit();

        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_home, "fragment_home").addToBackStack("fragment_home").commit();

        }

    }
}
