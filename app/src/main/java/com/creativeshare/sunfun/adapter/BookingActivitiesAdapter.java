package com.creativeshare.sunfun.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.databinding.BookActivityRowBinding;
import com.creativeshare.sunfun.models.BookingScanData;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class BookingActivitiesAdapter extends RecyclerView.Adapter<BookingActivitiesAdapter.ActivityHolder> {

    private List<BookingScanData.BookingDetails> activityModelList;
    private Context context;
    private LayoutInflater inflater;
    private String lang;

    public BookingActivitiesAdapter(List<BookingScanData.BookingDetails> activityModelList, Context context) {
        this.activityModelList = activityModelList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
    }

    @NonNull
    @Override
    public BookingActivitiesAdapter.ActivityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        BookActivityRowBinding binding  = DataBindingUtil.inflate(inflater, R.layout.book_activity_row,parent,false);
        return new ActivityHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingActivitiesAdapter.ActivityHolder holder, int position) {
        BookingScanData.BookingDetails activityModel = activityModelList.get(position);

        holder.binding.setLang(lang);
        holder.binding.setActivityModel(activityModel);


    }

    @Override
    public int getItemCount() {
        return activityModelList.size();
    }

    public class ActivityHolder extends RecyclerView.ViewHolder {
        private BookActivityRowBinding binding;
        public ActivityHolder(@NonNull BookActivityRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }






}
