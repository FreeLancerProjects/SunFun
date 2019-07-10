package com.creativeshare.sunfun.Activities_Fragments.Sign_in.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.Activities_Fragments.Sign_in.Activities.SignInActivity;
import com.creativeshare.sunfun.databinding.FragmentSignInBinding;

import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_Sign_In extends Fragment {
    private SignInActivity signInActivity;
    private LinearLayout ll_new_account;
    private Button bt_signin;

    private String  cuurent_language;
    FragmentSignInBinding fragmentSignInBinding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      fragmentSignInBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_sign_in,container,false);
      View view=fragmentSignInBinding.getRoot();
        initView();
        return view;
    }

    private void initView() {
        signInActivity=(SignInActivity)getActivity();
        Paper.init(signInActivity);
        cuurent_language = Paper.book().read("lang", Locale.getDefault().getLanguage());
        ll_new_account=fragmentSignInBinding.llNewAcccount;
        bt_signin=fragmentSignInBinding.btSignin;
        ll_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInActivity.DisplayFragmentSignup();
            }
        });
        bt_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInActivity.gotohome();
            }
        });

    }

    public static Fragment_Sign_In newInstance()
    {
        return new Fragment_Sign_In();
    }
}
