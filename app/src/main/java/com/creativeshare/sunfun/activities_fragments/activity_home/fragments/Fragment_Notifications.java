package com.creativeshare.sunfun.activities_fragments.activity_home.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.activities_fragments.activity_company_action.CompanyActionActivity;
import com.creativeshare.sunfun.activities_fragments.activity_home.activity.HomeActivity;
import com.creativeshare.sunfun.adapter.MyNotificationAdapter;
import com.creativeshare.sunfun.databinding.FragmentCurrentPreviousOrderBinding;
import com.creativeshare.sunfun.models.NotificationDataModel;
import com.creativeshare.sunfun.models.UserModel;
import com.creativeshare.sunfun.preferences.Preferences;
import com.creativeshare.sunfun.viewmodel.my_notification_view_model.NotificationViewModel;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Notifications extends Fragment {
    private FragmentCurrentPreviousOrderBinding binding;
    private HomeActivity activity;
    private LinearLayoutManager manager;
    private MyNotificationAdapter adapter;
    private List<NotificationDataModel.NotificationModel> notificationModelList;
    private Preferences preferences;
    private UserModel userModel;
    private boolean isLoading = false;
    private NotificationViewModel notificationViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_current_previous_order,container,false);
        notificationViewModel = ViewModelProviders.of(this).get(NotificationViewModel.class);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        notificationModelList = new ArrayList<>();
        activity = (HomeActivity) getActivity();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        notificationViewModel.setContext(activity);
        binding.swipeRefresh.setColorSchemeColors(ContextCompat.getColor(activity, R.color.colorPrimary), ContextCompat.getColor(activity, R.color.black), ContextCompat.getColor(activity, R.color.delete), ContextCompat.getColor(activity, R.color.golden));
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        manager = new LinearLayoutManager(activity);
        binding.recView.setLayoutManager(manager);
        adapter = new MyNotificationAdapter(notificationModelList, activity,this);
        binding.recView.setAdapter(adapter);

        getNotification();
        notificationViewModel.data.observe(this, notificationModelList -> {
            binding.progBar.setVisibility(View.GONE);
            binding.swipeRefresh.setRefreshing(false);
            if (notificationModelList.size() > 0) {
                binding.tvNoOrders.setVisibility(View.GONE);
            } else {
                binding.tvNoOrders.setText(R.string.no_not);
                binding.tvNoOrders.setVisibility(View.VISIBLE);


            }
            Fragment_Notifications.this.notificationModelList.clear();
            Fragment_Notifications.this.notificationModelList.addAll(notificationModelList);

            adapter.notifyDataSetChanged();

        });

        binding.recView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int totalItems = adapter.getItemCount();
                    int lastVisiblePos = manager.findLastCompletelyVisibleItemPosition();

                    if (totalItems > 10 && (totalItems - lastVisiblePos) == 3 && !isLoading) {
                        isLoading = true;
                        notificationModelList.add(null);
                        adapter.notifyItemInserted(notificationModelList.size() - 1);
                        notificationViewModel.loadMore(userModel.getUser().getId());
                    }

                }
            }

        });

        notificationViewModel.dataLoadMore.observe(this, notificationModelList -> {
            isLoading = false;
            Fragment_Notifications.this.notificationModelList.remove(Fragment_Notifications.this.notificationModelList.size()-1);
            adapter.notifyItemRemoved(Fragment_Notifications.this.notificationModelList.size()-1);
            Fragment_Notifications.this.notificationModelList.addAll(notificationModelList);
            adapter.notifyDataSetChanged();
        });

        notificationViewModel.error.observe(this, aBoolean -> {
            binding.swipeRefresh.setRefreshing(false);
            binding.progBar.setVisibility(View.GONE);

        });

        notificationViewModel.errorLoadMore.observe(this, aBoolean -> {
            isLoading = false;
            if (notificationModelList.get(notificationModelList.size() - 1) == null) {
                notificationModelList.remove(notificationModelList.size() - 1);
                adapter.notifyItemRemoved(notificationModelList.size() - 1);
            }


        });

        binding.swipeRefresh.setOnRefreshListener(() -> notificationViewModel.getNotification(userModel.getUser().getId()));

    }

    public void getNotification() {
        notificationViewModel.getNotification(userModel.getUser().getId());

    }
    public static Fragment_Notifications newInstance()
    {
        return new Fragment_Notifications();
    }

    public void setItemData(NotificationDataModel.NotificationModel notificationModel) {
        Intent intent = new Intent(activity, CompanyActionActivity.class);
        intent.putExtra("data",notificationModel);
        startActivityForResult(intent,333);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 333&&resultCode== Activity.RESULT_OK&&data!=null)
        {
            if (data.hasExtra("action"))
            {
                int response = data.getExtras().getInt("action");

                if (response == 1)
                {
                    activity.refreshFragmentOrders();
                    getNotification();
                }else if (response == 2){
                    activity.getUserData();
                    getNotification();
                }
            }

        }
    }
}
