package com.creativeshare.sunfun.Activities_Fragments.activity_home.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.creativeshare.sunfun.Activities_Fragments.activity_home.activity.HomeActivity;
import com.creativeshare.sunfun.R;

import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_More extends Fragment {
    private HomeActivity homeActivity;
    private String cuurent_language;
    private LinearLayout ll_contact_us;
    private ImageView arrow_contact_us;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        homeActivity = (HomeActivity) getActivity();
        Paper.init(homeActivity);
        cuurent_language = Paper.book().read("lang", Locale.getDefault().getLanguage());
        ll_contact_us = view.findViewById(R.id.ll_contact_us);
        arrow_contact_us = view.findViewById(R.id.arrow_contact_us);
        if (cuurent_language.equals("ar")) {
            arrow_contact_us.setRotation(180.0f);
        }
        ll_contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeActivity.DisplayFragmentTicket_detilas();
            }
        });

    }

    public static Fragment_More newInstance() {
        return new Fragment_More();
    }
}
