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

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.activities_fragments.activity_app_info.AppInfoActivity;
import com.creativeshare.sunfun.activities_fragments.activity_home.activity.HomeActivity;
import com.creativeshare.sunfun.databinding.FragmentMoreBinding;

import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_More extends Fragment {
    private HomeActivity activity;
    private String current_language;
    private FragmentMoreBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_more,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeActivity) getActivity();
        Paper.init(activity);
        current_language = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(current_language);
        binding.llContact.setOnClickListener((view -> activity.DisplayFragmentContactUs()));
        binding.llBank.setOnClickListener((view -> activity.DisplayFragmentBank()));
        binding.llTerms.setOnClickListener(view -> NavigateToAppInfoActivity(2));
        binding.llAbout.setOnClickListener(view -> NavigateToAppInfoActivity(1));

    }

    private void NavigateToAppInfoActivity(int type)
    {
        Intent intent = new Intent(activity, AppInfoActivity.class);
        intent.putExtra("type",type);
        startActivity(intent);
    }
    public static Fragment_More newInstance() {
        return new Fragment_More();
    }
}
