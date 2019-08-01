package com.creativeshare.sunfun.activities_fragments.activity_home.fragments.fragment_orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.activities_fragments.activity_home.activity.HomeActivity;
import com.creativeshare.sunfun.adapter.Pager_Adapter;
import com.creativeshare.sunfun.databinding.FragmentOrdersBinding;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class Fragment_Orders extends Fragment {
    private HomeActivity activity;
    private FragmentOrdersBinding binding;
    private Pager_Adapter pager_adapter;
    private List<Fragment> fragments;
    private List<String> titles;

    public static Fragment_Orders newInstance()
    {
        return new Fragment_Orders();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_orders,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
        activity=(HomeActivity)getActivity();
        Paper.init(activity);

        fragments.add(Fragment_Current_Order.newInstance());
        fragments.add(Fragment_Previous_Order.newInstance());

        titles.add(getString(R.string.current));
        titles.add(getString(R.string.previou));
        binding.tab.setupWithViewPager(binding.pager);

        pager_adapter = new Pager_Adapter(getChildFragmentManager());
        pager_adapter.setFragments(fragments);
        pager_adapter.setTitles(titles);
        binding.pager.setAdapter(pager_adapter);

    }

    public void refreshFragmentOrder()
    {
        Fragment_Current_Order fragment_current_order = (Fragment_Current_Order) pager_adapter.getItem(0);
        fragment_current_order.getOrders();

        Fragment_Previous_Order fragment_previous_order= (Fragment_Previous_Order) pager_adapter.getItem(1);
        fragment_previous_order.getOrders();
    }


}
