package com.creativeshare.sunfun.activities_fragments.activity_create_event.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.activities_fragments.activity_create_event.fragments.Fragment_Address_Event;
import com.creativeshare.sunfun.activities_fragments.activity_create_event.fragments.Fragment_Details_About_Event;
import com.creativeshare.sunfun.activities_fragments.activity_create_event.fragments.Fragment_Event_Date_Time;
import com.creativeshare.sunfun.activities_fragments.add_activity.ActivityAddActivity;
import com.creativeshare.sunfun.databinding.ActivityCreateEventBinding;
import com.creativeshare.sunfun.language.Language;
import com.creativeshare.sunfun.models.UserModel;
import com.creativeshare.sunfun.preferences.Preferences;
import com.creativeshare.sunfun.singleton.Singleton;
import com.creativeshare.sunfun.viewmodel.add_event_view_model.AddEventViewModel;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class Create_Event_Activity extends AppCompatActivity {
    private Fragment_Details_About_Event fragment_details_about_event;
    private Fragment_Address_Event fragment_address_event;
    private Fragment_Event_Date_Time fragment_event_date_time;
    private String current_language;
    private int fragment_count = 0;
    private ActivityCreateEventBinding binding;
    private FragmentManager fragmentManager;
    private AddEventViewModel addEventViewModel;
    private Preferences preferences;
    private UserModel userModel;
    private Singleton singleton;



    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(Language.updateResources(newBase,  Preferences.getInstance().getLanguage(newBase)));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_create_event);
        addEventViewModel = ViewModelProviders.of(this).get(AddEventViewModel.class);
        initView();
        if (savedInstanceState==null)
        {
            fragmentManager = getSupportFragmentManager();
            DisplayDetailsAboutEvent();
        }


    }

    private void initView() {
        singleton = Singleton.newInstance();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        Paper.init(this);
        current_language = Paper.book().read("lang", Locale.getDefault().getLanguage());
        addEventViewModel.setContext(this);
        binding.setViewModel(addEventViewModel);
        binding.setUserModel(userModel);
        binding.setLang(current_language);
        binding.seekBar.setEnabled(false);

        addEventViewModel.step1.observe(this, aBoolean -> {
            binding.btnNext.setText(R.string.next);
            binding.seekBar.setProgress(50.0f);
            binding.btnPrevious.setVisibility(View.VISIBLE);
            DisplayAddressEvent();

        });

        addEventViewModel.success.observe(this, eventIdModel -> {
            singleton.setEventAdded(true);
            Intent intent = new Intent(Create_Event_Activity.this, ActivityAddActivity.class);
            intent.putExtra("data",eventIdModel);
            startActivity(intent);
            finish();
        });
        addEventViewModel.step2.observe(this, aBoolean -> {

            binding.seekBar.setProgress(100.0f);
            binding.btnPrevious.setVisibility(View.VISIBLE);
            binding.btnPrevious.setVisibility(View.VISIBLE);
            binding.btnNext.setText(R.string.done);
            DisplayDateTimeEvent();


        });


        binding.btnNext.setOnClickListener(view ->
        {
            if (fragment_count==1)
            {
                if (fragment_details_about_event!=null&&fragment_details_about_event.isAdded())
                {
                    fragment_details_about_event.CheckData(addEventViewModel);
                }
            }else if (fragment_count==2)
            {
                if (fragment_address_event!=null&&fragment_address_event.isAdded())
                {
                    fragment_address_event.CheckData(addEventViewModel);
                }
            }
            else if (fragment_count==3)
            {
                if (fragment_event_date_time!=null&&fragment_event_date_time.isAdded())
                {
                    fragment_event_date_time.CheckData(addEventViewModel);
                }
            }
        });


        binding.btnPrevious.setOnClickListener(view ->

            back()


        );

        binding.arrow.setOnClickListener(view -> finish());


    }

    public void DisplayDetailsAboutEvent()
    {
        fragment_count+=1;
        addEventViewModel.pos=fragment_count;

        if (fragment_details_about_event==null)
        {
            fragment_details_about_event = Fragment_Details_About_Event.newInstance();
        }

        if (fragment_details_about_event.isAdded())
        {
            fragmentManager.beginTransaction().show(fragment_details_about_event).commit();
        }else
            {
                fragmentManager.beginTransaction().add(R.id.fragment_child_container,fragment_details_about_event,"fragment_details_about_event").addToBackStack("fragment_details_about_event").commit();
            }

    }

    public void DisplayAddressEvent()
    {
        fragment_count+=1;
        addEventViewModel.pos=fragment_count;
        fragment_address_event = Fragment_Address_Event.newInstance();

        if (fragment_address_event.isAdded())
        {
            fragmentManager.beginTransaction().show(fragment_address_event).commit();
        }else
        {
            fragmentManager.beginTransaction().add(R.id.fragment_child_container,fragment_address_event,"fragment_address_event").addToBackStack("fragment_address_event").commit();
        }

    }

    public void DisplayDateTimeEvent()
    {
        fragment_count+=1;
        addEventViewModel.pos=fragment_count;

        if (fragment_event_date_time==null)
        {
            fragment_event_date_time = Fragment_Event_Date_Time.newInstance();
        }

        if (fragment_event_date_time.isAdded())
        {
            fragmentManager.beginTransaction().show(fragment_event_date_time).commit();
        }else
        {
            fragmentManager.beginTransaction().add(R.id.fragment_child_container,fragment_event_date_time,"fragment_event_date_time").addToBackStack("fragment_event_date_time").commit();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragmentList = fragmentManager.getFragments();
        for (Fragment fragment : fragmentList) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragmentList = fragmentManager.getFragments();
        for (Fragment fragment : fragmentList) {
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    @Override
    public void onBackPressed() {
        back();
    }

    private void back() {



        if (fragment_count>1)
        {
            if (fragment_count==2)
            {
                binding.btnPrevious.setVisibility(View.GONE);
                binding.seekBar.setProgress(0.0f);
                binding.btnNext.setText(R.string.next);

            }else if (fragment_count==3)
            {

                binding.seekBar.setProgress(50.0f);
                binding.btnNext.setText(R.string.next);

            }
            fragment_count--;
            addEventViewModel.current_pos.set(fragment_count);

            super.onBackPressed();
        }else
        {
            finish();
        }
    }
}
