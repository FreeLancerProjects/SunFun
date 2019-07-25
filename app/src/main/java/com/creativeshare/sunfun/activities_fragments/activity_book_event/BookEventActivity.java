package com.creativeshare.sunfun.activities_fragments.activity_book_event;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.adapter.PaymentMethodAdapter;
import com.creativeshare.sunfun.databinding.ActivityBookEventBinding;
import com.creativeshare.sunfun.language.Language;
import com.creativeshare.sunfun.models.EventDataModel;
import com.creativeshare.sunfun.models.PaymentDataModel;
import com.creativeshare.sunfun.models.UserModel;
import com.creativeshare.sunfun.preferences.Preferences;
import com.creativeshare.sunfun.viewmodel.book_event_view_model.BookEventViewModel;
import com.creativeshare.sunfun.viewmodel.payment_view_model.PaymentViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class BookEventActivity extends AppCompatActivity {
    private ActivityBookEventBinding binding;
    private List<PaymentDataModel.PaymentModel> paymentModelList;
    private PaymentMethodAdapter adapter;
    private LinearLayoutManager manager;
    private Preferences preferences;
    private UserModel userModel;
    private String current_lang;
    private EventDataModel.EventModel eventModel;
    private PaymentViewModel paymentViewModel;
    private BookEventViewModel bookEventViewModel;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", Locale.getDefault().getLanguage())));

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_book_event);
        paymentViewModel = ViewModelProviders.of(this).get(PaymentViewModel.class);
        paymentViewModel.setContext(this);
        bookEventViewModel = ViewModelProviders.of(this).get(BookEventViewModel.class);
        bookEventViewModel.setContext(this);
        initView();
        getDataFromIntent();


    }



    private void initView() {
        paymentModelList = new ArrayList<>();
        Paper.init(this);
        current_lang = Paper.book().read("lang",Locale.getDefault().getLanguage());
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);

        binding.setLang(current_lang);
        binding.setUserModel(userModel);
        manager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        binding.recView.setLayoutManager(manager);
        adapter = new PaymentMethodAdapter(paymentModelList,this);
        binding.recView.setAdapter(adapter);

        paymentViewModel.data.observe(this, paymentModelList -> {
            BookEventActivity.this.paymentModelList.clear();
            BookEventActivity.this.paymentModelList.addAll(paymentModelList);
            adapter.notifyDataSetChanged();

        });

        paymentViewModel.getPaymentMethod();


    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent!=null)
        {
            eventModel = (EventDataModel.EventModel) intent.getSerializableExtra("data");
            binding.setEventModel(eventModel);
        }
    }

    public void setItemSelected(PaymentDataModel.PaymentModel paymentModel) {
        bookEventViewModel.payment.set(String.valueOf(paymentModel.getId()));
    }
}
