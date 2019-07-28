package com.creativeshare.sunfun.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.databinding.SpinnerRowBinding;
import com.creativeshare.sunfun.models.EventDataModel;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class SpinnerActivityAdapter extends BaseAdapter {
    private List<EventDataModel.EventModel.ActivityModel> activityModelList;
    private Context context;
    private String lang;

    public SpinnerActivityAdapter(List<EventDataModel.EventModel.ActivityModel> activityModelList, Context context) {
        this.activityModelList = activityModelList;
        this.context = context;
        Paper.init(context);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
    }

    @Override
    public int getCount() {
        return activityModelList.size();
    }

    @Override
    public Object getItem(int i) {
        return activityModelList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        @SuppressLint("ViewHolder") SpinnerRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.spinner_row,viewGroup,false);
        EventDataModel.EventModel.ActivityModel activityModel = activityModelList.get(i);
        Log.e("ar",activityModel.getAr_title());
        Log.e("en",activityModel.getEn_title()+"_");

        if (lang.equals("ar"))
        {
            binding.tvTitle.setText(activityModel.getAr_title());
        }else
            {
                if (activityModel.getEn_title()!=null)
                {
                    binding.tvTitle.setText(activityModel.getEn_title());

                }else
                    {
                        binding.tvTitle.setText(activityModel.getAr_title());

                    }

            }
        return binding.getRoot();
    }
}
