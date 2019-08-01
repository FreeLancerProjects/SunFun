package com.creativeshare.sunfun.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.activities_fragments.activity_my_events.MyEventsActivity;
import com.creativeshare.sunfun.databinding.LoadMoreBinding;
import com.creativeshare.sunfun.databinding.MyEventRowBinding;
import com.creativeshare.sunfun.models.EventDataModel;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class MyEventsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int ITEM_DATA = 1;
    private final int LOAD = 2;
    private List<EventDataModel.EventModel> eventModelList;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
    private MyEventsActivity activity;

    public MyEventsAdapter(List<EventDataModel.EventModel> eventModelList, Context context) {
        this.eventModelList = eventModelList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        this.activity = (MyEventsActivity) context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType==ITEM_DATA)
        {
            MyEventRowBinding binding  = DataBindingUtil.inflate(inflater, R.layout.my_event_row,parent,false);
            return new EventHolder(binding);

        }else
            {
                LoadMoreBinding binding = DataBindingUtil.inflate(inflater, R.layout.load_more,parent,false);
                return new LoadHolder(binding);
            }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        EventDataModel.EventModel eventModel = eventModelList.get(position);
        if (holder instanceof EventHolder)
        {
            EventHolder eventHolder = (EventHolder) holder;
            eventHolder.binding.setLang(lang);
            eventHolder.binding.setEvent(eventModel);
            eventHolder.binding.imageEdit.setOnClickListener(view -> {
                EventDataModel.EventModel eventModel1 = eventModelList.get(eventHolder.getAdapterPosition());
                activity.setItemData(eventModel1,1,holder.getAdapterPosition());
            });

            eventHolder.binding.imageDelete.setOnClickListener(view -> {
                EventDataModel.EventModel eventModel1 = eventModelList.get(eventHolder.getAdapterPosition());
                activity.setItemData(eventModel1,2,holder.getAdapterPosition());
            });


        }else
            {
                LoadHolder loadHolder = (LoadHolder) holder;
                loadHolder.binding.progBar.setIndeterminate(true);
            }
    }

    @Override
    public int getItemCount() {
        return eventModelList.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder {
        public MyEventRowBinding binding;
        public EventHolder(@NonNull MyEventRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public class LoadHolder extends RecyclerView.ViewHolder {
        private LoadMoreBinding binding;
        public LoadHolder(@NonNull LoadMoreBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        }

    }

    @Override
    public int getItemViewType(int position) {
        EventDataModel.EventModel eventModel = eventModelList.get(position);
        if (eventModel!=null)
        {
            return ITEM_DATA;
        }else
            {
                return LOAD;
            }

    }


}
