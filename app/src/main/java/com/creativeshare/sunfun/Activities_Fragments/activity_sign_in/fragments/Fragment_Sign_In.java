package com.creativeshare.sunfun.Activities_Fragments.activity_sign_in.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.creativeshare.sunfun.Activities_Fragments.activity_sign_in.activities.SignInActivity;
import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.Share.Common;
import com.creativeshare.sunfun.databinding.FragmentSignInBinding;
import com.creativeshare.sunfun.models.UserModel;
import com.creativeshare.sunfun.preferences.Preferences;
import com.creativeshare.sunfun.remote.Api;
import com.creativeshare.sunfun.tags.Tags;
import com.creativeshare.sunfun.viewmodel.User_View_Model;

import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Sign_In extends Fragment {
    private FragmentSignInBinding fragmentSignInBinding;
    private SignInActivity signInActivity;
    private String cuurent_language;
    private User_View_Model user_view_model;
    private Preferences preferences;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentSignInBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false);
        View view = fragmentSignInBinding.getRoot();
        initView();
        return view;
    }

    private void initView() {
        signInActivity = (SignInActivity) getActivity();
        preferences = Preferences.getInstance();
        Paper.init(signInActivity);
        user_view_model = new User_View_Model(signInActivity);

        cuurent_language = Paper.book().read("lang", Locale.getDefault().getLanguage());
        fragmentSignInBinding.setUserview(user_view_model);
        fragmentSignInBinding.btSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check = checkdata();
                if (check == true) {
                    login();
                }
            }
        });
        fragmentSignInBinding.llNewAcccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInActivity.DisplayFragmentSignup();
            }
        });
    }

    private void login() {
        final ProgressDialog dialog = Common.createProgressDialog(signInActivity, getString(R.string.log_in));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        Api.getService(Tags.base_url).signIn(user_view_model.useremail, user_view_model.userpass).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    Log.e("Error", response.code() + " " + response.body().getUser().getId());

                    preferences.create_update_userdata(signInActivity, response.body());
                    signInActivity.gotohome();
                } else {
                    dialog.dismiss();
                    if (response.code() == 404) {
                        Log.e("Error", response.code() + "" + response.body() + user_view_model.useremail);
                        user_view_model.msg = getString(R.string.inc_phonr_password);
                        Common.CreateSignAlertDialog(signInActivity);
                    } else {
                        Log.e("Error", response.code() + "" + response.errorBody());

                    }
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                dialog.dismiss();
                Log.e("Error", t.getMessage());


            }
        });
    }

    private boolean checkdata() {
        String useremail = user_view_model.useremail;
        String userpass = user_view_model.userpass;
        if (useremail == null || userpass == null) {
            if (useremail == null) {
                fragmentSignInBinding.edtEmail.setError("");
            }
            if (userpass == null) {
                fragmentSignInBinding.edtPass.setError("");
            }
            return false;
        } else {
            return true;
        }
    }

    public static Fragment_Sign_In newInstance() {
        return new Fragment_Sign_In();
    }

}
