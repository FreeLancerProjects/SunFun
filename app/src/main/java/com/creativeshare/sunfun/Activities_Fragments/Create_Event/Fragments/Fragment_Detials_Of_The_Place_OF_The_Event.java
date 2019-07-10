package com.creativeshare.sunfun.Activities_Fragments.Create_Event.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.creativeshare.sunfun.Activities_Fragments.Create_Event.Activity.Create_Event_Activity;
import com.creativeshare.sunfun.R;

import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_Detials_Of_The_Place_OF_The_Event extends Fragment {
    private Create_Event_Activity create_event_activity;
private String cuurent_language;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_of_the_place_of_the_event,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
       create_event_activity=(Create_Event_Activity) getActivity();
        Paper.init(create_event_activity);
        cuurent_language = Paper.book().read("lang", Locale.getDefault().getLanguage());

    }

    public static Fragment_Detials_Of_The_Place_OF_The_Event newInstance()
    {
        return new Fragment_Detials_Of_The_Place_OF_The_Event();
    }
}
