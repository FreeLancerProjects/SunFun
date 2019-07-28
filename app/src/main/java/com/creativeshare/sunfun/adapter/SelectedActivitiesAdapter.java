package com.creativeshare.sunfun.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.activities_fragments.activity_book_event.BookEventActivity;
import com.creativeshare.sunfun.databinding.SelectedActivityRowBinding;
import com.creativeshare.sunfun.models.ActivityModelUpload;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class SelectedActivitiesAdapter extends RecyclerView.Adapter<SelectedActivitiesAdapter.Holder> {

    private List<ActivityModelUpload> activityModelUploadList;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
    private BookEventActivity activity;

    public SelectedActivitiesAdapter(List<ActivityModelUpload> activityModelUploadList, Context context) {
        this.activityModelUploadList = activityModelUploadList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        activity = (BookEventActivity) context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        SelectedActivityRowBinding binding  = DataBindingUtil.inflate(inflater, R.layout.selected_activity_row,parent,false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        ActivityModelUpload activityModelUpload = activityModelUploadList.get(position);

        holder.binding.setLang(lang);
        holder.binding.setActivityModel(activityModelUpload);
        holder.binding.imgDelete.setOnClickListener(view -> activity.deleteItem(holder.getAdapterPosition()));



    }

    @Override
    public int getItemCount() {
        return activityModelUploadList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private SelectedActivityRowBinding binding;
        public Holder(@NonNull SelectedActivityRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }






}
