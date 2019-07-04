package com.creativeshare.sunfun.Home.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.creativeshare.sunfun.Home.Fragments.Fragment_Home;
import com.creativeshare.sunfun.Home.Fragments.Fragment_Main;
import com.creativeshare.sunfun.Home.Fragments.Fragment_More;
import com.creativeshare.sunfun.Home.Fragments.Fragment_Notidications;
import com.creativeshare.sunfun.Home.Fragments.Fragment_Orders;
import com.creativeshare.sunfun.R;

public class HomeActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private int fragment_count=0;
    private Fragment_Home fragment_home;
    private Fragment_Main fragment_main;
    private Fragment_More fragment_more;
    private Fragment_Orders fragment_orders;
    private Fragment_Notidications fragment_notidications;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        initView();

        if (savedInstanceState == null) {
            DisplayFragmentHome();
            DisplayFragmentMain();
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
    public void DisplayFragmentMain()
    {

        if (fragment_main == null) {
            fragment_main = Fragment_Main.newInstance();
        }
        if(fragment_more!=null&&fragment_more.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_more).commit();
        }
        if(fragment_orders!=null&&fragment_orders.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_orders).commit();
        }
        if(fragment_notidications!=null&&fragment_notidications.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_notidications).commit();
        }
        if (fragment_main.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_main).commit();

        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_home_container, fragment_main, "fragment_main").addToBackStack("fragment_main").commit();

        }
        if(fragment_home!=null&&fragment_home.isAdded()){
            fragment_home.updateBottomNavigationPosition(0);
        }

    }
    public void DisplayFragmentMore()
    {

        if (fragment_more == null) {
            fragment_more = Fragment_More.newInstance();
        }
if(fragment_main!=null&&fragment_main.isAdded()){
    fragmentManager.beginTransaction().hide(fragment_main).commit();
}
        if(fragment_orders!=null&&fragment_orders.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_orders).commit();
        }
        if(fragment_notidications!=null&&fragment_notidications.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_notidications).commit();
        }
        if (fragment_more.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_more).commit();

        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_home_container, fragment_more, "fragment_more").addToBackStack("fragment_more").commit();

        }
        if(fragment_home!=null&&fragment_home.isAdded()){
            fragment_home.updateBottomNavigationPosition(3);
        }
    }
    public void DisplayFragmentorders()
    {

        if (fragment_orders == null) {
            fragment_orders = Fragment_Orders.newInstance();
        }
        if(fragment_main!=null&&fragment_main.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_main).commit();
        }
        if(fragment_more!=null&&fragment_more.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_more).commit();
        }
        if(fragment_notidications!=null&&fragment_notidications.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_notidications).commit();
        }
        if (fragment_orders.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_orders).commit();

        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_home_container, fragment_orders, "fragment_orders").addToBackStack("fragment_orders").commit();

        }
        if(fragment_home!=null&&fragment_home.isAdded()){
            fragment_home.updateBottomNavigationPosition(1);
        }
    }
    public void DisplayFragmentnotifications()
    {

        if (fragment_notidications == null) {
            fragment_notidications = Fragment_Notidications.newInstance();
        }
        if(fragment_main!=null&&fragment_main.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_main).commit();
        }

        if(fragment_orders!=null&&fragment_orders.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_orders).commit();
        }
        if(fragment_more!=null&&fragment_more.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_more).commit();
        }
        if (fragment_notidications.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_notidications).commit();

        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_home_container, fragment_notidications, "fragment_notidications").addToBackStack("fragment_notidications").commit();

        }
        if(fragment_home!=null&&fragment_home.isAdded()){
            fragment_home.updateBottomNavigationPosition(2);
        }
    }
    @Override
    public void onBackPressed() {
        Back();
    }

    public void Back() {
        if (fragment_count > 1) {
            fragment_count -= 1;
            super.onBackPressed();
        } else {

            if (fragment_home != null && fragment_home.isVisible()) {
                if (fragment_main != null && fragment_main.isVisible()) {

                        finish();

                } else {
                    DisplayFragmentMain();
                }
            } else {
                DisplayFragmentHome();
            }
        }

    }
}
