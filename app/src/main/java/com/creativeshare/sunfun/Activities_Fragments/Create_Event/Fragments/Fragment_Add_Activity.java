package com.creativeshare.sunfun.Activities_Fragments.Create_Event.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.creativeshare.sunfun.Activities_Fragments.Create_Event.Activity.Create_Event_Activity;
import com.creativeshare.sunfun.R;

import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_Add_Activity extends Fragment {
    private Create_Event_Activity create_event_activity;
private String cuurent_language;
private CheckBox checkBox_yes,checkBox_no;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_activity,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
       create_event_activity=(Create_Event_Activity) getActivity();
        Paper.init(create_event_activity);
        cuurent_language = Paper.book().read("lang", Locale.getDefault().getLanguage());
checkBox_yes=view.findViewById(R.id.chec_yes);
checkBox_no=view.findViewById(R.id.chec_no);
checkBox_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(checkBox_yes.isChecked()){
            checkBox_no.setChecked(false);
        }
        else {
            checkBox_no.setChecked(true);
        }
    }
});
        checkBox_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(checkBox_no.isChecked()){
                    checkBox_yes.setChecked(false);
                }
                else {
                    checkBox_yes.setChecked(true);
                }
            }
        });
    }

    public static Fragment_Add_Activity newInstance()
    {
        return new Fragment_Add_Activity();
    }
}
