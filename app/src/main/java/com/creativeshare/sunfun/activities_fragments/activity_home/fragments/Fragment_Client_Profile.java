package com.creativeshare.sunfun.activities_fragments.activity_home.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.activities_fragments.activity_home.activity.HomeActivity;
import com.creativeshare.sunfun.databinding.FragmentProfileClientBinding;
import com.creativeshare.sunfun.models.UserModel;
import com.creativeshare.sunfun.preferences.Preferences;
import com.creativeshare.sunfun.tags.Tags;

import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_Client_Profile extends Fragment {
    private FragmentProfileClientBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private String current_lang;
    private HomeActivity activity;

    public static Fragment_Client_Profile newInstance()
    {
        return new Fragment_Client_Profile();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_client,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeActivity) getActivity();
        Paper.init(activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        binding.setLang(current_lang);
        binding.setUserModel(userModel);
        binding.consBack.setOnClickListener(view -> activity.Back());
        binding.consUpgrade.setOnClickListener(view -> {
            if (userModel.getUser().getUser_type()== Tags.type_company)
            {

            }else
            {

            }
        });
    }
}
