package com.creativeshare.sunfun.activities_fragments.activity_home.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.activities_fragments.activity_home.fragments.FragmentUpgradeToCompany;
import com.creativeshare.sunfun.activities_fragments.activity_home.fragments.Fragment_Bank_Account;
import com.creativeshare.sunfun.activities_fragments.activity_home.fragments.Fragment_Client_Profile;
import com.creativeshare.sunfun.activities_fragments.activity_home.fragments.Fragment_Company_Profile;
import com.creativeshare.sunfun.activities_fragments.activity_home.fragments.Fragment_Contact_Us;
import com.creativeshare.sunfun.activities_fragments.activity_home.fragments.Fragment_Event_Details;
import com.creativeshare.sunfun.activities_fragments.activity_home.fragments.Fragment_Home;
import com.creativeshare.sunfun.activities_fragments.activity_home.fragments.Fragment_Main;
import com.creativeshare.sunfun.activities_fragments.activity_home.fragments.Fragment_Map;
import com.creativeshare.sunfun.activities_fragments.activity_home.fragments.Fragment_More;
import com.creativeshare.sunfun.activities_fragments.activity_home.fragments.Fragment_Notidications;
import com.creativeshare.sunfun.activities_fragments.activity_home.fragments.fragment_orders.Fragment_Orders;
import com.creativeshare.sunfun.activities_fragments.activity_sign_in.activities.SignInActivity;
import com.creativeshare.sunfun.databinding.DialogCustomBinding;
import com.creativeshare.sunfun.language.Language;
import com.creativeshare.sunfun.models.EventDataModel;
import com.creativeshare.sunfun.models.SelectedLocation;
import com.creativeshare.sunfun.models.UserModel;
import com.creativeshare.sunfun.preferences.Preferences;
import com.creativeshare.sunfun.remote.Api;
import com.creativeshare.sunfun.singleton.Singleton;
import com.creativeshare.sunfun.tags.Tags;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private int fragment_count = 0;
    private Fragment_Home fragment_home;
    private Fragment_Main fragment_main;
    private Fragment_More fragment_more;
    private Fragment_Orders fragment_orders;
    private Fragment_Notidications fragment_notidications;
    private Fragment_Contact_Us fragment_contact_us;
    private Fragment_Event_Details fragment_event_details;
    private Fragment_Bank_Account fragment_bank_account;
    private Fragment_Client_Profile fragment_client_profile;
    private Fragment_Company_Profile fragment_company_profile;
    private Fragment_Map fragment_map;
    private FragmentUpgradeToCompany fragmentUpgradeToCompany;
    private Preferences preferences;
    private UserModel userModel;
    private boolean isFirstTime = true;
    private Singleton singleton;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", Locale.getDefault().getLanguage())));

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFirstTime)
        {
            isFirstTime = false;
        }else
            {
                if (singleton.isSentNewOrder())
                {
                    refreshFragmentOrders();
                    singleton.setSentNewOrder(false);
                }
            }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        initView();

        if (savedInstanceState == null) {
            DisplayFragmentHome();
            DisplayFragmentMain();
        }

    }

    private void initView() {
        singleton = Singleton.newInstance();
        Paper.init(this);
        fragmentManager = this.getSupportFragmentManager();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        String lastVisit = preferences.getLastVisit(this);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
        String now = dateFormat.format(new Date(Calendar.getInstance().getTimeInMillis()));

        if (!lastVisit.equals(now))
        {
            updateVisit(now);

        }
    }

    private void updateVisit(String now) {
        Api.getService(Tags.base_url)
                .updateVisit(now,2)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful())
                        {
                            preferences.setLastVisit(HomeActivity.this,now);
                        }else
                            {
                                try {
                                    Log.e("errorVisitCode",response.code()+"_"+response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        try {
                            Log.e("Error",t.getMessage()+"_");
                        }catch (Exception e){}
                    }
                });
    }

    public void DisplayFragmentHome() {
        fragment_count += 1;
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
        if (fragment_more != null && fragment_more.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_more).commit();
        }
        if (fragment_orders != null && fragment_orders.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_orders).commit();
        }
        if (fragment_notidications != null && fragment_notidications.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_notidications).commit();
        }
        if (fragment_main.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_main).commit();

        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_home_container, fragment_main, "fragment_main").addToBackStack("fragment_main").commit();

        }
        if (fragment_home != null && fragment_home.isAdded()) {
            fragment_home.updateBottomNavigationPosition(0);
        }

    }
    public void DisplayFragmentOrders()
    {

        if (fragment_orders == null) {
            fragment_orders = Fragment_Orders.newInstance();
        }
        if (fragment_main != null && fragment_main.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_main).commit();
        }
        if (fragment_more != null && fragment_more.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_more).commit();
        }
        if (fragment_notidications != null && fragment_notidications.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_notidications).commit();
        }
        if (fragment_orders.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_orders).commit();

        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_home_container, fragment_orders, "fragment_orders").addToBackStack("fragment_orders").commit();

        }
        if (fragment_home != null && fragment_home.isAdded()) {
            fragment_home.updateBottomNavigationPosition(2);
        }
    }
    public void DisplayFragmentMore() {

        if (fragment_more == null) {
            fragment_more = Fragment_More.newInstance();
        }
        if (fragment_main != null && fragment_main.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_main).commit();
        }
        if (fragment_orders != null && fragment_orders.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_orders).commit();
        }
        if (fragment_notidications != null && fragment_notidications.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_notidications).commit();
        }
        if (fragment_more.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_more).commit();

        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_home_container, fragment_more, "fragment_more").addToBackStack("fragment_more").commit();

        }
        if (fragment_home != null && fragment_home.isAdded()) {
            fragment_home.updateBottomNavigationPosition(3);
        }
    }

    public void DisplayFragmentContactUs() {
        fragment_count += 1;
        fragment_contact_us = Fragment_Contact_Us.newInstance();

        if (fragment_contact_us.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_contact_us).commit();

        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_contact_us, "fragment_contact_us").addToBackStack("fragment_contact_us").commit();

        }
    }

    public void DisplayFragmentBank() {
        fragment_count += 1;
        fragment_bank_account = Fragment_Bank_Account.newInstance();

        if (fragment_bank_account.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_bank_account).commit();

        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_bank_account, "fragment_bank_account").addToBackStack("fragment_bank_account").commit();

        }
    }

    public void DisplayFragmentMap(String from) {
        fragment_count += 1;
        fragment_map = Fragment_Map.newInstance(from);

        if (fragment_map.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_map).commit();

        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_map, "fragment_map").addToBackStack("fragment_map").commit();

        }
    }

    public void DisplayFragmentEventDetails(EventDataModel.EventModel eventModel) {
        fragment_count += 1;

        fragment_event_details = Fragment_Event_Details.newInstance(eventModel);

        if (fragment_event_details.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_event_details).commit();

        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_event_details, "fragment_event_details").addToBackStack("fragment_event_details").commit();

        }
    }

    public void DisplayFragmentNotifications()
    {

        if (fragment_notidications == null) {
            fragment_notidications = Fragment_Notidications.newInstance();
        }
        if (fragment_main != null && fragment_main.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_main).commit();
        }

        if (fragment_orders != null && fragment_orders.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_orders).commit();
        }
        if (fragment_more != null && fragment_more.isAdded()) {
            fragmentManager.beginTransaction().hide(fragment_more).commit();
        }
        if (fragment_notidications.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_notidications).commit();

        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_home_container, fragment_notidications, "fragment_notidications").addToBackStack("fragment_notidications").commit();

        }
        if (fragment_home != null && fragment_home.isAdded()) {
            fragment_home.updateBottomNavigationPosition(1);
        }
    }

    public void DisplayFragmentClientProfile()
    {

        fragment_count += 1;
         fragment_client_profile= Fragment_Client_Profile.newInstance();

        if (fragment_client_profile.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_client_profile).commit();

        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_client_profile, "fragment_client_profile").addToBackStack("fragment_client_profile").commit();

        }
    }
    public void DisplayFragmentCompanyProfile()
    {

        fragment_count += 1;
        fragment_company_profile= Fragment_Company_Profile.newInstance();

        if (fragment_company_profile.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_company_profile).commit();

        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_company_profile, "fragment_company_profile").addToBackStack("fragment_company_profile").commit();

        }
    }
    public void DisplayFragmentUpgradeToCompany()
    {

        fragment_count += 1;
        fragmentUpgradeToCompany= FragmentUpgradeToCompany.newInstance();

        if (fragmentUpgradeToCompany.isAdded()) {
            fragmentManager.beginTransaction().show(fragmentUpgradeToCompany).commit();

        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragmentUpgradeToCompany, "fragmentUpgradeToCompany").addToBackStack("fragmentUpgradeToCompany").commit();

        }
    }
    private void refreshFragmentOrders()
    {
        if (fragment_orders!=null&&fragment_orders.isAdded())
        {
            fragment_orders.refreshFragmentOrder();
        }
    }
    private void refreshProfile(UserModel userModel)
    {
        this.userModel = userModel;
        preferences.create_update_userdata(this,userModel);
        if (userModel.getUser().getUser_type().equals(Tags.type_user))
        {
            if (fragment_client_profile!=null&&fragment_client_profile.isAdded())
            {
                try {
                    new Handler()
                            .postDelayed(() -> fragment_client_profile.updateUI(userModel),1000);
                }catch (Exception e){}

            }
        }else
            {
                if (fragment_company_profile!=null&&fragment_company_profile.isAdded())
                {
                    try {
                        new Handler()
                                .postDelayed(() -> fragment_company_profile.updateUI(userModel),1000);
                    }catch (Exception e){}

                }
            }
    }
    public void listenForMapLocation(String from,SelectedLocation selectedLocation)
    {
        Back();
        if (from.equals("fragmentUpgradeToCompany"))
        {
            if (fragmentUpgradeToCompany!=null&&fragmentUpgradeToCompany.isAdded())
            {
                fragmentUpgradeToCompany.setSelectedLocation(selectedLocation);
            }
        }
    }

    public void CreateNoSignAlertDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .create();

        DialogCustomBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_custom, null, false);

        binding.btnSignUp.setOnClickListener((v) -> {
            dialog.dismiss();

            NavigateToSignInActivity();
        });
        binding.btnSignIn.setOnClickListener((v) -> {
            dialog.dismiss();

            NavigateToSignInActivity();


        });

        binding.btnCancel.setOnClickListener((v) ->
                dialog.dismiss()

        );
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_congratulation_animation;
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_window_bg);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(binding.getRoot());
        dialog.show();
    }

    private void NavigateToSignInActivity() {
        Intent intent = new Intent(HomeActivity.this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragmentList = fragmentManager.getFragments();
        for (Fragment fragment : fragmentList) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragmentList = fragmentManager.getFragments();
        for (Fragment fragment : fragmentList) {
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void RefreshActivity(String lang)
    {
        Paper.book().write("lang",lang);
        Language.setNewLocale(this,lang);
        new Handler()
                .postDelayed(() -> {

                    Intent intent =  getIntent();
                    finish();
                    startActivity(intent);
                },1050);



    }
    public void logout()
    {
        if (userModel!=null)
        {
            userModel =null;
            preferences.clear(this);
            NavigateToSignInActivity();

        }else
            {
                NavigateToSignInActivity();
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

                    if (userModel != null) {
                        finish();

                    } else {
                        NavigateToSignInActivity();
                    }

                } else {
                    DisplayFragmentMain();
                }
            } else {
                DisplayFragmentHome();
            }
        }

    }


}
