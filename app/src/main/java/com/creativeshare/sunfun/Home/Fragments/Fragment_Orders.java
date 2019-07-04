package com.creativeshare.sunfun.Home.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.creativeshare.sunfun.Home.Activity.HomeActivity;
import com.creativeshare.sunfun.R;

public class Fragment_Orders extends Fragment {
    private HomeActivity homeActivity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {


    }

    public static Fragment_Orders newInstance()
    {
        return new Fragment_Orders();
    }
}
