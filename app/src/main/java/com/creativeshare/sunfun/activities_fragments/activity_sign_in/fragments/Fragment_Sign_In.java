package com.creativeshare.sunfun.activities_fragments.activity_sign_in.fragments;

import android.content.Intent;
import android.os.Bundle;
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
import com.creativeshare.sunfun.databinding.FragmentSignInBinding;
import com.creativeshare.sunfun.preferences.Preferences;
import com.creativeshare.sunfun.viewmodel.login_view_model.Login_View_Model;

import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_Sign_In extends Fragment {
    private FragmentSignInBinding binding;
    private SignInActivity activity;
    private String cuurent_language;
    private Preferences preferences;
    private Login_View_Model login_view_model;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false);
        View view = binding.getRoot();
        initView();
        return view;
    }

    private void initView() {
        activity = (SignInActivity) getActivity();
        preferences = Preferences.getInstance();
        Paper.init(activity);
        cuurent_language = Paper.book().read("lang", Locale.getDefault().getLanguage());

        login_view_model = ViewModelProviders.of(this).get(Login_View_Model.class);
        binding.setLoginViewModel(login_view_model);
        login_view_model.setContext(activity);

        login_view_model.userModelMutableLiveData.observe(this, userModel ->
                {
                    preferences.create_update_userdata(activity,userModel);
                    Intent intent = new Intent(activity,HomeActivity.class);
                    startActivity(intent);
                    activity.finish();


                }
        );


        binding.tvSkip.setOnClickListener((v) -> {
            Intent intent = new Intent(activity, HomeActivity.class);
            startActivity(intent);
        });

        binding.tvNewAccount.setOnClickListener((v) ->
            activity.DisplayFragmentSignUp()
        );


    }


    public static Fragment_Sign_In newInstance() {
        return new Fragment_Sign_In();
    }


}
