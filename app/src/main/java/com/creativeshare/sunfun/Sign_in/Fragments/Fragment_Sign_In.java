package com.creativeshare.sunfun.Sign_in.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.Sign_in.Activities.SignInActivity;

public class Fragment_Sign_In extends Fragment {
    private SignInActivity signInActivity;
    private LinearLayout ll_new_account;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        signInActivity=(SignInActivity)getActivity();
        ll_new_account=view.findViewById(R.id.ll_new_acccount);
        ll_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInActivity.DisplayFragmentSignup();
            }
        });

    }

    public static Fragment_Sign_In newInstance()
    {
        return new Fragment_Sign_In();
    }
}
