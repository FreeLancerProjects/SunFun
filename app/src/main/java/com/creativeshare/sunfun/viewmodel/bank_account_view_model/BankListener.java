package com.creativeshare.sunfun.viewmodel.bank_account_view_model;

import com.creativeshare.sunfun.models.BankDataModel;

import java.util.List;

public interface BankListener {

    void onSuccess(List<BankDataModel.BankModel> bankModelList);
    void onFailed(int code);
    void onError(String error);
}
