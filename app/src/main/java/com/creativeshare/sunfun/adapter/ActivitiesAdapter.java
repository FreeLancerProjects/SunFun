package com.creativeshare.sunfun.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.activities_fragments.activity_home.fragments.Fragment_Event_Details;
import com.creativeshare.sunfun.databinding.ActivitiesRowBinding;
import com.creativeshare.sunfun.models.EventDataModel;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivitiesAdapter.ActivityHolder> {

    private List<EventDataModel.EventModel.ActivityModel> activityModelList;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
    private Fragment_Event_Details fragment;

    public ActivitiesAdapter(List<EventDataModel.EventModel.ActivityModel> activityModelList, Context context, Fragment_Event_Details fragment) {
        this.activityModelList = activityModelList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ActivitiesAdapter.ActivityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ActivitiesRowBinding binding  = DataBindingUtil.inflate(inflater, R.layout.activities_row,parent,false);
        return new ActivityHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivitiesAdapter.ActivityHolder holder, int position) {
        EventDataModel.EventModel.ActivityModel activityModel = activityModelList.get(position);

        holder.binding.setLang(lang);
        holder.binding.setActivityModel(activityModel);


    }

    @Override
    public int getItemCount() {
        return activityModelList.size();
    }

    public class ActivityHolder extends RecyclerView.ViewHolder {
        private ActivitiesRowBinding binding;
        public ActivityHolder(@NonNull ActivitiesRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }






}
