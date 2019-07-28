package com.creativeshare.sunfun.activities_fragments.activity_book_event;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.adapter.PaymentMethodAdapter;
import com.creativeshare.sunfun.adapter.SelectedActivitiesAdapter;
import com.creativeshare.sunfun.adapter.SpinnerActivityAdapter;
import com.creativeshare.sunfun.databinding.ActivityBookEventBinding;
import com.creativeshare.sunfun.databinding.DialogSubscribersBinding;
import com.creativeshare.sunfun.language.Language;
import com.creativeshare.sunfun.models.ActivityModelUpload;
import com.creativeshare.sunfun.models.EventDataModel;
import com.creativeshare.sunfun.models.PaymentDataModel;
import com.creativeshare.sunfun.models.UserModel;
import com.creativeshare.sunfun.preferences.Preferences;
import com.creativeshare.sunfun.share.Common;
import com.creativeshare.sunfun.singleton.Singleton;
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
    private Singleton singleton;
    private LinearLayoutManager manager2;
    private SpinnerActivityAdapter spinnerActivityAdapter;
    private List<EventDataModel.EventModel.ActivityModel> activityModelList;
    private SelectedActivitiesAdapter selectedActivitiesAdapter;
    private List<ActivityModelUpload> activityModelUploadList;


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
        getDataFromIntent();
        initView();


    }



    private void initView() {

        activityModelUploadList = new ArrayList<>();
        activityModelList = new ArrayList<>();
        EventDataModel.EventModel.ActivityModel activityModel = new EventDataModel.EventModel.ActivityModel();
        activityModel.setAr_title("إختر");
        activityModel.setEn_title("Choose");
        activityModelList.add(activityModel);

        paymentModelList = new ArrayList<>();
        Paper.init(this);
        current_lang = Paper.book().read("lang",Locale.getDefault().getLanguage());
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        binding.setEventModel(eventModel);

        binding.setLang(current_lang);
        binding.setUserModel(userModel);
        binding.setBookViewModel(bookEventViewModel);
        manager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        binding.recView.setLayoutManager(manager);
        adapter = new PaymentMethodAdapter(paymentModelList,this);
        binding.recView.setAdapter(adapter);

        //////////////////////////////////////////////////////////////
        activityModelList.addAll(eventModel.getActivities());
        spinnerActivityAdapter =new SpinnerActivityAdapter(activityModelList,this);
        binding.spinner.setAdapter(spinnerActivityAdapter);

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0)
                {
                    EventDataModel.EventModel.ActivityModel model = activityModelList.get(i);
                    if (!hasItem(model))
                    {
                        ActivityModelUpload activityModelUpload = new ActivityModelUpload(model.getId(),0,model.getAr_title(),model.getEn_title());
                        CreateSubscribersAlertDialog(activityModelUpload);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //////////////////////////////////////////////////////////////
        selectedActivitiesAdapter = new SelectedActivitiesAdapter(activityModelUploadList,this);
        manager2 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        binding.recViewActivities.setLayoutManager(manager2);
        binding.recViewActivities.setAdapter(selectedActivitiesAdapter);

        //////////////////////////////////////////////////////////////
        paymentViewModel.data.observe(this, paymentModelList -> {
            BookEventActivity.this.paymentModelList.clear();
            BookEventActivity.this.paymentModelList.addAll(paymentModelList);
            adapter.notifyDataSetChanged();

        });

        paymentViewModel.getPaymentMethod();

        bookEventViewModel.success.observe(this, aBoolean ->{
                    Toast.makeText(BookEventActivity.this,getString(R.string.suc), Toast.LENGTH_SHORT).show();
                    singleton = Singleton.newInstance();
                    singleton.setEventAdded(true);
                    finish();

                }

        );


    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent!=null)
        {
            eventModel = (EventDataModel.EventModel) intent.getSerializableExtra("data");
        }
    }

    public void setItemSelected(PaymentDataModel.PaymentModel paymentModel) {
        bookEventViewModel.payment.set(String.valueOf(paymentModel.getId()));
    }



    private boolean hasItem(EventDataModel.EventModel.ActivityModel activityModel)
    {
        for (ActivityModelUpload model:activityModelUploadList)
        {
            if (activityModel.getId()== model.getActivity_id())
            {
                return true;
            }
        }


        return false;
    }

    public void deleteItem(int adapterPosition) {
        activityModelUploadList.remove(adapterPosition);
        selectedActivitiesAdapter.notifyItemRemoved(adapterPosition);
        bookEventViewModel.activityList.set(activityModelUploadList);

    }


    public void CreateSubscribersAlertDialog(ActivityModelUpload activityModelUpload) {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .create();

        DialogSubscribersBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_subscribers, null, false);



        binding.btnOk.setOnClickListener((v) ->
                {
                    String subscriber = binding.edtSubscribers.getText().toString().trim();
                    if (!TextUtils.isEmpty(subscriber))
                    {
                        binding.edtSubscribers.setError(null);
                        Common.CloseKeyBoard(this,binding.edtSubscribers);
                        activityModelUpload.setSubscribers_num(Integer.parseInt(subscriber));
                        activityModelUploadList.add(activityModelUpload);
                        selectedActivitiesAdapter.notifyDataSetChanged();
                        bookEventViewModel.activityList.set(activityModelUploadList);
                        dialog.dismiss();

                    }else
                        {
                            binding.edtSubscribers.setError(getString(R.string.field_req));
                        }
                }

        );
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_congratulation_animation;
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_window_bg);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(binding.getRoot());
        dialog.show();
    }

}
