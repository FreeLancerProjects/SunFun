package com.creativeshare.sunfun.Home.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.creativeshare.sunfun.Home.Activity.HomeActivity;
import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.Sign_in.Activities.SignInActivity;

public class Fragment_More extends Fragment {
    private HomeActivity homeActivity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {


    }

    public static Fragment_More newInstance()
    {
        return new Fragment_More();
    }
}
