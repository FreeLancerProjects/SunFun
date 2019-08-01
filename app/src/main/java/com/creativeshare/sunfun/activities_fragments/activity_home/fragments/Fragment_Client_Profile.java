package com.creativeshare.sunfun.activities_fragments.activity_home.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.activities_fragments.activity_home.activity.HomeActivity;
import com.creativeshare.sunfun.databinding.FragmentProfileClientBinding;
import com.creativeshare.sunfun.models.SocialDataModel;
import com.creativeshare.sunfun.models.UserModel;
import com.creativeshare.sunfun.preferences.Preferences;
import com.creativeshare.sunfun.remote.Api;
import com.creativeshare.sunfun.share.Common;
import com.creativeshare.sunfun.tags.Tags;

import java.io.IOException;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Client_Profile extends Fragment {
    private FragmentProfileClientBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private String current_lang;
    private HomeActivity activity;
    private SocialDataModel socialDataModel=null;

    public static Fragment_Client_Profile newInstance()
    {
        return new Fragment_Client_Profile();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_client,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeActivity) getActivity();
        Paper.init(activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        binding.setLang(current_lang);
        binding.setUserModel(userModel);
        binding.consBack.setOnClickListener(view -> activity.Back());
        binding.consUpgrade.setOnClickListener(view -> {
            if (userModel.getUser().getBe_company()==0||userModel.getUser().getBe_company()==2)
            {
                activity.DisplayFragmentUpgradeToCompany();
            }else
            {
                Common.CreateAlertDialog(activity,getString(R.string.req_sent));
            }
        });
        binding.imageFacebook.setOnClickListener(view -> {
            if (socialDataModel!=null)
            {
                if (socialDataModel.getFacebook()!=null&&!TextUtils.isEmpty(socialDataModel.getFacebook()) &&!socialDataModel.getFacebook().equals("0"))
                {
                    createSocialIntent(socialDataModel.getFacebook());
                }
                else
                    {
                        Common.CreateAlertDialog(activity,getString(R.string.not_avail));
                    }
            }else {
                Common.CreateAlertDialog(activity,getString(R.string.not_avail));

            }
        });

        binding.imageInstagram.setOnClickListener(view -> {
            if (socialDataModel!=null)
            {
                if (socialDataModel.getInstagram()!=null&&!TextUtils.isEmpty(socialDataModel.getInstagram()) &&!socialDataModel.getInstagram().equals("0"))
                {
                    createSocialIntent(socialDataModel.getInstagram());
                }
                else
                {
                    Common.CreateAlertDialog(activity,getString(R.string.not_avail));
                }
            }else {
                Common.CreateAlertDialog(activity,getString(R.string.not_avail));

            }
        });

        binding.imageTwitter.setOnClickListener(view -> {
            if (socialDataModel!=null)
            {
                if (socialDataModel.getTwitter()!=null&&!TextUtils.isEmpty(socialDataModel.getTwitter()) &&!socialDataModel.getTwitter().equals("0"))
                {
                    createSocialIntent(socialDataModel.getTwitter());
                }
                else
                {
                    Common.CreateAlertDialog(activity,getString(R.string.not_avail));
                }
            }else {
                Common.CreateAlertDialog(activity,getString(R.string.not_avail));

            }
        });
        getSocialMedia();
    }


    public void updateUI(UserModel userModel)
    {
        this.userModel = userModel;
        binding.setUserModel(userModel);
    }

    private void getSocialMedia() {
        ProgressDialog dialog = Common.createProgressDialog(activity,getString(R.string.wait));
        dialog.show();
        Api.getService(Tags.base_url)
                .getSocial()
                .enqueue(new Callback<SocialDataModel>() {
                    @Override
                    public void onResponse(Call<SocialDataModel> call, Response<SocialDataModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful())
                        {
                            socialDataModel = response.body();
                        }else
                            {
                                try {
                                    Log.e("error_code",response.code()+"_"+response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                    }

                    @Override
                    public void onFailure(Call<SocialDataModel> call, Throwable t) {
                        try {
                            dialog.dismiss();
                            Log.e("error",t.getMessage());

                        }catch (Exception e){}
                    }
                });
    }

    private void createSocialIntent(String url)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
