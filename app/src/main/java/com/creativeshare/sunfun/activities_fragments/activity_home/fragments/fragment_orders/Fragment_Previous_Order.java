package com.creativeshare.sunfun.activities_fragments.activity_home.fragments.fragment_orders;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.activities_fragments.activity_home.activity.HomeActivity;
import com.creativeshare.sunfun.adapter.MyOrderAdapter;
import com.creativeshare.sunfun.databinding.FragmentCurrentPreviousOrderBinding;
import com.creativeshare.sunfun.models.OrderDataModel;
import com.creativeshare.sunfun.models.UserModel;
import com.creativeshare.sunfun.preferences.Preferences;
import com.creativeshare.sunfun.viewmodel.my_orders.MyOrderViewModel;

import java.util.ArrayList;
import java.util.List;


public class Fragment_Previous_Order extends Fragment {

    private FragmentCurrentPreviousOrderBinding binding;
    private HomeActivity activity;
    private LinearLayoutManager manager;
    private List<OrderDataModel.OrderModel> orderModelList;
    private MyOrderAdapter adapter;
    private MyOrderViewModel myOrderViewModel;
    private Preferences preferences;
    private UserModel userModel;
    private boolean isLoading = false;

    public static Fragment_Previous_Order newInstance() {

        return new Fragment_Previous_Order();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_current_previous_order,container,false);
        myOrderViewModel = ViewModelProviders.of(this).get(MyOrderViewModel.class);

        initView();
        return binding.getRoot();
    }

    private void initView() {
        orderModelList = new ArrayList<>();
        activity = (HomeActivity) getActivity();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        myOrderViewModel.setContext(activity);
        binding.swipeRefresh.setColorSchemeColors(ContextCompat.getColor(activity, R.color.colorPrimary), ContextCompat.getColor(activity, R.color.black), ContextCompat.getColor(activity, R.color.delete), ContextCompat.getColor(activity, R.color.golden));
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        manager = new LinearLayoutManager(activity);
        binding.recView.setLayoutManager(manager);
        adapter = new MyOrderAdapter(orderModelList, activity, this);
        binding.recView.setAdapter(adapter);

        getOrders();
        myOrderViewModel.dataPreviousOrder.observe(this, orderModelList -> {
            binding.swipeRefresh.setRefreshing(false);
            binding.progBar.setVisibility(View.GONE);

            if (orderModelList.size() > 0) {
                binding.tvNoOrders.setVisibility(View.GONE);
            } else {
                binding.tvNoOrders.setVisibility(View.VISIBLE);


            }
            Fragment_Previous_Order.this.orderModelList.clear();
            Fragment_Previous_Order.this.orderModelList.addAll(orderModelList);
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
                        orderModelList.add(null);
                        adapter.notifyItemInserted(orderModelList.size() - 1);
                        myOrderViewModel.loadMorePreviousOrder(userModel.getUser().getId());
                    }

                }
            }

        });

        myOrderViewModel.dataLoadMorePreviousOrder.observe(this, orderModelList -> {
            isLoading = false;
            Fragment_Previous_Order.this.orderModelList.addAll(orderModelList);
            adapter.notifyDataSetChanged();
        });

        myOrderViewModel.errorPreviousOrder.observe(this, aBoolean -> {
            binding.swipeRefresh.setRefreshing(false);
            binding.progBar.setVisibility(View.GONE);

        });

        myOrderViewModel.errorLoadMorePreviousOrder.observe(this, aBoolean -> {
            isLoading = false;
            if (orderModelList.get(orderModelList.size() - 1) == null) {
                orderModelList.remove(orderModelList.size() - 1);
                adapter.notifyItemRemoved(orderModelList.size() - 1);
            }


        });

        binding.swipeRefresh.setOnRefreshListener(() -> myOrderViewModel.getMyPreviousOrder(userModel.getUser().getId()));

    }

    public void getOrders() {
        myOrderViewModel.getMyPreviousOrder(userModel.getUser().getId());

    }


}

