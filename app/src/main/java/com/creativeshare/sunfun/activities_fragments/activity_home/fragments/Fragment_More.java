package com.creativeshare.sunfun.activities_fragments.activity_home.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.activities_fragments.activity_app_info.AppInfoActivity;
import com.creativeshare.sunfun.activities_fragments.activity_home.activity.HomeActivity;
import com.creativeshare.sunfun.databinding.FragmentMoreBinding;
import com.creativeshare.sunfun.models.UserModel;
import com.creativeshare.sunfun.preferences.Preferences;
import com.creativeshare.sunfun.tags.Tags;

import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_More extends Fragment {
    private HomeActivity activity;
    private String current_language;
    private FragmentMoreBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private String [] language_array;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_more,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeActivity) getActivity();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        Paper.init(activity);
        current_language = Paper.book().read("lang", Locale.getDefault().getLanguage());
        language_array = new String[]{"English","العربية"};

        binding.setLang(current_language);
        binding.llContact.setOnClickListener((view -> activity.DisplayFragmentContactUs()));
        binding.llBank.setOnClickListener((view -> activity.DisplayFragmentBank()));
        binding.llTerms.setOnClickListener(view -> NavigateToAppInfoActivity(1));
        binding.llAbout.setOnClickListener(view -> NavigateToAppInfoActivity(2));
        binding.llProfile.setOnClickListener(view -> {
            if (userModel!=null)
            {
                if (userModel.getUser().getUser_type()== Tags.type_user)
                {
                    activity.DisplayFragmentClientProfile();
                }
            }else
                {
                    activity.CreateNoSignAlertDialog();

                }
        });
        binding.llEditProfile.setOnClickListener(view -> {
            if (userModel!=null)
            {
                if (userModel.getUser().getUser_type()== Tags.type_user)
                {

                }
            }else
            {
                activity.CreateNoSignAlertDialog();

            }
        });
        binding.llLanguageSettings.setOnClickListener(view -> CreateLanguageDialog());
        binding.llLogout.setOnClickListener(view ->activity.logout());
    }

    private void NavigateToAppInfoActivity(int type)
    {
        Intent intent = new Intent(activity, AppInfoActivity.class);
        intent.putExtra("type",type);
        startActivity(intent);
    }
    public static Fragment_More newInstance() {
        return new Fragment_More();
    }

    private void CreateLanguageDialog()
    {
        final AlertDialog dialog = new AlertDialog.Builder(activity)
                .setCancelable(true)
                .create();

        View view  = LayoutInflater.from(activity).inflate(R.layout.dialog_language,null);
        Button btn_select = view.findViewById(R.id.btn_select);
        Button btn_cancel = view.findViewById(R.id.btn_cancel);

        final NumberPicker numberPicker = view.findViewById(R.id.numberPicker);

        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(language_array.length-1);
        numberPicker.setDisplayedValues(language_array);
        numberPicker.setWrapSelectorWheel(false);
        if (current_language.equals("ar"))
        {
            numberPicker.setValue(1);

        }else
        {
            numberPicker.setValue(0);

        }
        btn_select.setOnClickListener(v -> {
            dialog.dismiss();
            int pos = numberPicker.getValue();
            if (pos == 0)
            {
                activity.RefreshActivity("en");
            }else
            {
                activity.RefreshActivity("ar");

            }

        });




        btn_cancel.setOnClickListener(v -> dialog.dismiss());

        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_congratulation_animation;
        dialog.setView(view);
        dialog.show();
    }
}
