package com.creativeshare.sunfun.activities_fragments.activity_sign_in.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.activities_fragments.activity_home.activity.HomeActivity;
import com.creativeshare.sunfun.activities_fragments.activity_sign_in.activities.SignInActivity;
import com.creativeshare.sunfun.databinding.FragmentSignUpBinding;
import com.creativeshare.sunfun.preferences.Preferences;
import com.creativeshare.sunfun.viewmodel.signup_view_model.SignUp_View_Model;
import com.mukesh.countrypicker.Country;
import com.mukesh.countrypicker.CountryPicker;
import com.mukesh.countrypicker.listeners.OnCountryPickerListener;

import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_Sign_Up extends Fragment implements OnCountryPickerListener {
    private SignInActivity activity;
    private String current_language;
    private FragmentSignUpBinding binding;
    private SignUp_View_Model signUp_view_model;
    private CountryPicker picker;
    private Preferences preferences;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);
        signUp_view_model = ViewModelProviders.of(this).get(SignUp_View_Model.class);
        binding.setSignUpViewModel(signUp_view_model);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (SignInActivity) getActivity();
        signUp_view_model.setContext(activity);
        Paper.init(activity);
        preferences = Preferences.getInstance();
        current_language = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(current_language);

        signUp_view_model.userModelMutableLiveData.observe(this, userModel -> {
            userModel.getUser().setUser_type("1");
            preferences.create_update_userdata(activity,userModel);
            Intent intent = new Intent(activity, HomeActivity.class);
            startActivity(intent);
            activity.finish();
        });
        CreateCountryDialog();

        binding.llBack.setOnClickListener((v) ->
                activity.Back()
        );
        binding.imagePhoneCode.setOnClickListener((v)->picker.showDialog(activity));



    }

    public static Fragment_Sign_Up newInstance() {
        return new Fragment_Sign_Up();
    }


    private void CreateCountryDialog() {
        CountryPicker.Builder builder = new CountryPicker.Builder()
                .canSearch(true)
                .with(activity)
                .listener(this);
        picker = builder.build();

        TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);


        if (picker.getCountryFromSIM() != null) {
            updateUi(picker.getCountryFromSIM());

        } else if (telephonyManager != null && picker.getCountryByISO(telephonyManager.getNetworkCountryIso()) != null)
        {
            updateUi(picker.getCountryByISO(telephonyManager.getNetworkCountryIso()));


        } else if (picker.getCountryByLocale(Locale.getDefault()) != null) {
            updateUi(picker.getCountryByLocale(Locale.getDefault()));

        }else
        {
            signUp_view_model.phone_code.set("+966");


        }


    }
    @Override
    public void onSelectCountry(Country country) {
        updateUi(country);
    }

    private void updateUi(Country country) {
        signUp_view_model.phone_code.set(country.getDialCode());
    }
}
