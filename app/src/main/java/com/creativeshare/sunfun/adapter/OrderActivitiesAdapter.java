package com.creativeshare.sunfun.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.databinding.OrderActivityRowBinding;
import com.creativeshare.sunfun.models.OrderDataModel;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class OrderActivitiesAdapter extends RecyclerView.Adapter<OrderActivitiesAdapter.ActivityHolder> {

    private List<OrderDataModel.BookingDetails> activityModelList;
    private Context context;
    private LayoutInflater inflater;
    private String lang;

    public OrderActivitiesAdapter(List<OrderDataModel.BookingDetails> activityModelList, Context context) {
        this.activityModelList = activityModelList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
    }

    @NonNull
    @Override
    public OrderActivitiesAdapter.ActivityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        OrderActivityRowBinding binding  = DataBindingUtil.inflate(inflater, R.layout.order_activity_row,parent,false);
        return new ActivityHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderActivitiesAdapter.ActivityHolder holder, int position) {
        OrderDataModel.BookingDetails activityModel = activityModelList.get(position);

        holder.binding.setLang(lang);
        holder.binding.setActivityModel(activityModel);


    }

    @Override
    public int getItemCount() {
        return activityModelList.size();
    }

    public class ActivityHolder extends RecyclerView.ViewHolder {
        private OrderActivityRowBinding binding;
        public ActivityHolder(@NonNull OrderActivityRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }






}
