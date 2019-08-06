package com.creativeshare.sunfun.activities_fragments.activity_home.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.creativeshare.sunfun.activities_fragments.activity_home.activity.HomeActivity;
import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.databinding.FragmentContactUsBinding;
import com.creativeshare.sunfun.models.UserModel;
import com.creativeshare.sunfun.preferences.Preferences;
import com.creativeshare.sunfun.viewmodel.contact_us_view_model.ContactViewModel;

import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_Contact_Us extends Fragment {
    private HomeActivity activity;
    private String current_language;
    private FragmentContactUsBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private ContactViewModel contactViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_contact_us,container,false);
        contactViewModel= ViewModelProviders.of(this).get(ContactViewModel.class);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeActivity) getActivity();
        binding.setViewModel(contactViewModel);
        contactViewModel.setContext(activity);
        Paper.init(activity);
        current_language = Paper.book().read("lang", Locale.getDefault().getLanguage());
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        binding.setLang(current_language);
        if (userModel!=null)
        {
            contactViewModel.name.set(userModel.getUser().getName());
            contactViewModel.email.set(userModel.getUser().getEmail());

        }

        binding.arrow.setOnClickListener(view -> activity.Back());


    }

    public static Fragment_Contact_Us newInstance() {
        return new Fragment_Contact_Us();
    }
}
