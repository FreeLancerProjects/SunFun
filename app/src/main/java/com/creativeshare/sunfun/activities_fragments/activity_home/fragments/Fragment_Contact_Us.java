package com.creativeshare.sunfun.activities_fragments.activity_home.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.creativeshare.sunfun.activities_fragments.activity_home.activity.HomeActivity;
import com.creativeshare.sunfun.R;

import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_Contact_Us extends Fragment {
    private HomeActivity homeActivity;
private String cuurent_language;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_us,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        homeActivity=(HomeActivity)getActivity();
        Paper.init(homeActivity);
        cuurent_language = Paper.book().read("lang", Locale.getDefault().getLanguage());

    }

    public static Fragment_Contact_Us newInstance()
    {
        return new Fragment_Contact_Us();
    }
}
