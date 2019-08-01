package com.creativeshare.sunfun.activities_fragments.activity_create_event.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.activities_fragments.activity_create_event.activity.Create_Event_Activity;
import com.creativeshare.sunfun.databinding.FragmentEventDateTimeBinding;
import com.creativeshare.sunfun.models.UserModel;
import com.creativeshare.sunfun.preferences.Preferences;
import com.creativeshare.sunfun.viewmodel.add_event_view_model.AddEventViewModel;
import com.creativeshare.sunfun.viewmodel.add_event_view_model.ViewModelEventDateTime;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_Event_Date_Time extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    private Create_Event_Activity activity;
    private String current_language;
    private FragmentEventDateTimeBinding binding;
    private TimePickerDialog timePickerDialog;
    private DatePickerDialog datePickerDialog;
    private int selected_type;
    private ViewModelEventDateTime viewModelEventDateTime;
    private UserModel userModel;
    private Preferences preferences;



    public static Fragment_Event_Date_Time newInstance() {
        return new Fragment_Event_Date_Time();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_event_date_time,container,false);
        viewModelEventDateTime = ViewModelProviders.of(this).get(ViewModelEventDateTime.class);
        binding.setViewModel(viewModelEventDateTime);
        initView();
        return binding.getRoot();

    }

    private void initView() {
        activity = (Create_Event_Activity) getActivity();
        Paper.init(activity);
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        viewModelEventDateTime.setContext(activity);
        current_language = Paper.book().read("lang", Locale.getDefault().getLanguage());
        createDatePickerDialog();
        createTimePickerDialog();

        binding.llStartdate.setOnClickListener(view -> {
            selected_type = 1;
            datePickerDialog.show(activity.getFragmentManager(),"");

        });

        binding.llStartTime.setOnClickListener(view -> {
            selected_type = 2;
            timePickerDialog.show(activity.getFragmentManager(),"");

        });

        binding.llEndDate.setOnClickListener(view -> {
            selected_type = 3;
            datePickerDialog.show(activity.getFragmentManager(),"");

        });

        binding.llEndTime.setOnClickListener(view -> {
            selected_type = 4;
            timePickerDialog.show(activity.getFragmentManager(),"");

        });


    }

    private void createDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(this,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.dismissOnPause(true);
        datePickerDialog.setAccentColor(ActivityCompat.getColor(activity,R.color.colorPrimary));
        datePickerDialog.setCancelColor(ActivityCompat.getColor(activity,R.color.gray4));
        datePickerDialog.setOkColor(ActivityCompat.getColor(activity,R.color.colorPrimary));
        datePickerDialog.setOkText(getString(R.string.select));
        datePickerDialog.setCancelText(getString(R.string.cancel));
        datePickerDialog.setLocale(new Locale(current_language));
        datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);
        datePickerDialog.setMinDate(calendar);


    }

    private void createTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY,5);
        timePickerDialog = TimePickerDialog.newInstance(this,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false);
        timePickerDialog.dismissOnPause(true);
        timePickerDialog.setAccentColor(ActivityCompat.getColor(activity,R.color.colorPrimary));
        timePickerDialog.setCancelColor(ActivityCompat.getColor(activity,R.color.gray4));
        timePickerDialog.setOkColor(ActivityCompat.getColor(activity,R.color.colorPrimary));
        timePickerDialog.setOkText(getString(R.string.select));
        timePickerDialog.setCancelText(getString(R.string.cancel));
        timePickerDialog.setLocale(new Locale(current_language));
        timePickerDialog.setVersion(TimePickerDialog.Version.VERSION_2);
        timePickerDialog.setMinTime(calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),calendar.get(Calendar.SECOND));


    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
        String t = dateFormat.format(new Date(calendar.getTimeInMillis()));

        if (selected_type==1)
        {
            binding.tvStartDate.setText(t);
            viewModelEventDateTime.start_date.set(calendar.getTimeInMillis());
        }else if (selected_type ==3)
        {
            binding.tvEndDate.setText(t);
            viewModelEventDateTime.end_date.set(calendar.getTimeInMillis());


        }


    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,second);


        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa",Locale.ENGLISH);
        String t = dateFormat.format(new Date(calendar.getTimeInMillis()));

        if (selected_type==2)
        {
            binding.tvStartTime.setText(t);
            viewModelEventDateTime.start_time.set(calendar.getTimeInMillis());
        }else if (selected_type ==4)
        {
            binding.tvEndTime.setText(t);
            viewModelEventDateTime.end_time.set(calendar.getTimeInMillis());

        }

    }


    public void CheckData(AddEventViewModel addEventViewModel) {

        viewModelEventDateTime.checkEventDateTimeData(addEventViewModel,userModel.getUser().getId());
    }
}
