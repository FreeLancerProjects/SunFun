package com.creativeshare.sunfun.activities_fragments.activity_order_details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.adapter.OrderActivitiesAdapter;
import com.creativeshare.sunfun.databinding.ActivityOrderDetailsBinding;
import com.creativeshare.sunfun.language.Language;
import com.creativeshare.sunfun.models.OrderDataModel;

import java.util.Locale;

import io.paperdb.Paper;

public class OrderDetailsActivity extends AppCompatActivity {

    private ActivityOrderDetailsBinding binding;
    private String lang;
    private LinearLayoutManager manager;
    private OrderDataModel.OrderModel orderModel;
    private OrderActivitiesAdapter adapter;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", Locale.getDefault().getLanguage())));

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_order_details);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent!=null)
        {
            orderModel = (OrderDataModel.OrderModel) intent.getSerializableExtra("data");
        }
    }

    private void initView() {
        Paper.init(this);
        lang = Paper.book().read("lang",Locale.getDefault().getLanguage());
        binding.setLang(lang);
        binding.setOrderModel(orderModel);
        manager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false);
        binding.recView.setLayoutManager(manager);
        adapter = new OrderActivitiesAdapter(orderModel.getBooking_details(),this);
        binding.recView.setAdapter(adapter);

        binding.consBack.setOnClickListener(view -> finish());



    }
}
