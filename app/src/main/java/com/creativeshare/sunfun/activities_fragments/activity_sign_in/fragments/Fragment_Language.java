package com.creativeshare.sunfun.activities_fragments.activity_sign_in.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.activities_fragments.activity_sign_in.activities.SignInActivity;
import com.creativeshare.sunfun.databinding.FragmentLanguageBinding;

import io.paperdb.Paper;

public class Fragment_Language extends Fragment {
    private String selected_language = "ar";
    private SignInActivity activity;
    private FragmentLanguageBinding binding;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_language,container,false);
        initView();
        return binding.getRoot();
    }

    public static Fragment_Language newInstance()
    {
        return new Fragment_Language();
    }
    private void initView() {
        activity = (SignInActivity) getActivity();
        Paper.init(activity);

        binding.rbAr.setOnClickListener((v)->
                selected_language = "ar"
                );

        binding.rbEn.setOnClickListener((v)->
                selected_language = "en"
        );
        binding.fab.setOnClickListener((v)->
                activity.RefreshActivity(selected_language)

        );
    }
}
