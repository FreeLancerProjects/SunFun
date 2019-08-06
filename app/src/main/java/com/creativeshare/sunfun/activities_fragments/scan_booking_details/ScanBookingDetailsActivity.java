package com.creativeshare.sunfun.activities_fragments.scan_booking_details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.adapter.BookingActivitiesAdapter;
import com.creativeshare.sunfun.databinding.ActivityScanBookingDetailsBinding;
import com.creativeshare.sunfun.language.Language;
import com.creativeshare.sunfun.models.BookingScanData;

import java.util.Locale;

import io.paperdb.Paper;

public class ScanBookingDetailsActivity extends AppCompatActivity {
    private ActivityScanBookingDetailsBinding binding;
    private LinearLayoutManager manager;
    private BookingActivitiesAdapter adapter;
    private String lang;
    private BookingScanData bookingScanData;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", Locale.getDefault().getLanguage())));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_scan_booking_details);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent!=null)
        {
            bookingScanData = (BookingScanData) intent.getSerializableExtra("data");
        }

    }

    private void initView() {
        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(lang);
        binding.setBookingModel(bookingScanData);
        manager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false);
        binding.recView.setLayoutManager(manager);
        adapter = new BookingActivitiesAdapter(bookingScanData.getBooking_details(),this);
        binding.recView.setAdapter(adapter);
        binding.consBack.setOnClickListener(view -> finish());



    }
}
