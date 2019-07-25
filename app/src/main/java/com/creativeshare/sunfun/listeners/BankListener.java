package com.creativeshare.sunfun.listeners;

import com.creativeshare.sunfun.models.BankDataModel;

import java.util.List;

public interface BankListener {

    void onSuccess(List<BankDataModel.BankModel> bankModelList);
    void onFailed(int code);
    void onError(String error);
}
