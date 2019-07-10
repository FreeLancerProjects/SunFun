package com.creativeshare.sunfun.Activities_Fragments.activity_create_event.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.creativeshare.sunfun.Activities_Fragments.activity_create_event.fragments.Fragment_Add_Activity;
import com.creativeshare.sunfun.Activities_Fragments.activity_create_event.fragments.Fragment_Detials_About_The_Place;
import com.creativeshare.sunfun.Activities_Fragments.activity_create_event.fragments.Fragment_Detials_Of_The_Place_OF_The_Event;
import com.creativeshare.sunfun.Activities_Fragments.activity_create_event.fragments.Fragment_Event_Date_Time;
import com.creativeshare.sunfun.Adapter.PageAdapter;
import com.creativeshare.sunfun.Language.Language;
import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.preferences.Preferences;
import com.xw.repo.BubbleSeekBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class Create_Event_Activity extends AppCompatActivity {
    private List<Fragment> fragmentList;
    private ImageView back_arrow;
    private PageAdapter pageAdapter;
    private ViewPager viewPager;
    private BubbleSeekBar seekBar;
    private Button bt_next, bt_previous;
    private Fragment_Detials_About_The_Place fragment_detials_about_the_place;
    private Fragment_Detials_Of_The_Place_OF_The_Event fragment_detials_of_the_place_of_the_event;
    private Fragment_Event_Date_Time fragment_event_date_time;
    private Fragment_Add_Activity fragment_add_activity;
    private String current_language;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(Language.updateResources(newBase,  Preferences.getInstance().getLanguage(newBase)));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        intitview();

    }

    private void intitview() {
        Paper.init(this);
        current_language = Paper.book().read("lang", Locale.getDefault().getLanguage());
        fragmentList = new ArrayList<>();
        back_arrow = findViewById(R.id.arrow);
        viewPager = findViewById(R.id.pager);
        seekBar = findViewById(R.id.seekBar);
        bt_next = findViewById(R.id.bt_next);
        bt_previous = findViewById(R.id.bt_previous);
        if (current_language.equals("en")) {
            back_arrow.setRotation(180.0f);
        } else {
            seekBar.setRotation(180.0f);
        }
        bt_previous.setVisibility(View.GONE);
        pageAdapter = new PageAdapter(getSupportFragmentManager());
        intitfragmentspage();
        pageAdapter.addfragments(fragmentList);
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                int progress = position * 34;
                if (progress > 100) {
                    seekBar.setProgress(100);
                } else {
                    seekBar.setProgress(progress);
                }
                if (position == 0) {
                    bt_previous.setVisibility(View.GONE);
                    bt_next.setVisibility(View.VISIBLE);
                } else if (position == viewPager.getAdapter().getCount() - 1) {
                    bt_previous.setVisibility(View.VISIBLE);
                    bt_next.setVisibility(View.GONE);
                } else {
                    bt_previous.setVisibility(View.VISIBLE);
                    bt_next.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int progress = viewPager.getCurrentItem() * 34;
                if (progress > 100) {
                    seekBar.setProgress(100);
                } else {
                    seekBar.setProgress(progress);
                }
                switch (viewPager.getCurrentItem()) {
                    case 0:

                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);


                        break;

                    case 1:
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);

                        break;

                    case 2:
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);

                        break;


                }


            }
        });

        bt_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int progress = viewPager.getCurrentItem() * 34;
                seekBar.setProgress(progress);
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });
    }

    private void intitfragmentspage() {
        fragment_detials_about_the_place = Fragment_Detials_About_The_Place.newInstance();
        fragment_detials_of_the_place_of_the_event = Fragment_Detials_Of_The_Place_OF_The_Event.newInstance();
        fragment_event_date_time = Fragment_Event_Date_Time.newInstance();
        fragment_add_activity = Fragment_Add_Activity.newInstance();
        fragmentList.add(fragment_detials_about_the_place);
        fragmentList.add(fragment_detials_of_the_place_of_the_event);
        fragmentList.add(fragment_event_date_time);
        fragmentList.add(fragment_add_activity);
    }

}
