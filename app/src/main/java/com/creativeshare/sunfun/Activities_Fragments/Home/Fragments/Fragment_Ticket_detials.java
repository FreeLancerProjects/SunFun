package com.creativeshare.sunfun.Activities_Fragments.Home.Fragments;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.creativeshare.sunfun.Activities_Fragments.Home.Activity.HomeActivity;
import com.creativeshare.sunfun.R;
import com.google.android.material.appbar.AppBarLayout;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class Fragment_Ticket_detials extends Fragment {
    private HomeActivity homeActivity;
    private String cuurent_language;
    private AppBarLayout app_bar;
    private CircleImageView circleImageView;
    private TextView tv_detilas;
    private Button bt_book;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket_detials, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        homeActivity = (HomeActivity) getActivity();
        Paper.init(homeActivity);
        cuurent_language = Paper.book().read("lang", Locale.getDefault().getLanguage());

        app_bar = view.findViewById(R.id.app_bar);
        circleImageView=view.findViewById(R.id.image);
        bt_book=view.findViewById(R.id.bt_book);
        app_bar.addOnOffsetChangedListener(new AppBarLayout.BaseOnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                int total_range = appBarLayout.getTotalScrollRange();
                if ((total_range+i)<70)
                {
                   bt_book.setVisibility(View.GONE);
                   circleImageView.setVisibility(View.GONE);
                }else
                {
                   bt_book.setVisibility(View.VISIBLE);
                   circleImageView.setVisibility(View.VISIBLE);

                }
            }
        });

    }

    public static Fragment_Ticket_detials newInstance() {
        return new Fragment_Ticket_detials();
    }
}
