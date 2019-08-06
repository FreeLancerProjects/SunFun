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
import com.creativeshare.sunfun.activities_fragments.activity_home.fragments.Fragment_Notifications;
import com.creativeshare.sunfun.databinding.LoadMoreBinding;
import com.creativeshare.sunfun.databinding.NotificationRowBinding;
import com.creativeshare.sunfun.models.NotificationDataModel;

import java.util.List;

import io.paperdb.Paper;

public class MyNotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int ITEM_DATA = 1;
    private final int LOAD = 2;
    private List<NotificationDataModel.NotificationModel> notificationModelList;
    private Context context;
    private LayoutInflater inflater;
    private Fragment_Notifications fragment_notifications;

    public MyNotificationAdapter(List<NotificationDataModel.NotificationModel> notificationModelList, Context context, Fragment_Notifications fragment_notifications) {
        this.notificationModelList = notificationModelList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        this.fragment_notifications = fragment_notifications;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType==ITEM_DATA)
        {
            NotificationRowBinding binding  = DataBindingUtil.inflate(inflater, R.layout.notification_row,parent,false);
            return new NotificationHolder(binding);

        }else
            {
                LoadMoreBinding binding = DataBindingUtil.inflate(inflater, R.layout.load_more,parent,false);
                return new LoadHolder(binding);
            }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NotificationDataModel.NotificationModel notificationModel = notificationModelList.get(position);
        if (holder instanceof NotificationHolder)
        {
            NotificationHolder notificationHolder = (NotificationHolder) holder;
            notificationHolder.binding.setNotificationModel(notificationModel);

            notificationHolder.binding.getRoot().setOnClickListener(view -> {

                NotificationDataModel.NotificationModel notificationModel1 = notificationModelList.get(notificationHolder.getAdapterPosition());
                if (notificationModel1.getAction_type().equals("1"))
                {
                    fragment_notifications.setItemData(notificationModel1);

                }
            });

        }else
            {
                LoadHolder loadHolder = (LoadHolder) holder;
                loadHolder.binding.progBar.setIndeterminate(true);
            }

    }

    @Override
    public int getItemCount() {
        return notificationModelList.size();
    }

    public class NotificationHolder extends RecyclerView.ViewHolder {
        public NotificationRowBinding binding;
        public NotificationHolder(@NonNull NotificationRowBinding binding) {
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
        NotificationDataModel.NotificationModel notificationModel = notificationModelList.get(position);
        if (notificationModel!=null)
        {
            return ITEM_DATA;
        }else
            {
                return LOAD;
            }

    }


}
