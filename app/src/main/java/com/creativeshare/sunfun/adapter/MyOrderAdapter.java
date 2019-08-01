package com.creativeshare.sunfun.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.databinding.LoadMoreBinding;
import com.creativeshare.sunfun.databinding.OrderRowBinding;
import com.creativeshare.sunfun.models.OrderDataModel;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class MyOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int ITEM_DATA = 1;
    private final int LOAD = 2;
    private List<OrderDataModel.OrderModel> orderModelList;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
    private Fragment fragment;

    public MyOrderAdapter(List<OrderDataModel.OrderModel> orderModelList, Context context,Fragment fragment) {
        this.orderModelList = orderModelList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType==ITEM_DATA)
        {
            OrderRowBinding binding  = DataBindingUtil.inflate(inflater, R.layout.order_row,parent,false);
            return new MyOrderHolder(binding);

        }else
            {
                LoadMoreBinding binding = DataBindingUtil.inflate(inflater, R.layout.load_more,parent,false);
                return new LoadHolder(binding);
            }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OrderDataModel.OrderModel orderModel = orderModelList.get(position);
        if (holder instanceof MyOrderHolder)
        {
            MyOrderHolder myOrderHolder = (MyOrderHolder) holder;
            myOrderHolder.binding.setLang(lang);
            myOrderHolder.binding.setOrderModel(orderModel);


        }else
            {
                LoadHolder loadHolder = (LoadHolder) holder;
                loadHolder.binding.progBar.setIndeterminate(true);
            }
    }

    @Override
    public int getItemCount() {
        return orderModelList.size();
    }

    public class MyOrderHolder extends RecyclerView.ViewHolder {
        public OrderRowBinding binding;
        public MyOrderHolder(@NonNull OrderRowBinding binding) {
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
        OrderDataModel.OrderModel orderModel = orderModelList.get(position);
        if (orderModel!=null)
        {
            return ITEM_DATA;
        }else
            {
                return LOAD;
            }

    }


}
