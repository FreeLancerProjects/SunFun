package com.creativeshare.sunfun.activities_fragments.activity_app_info;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.databinding.ActivityAppInfoBinding;
import com.creativeshare.sunfun.language.Language;
import com.creativeshare.sunfun.viewmodel.app_data_view_model.AppViewModel;

import java.util.Locale;

import io.paperdb.Paper;

public class AppInfoActivity extends AppCompatActivity {

    private AppViewModel appViewModel;
    private ActivityAppInfoBinding binding;
    private String current_lang;
    private int type=1;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", Locale.getDefault().getLanguage())));

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_app_info);
        appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        getDataFromIntent();
        initView();

    }



    private void initView() {
        Paper.init(this);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(current_lang);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        appViewModel.setContext(this);
        appViewModel.getAppData();

        appViewModel.data.observe(this, appDataList -> {
            binding.progBar.setVisibility(View.GONE);
            if (type==1)
            {
                binding.setAppData(appDataList.get(0));
            }else
                {
                    binding.setAppData(appDataList.get(1));

                }
        });
        appViewModel.error.observe(this, aBoolean -> binding.progBar.setVisibility(View.GONE));
        binding.setType(type);



    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent!=null)
        {
            type = intent.getIntExtra("type",1);
        }

    }
}
