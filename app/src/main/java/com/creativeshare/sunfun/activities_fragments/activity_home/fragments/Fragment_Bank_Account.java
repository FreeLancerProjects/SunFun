package com.creativeshare.sunfun.activities_fragments.activity_home.fragments;

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

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.activities_fragments.activity_home.activity.HomeActivity;
import com.creativeshare.sunfun.adapter.BankAdapter;
import com.creativeshare.sunfun.databinding.FragmentBankAccountBinding;
import com.creativeshare.sunfun.models.BankDataModel;
import com.creativeshare.sunfun.viewmodel.bank_account_view_model.BankViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_Bank_Account extends Fragment {

    private HomeActivity activity;
    private FragmentBankAccountBinding binding;
    private LinearLayoutManager manager;
    private List<BankDataModel.BankModel> bankModelList;
    private String current_lang;
    private BankViewModel bankViewModel;
    private BankAdapter adapter;




    public static Fragment_Bank_Account newInstance()
    {
        return new Fragment_Bank_Account();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bank_account,container,false);
        bankViewModel = ViewModelProviders.of(this).get(BankViewModel.class);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        bankModelList = new ArrayList<>();
        activity = (HomeActivity) getActivity();
        bankViewModel.setContext(activity);
        Paper.init(activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(current_lang);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        manager = new LinearLayoutManager(activity);
        binding.recView.setLayoutManager(manager);
        adapter = new BankAdapter(bankModelList,activity);
        binding.recView.setAdapter(adapter);
        bankViewModel.getBanks();
        bankViewModel.error.observe(this, aBoolean ->
            binding.progBar.setVisibility(View.GONE)
        );
        bankViewModel.data.observe(this, bankModelList -> {
            binding.progBar.setVisibility(View.GONE);
            Fragment_Bank_Account.this.bankModelList.clear();
            Fragment_Bank_Account.this.bankModelList.addAll(bankModelList);
            adapter.notifyDataSetChanged();

        });

        binding.arrow.setOnClickListener(view -> activity.Back());
    }
}
