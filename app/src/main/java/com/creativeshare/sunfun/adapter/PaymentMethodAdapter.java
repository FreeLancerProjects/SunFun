package com.creativeshare.sunfun.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.activities_fragments.activity_book_event.BookEventActivity;
import com.creativeshare.sunfun.databinding.PaymentRowBinding;
import com.creativeshare.sunfun.models.PaymentDataModel;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class PaymentMethodAdapter extends RecyclerView.Adapter<PaymentMethodAdapter.PaymentHolder> {

    private List<PaymentDataModel.PaymentModel> paymentModelList;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
    private BookEventActivity activity;
    private SparseBooleanArray sparseBooleanArray;
    public PaymentMethodAdapter(List<PaymentDataModel.PaymentModel> paymentModelList, Context context) {
        this.paymentModelList = paymentModelList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        activity = (BookEventActivity) context;
        sparseBooleanArray = new SparseBooleanArray();
    }

    @NonNull
    @Override
    public PaymentMethodAdapter.PaymentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        PaymentRowBinding binding  = DataBindingUtil.inflate(inflater, R.layout.payment_row,parent,false);
        return new PaymentHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentMethodAdapter.PaymentHolder holder, int position) {
        PaymentDataModel.PaymentModel paymentModel = paymentModelList.get(position);

        holder.binding.setLang(lang);
        holder.binding.setPaymentModel(paymentModel);
        if (sparseBooleanArray.size()>0&&sparseBooleanArray.get(position))
        {
            holder.binding.tvTitle.setBackgroundResource(R.drawable.selected_payment_bg);
            holder.binding.tvTitle.setTextColor(ContextCompat.getColor(context,R.color.white));
        }else
            {
                holder.binding.tvTitle.setBackgroundResource(R.drawable.un_selected_payment_bg);
                holder.binding.tvTitle.setTextColor(ContextCompat.getColor(context,R.color.gray5));

            }


        holder.binding.tvTitle.setOnClickListener(view -> {
            PaymentDataModel.PaymentModel paymentModel1 = paymentModelList.get(holder.getAdapterPosition());

            sparseBooleanArray.clear();
            sparseBooleanArray.put(holder.getAdapterPosition(),true);
            notifyDataSetChanged();

            activity.setItemSelected(paymentModel1);
        });

    }

    @Override
    public int getItemCount() {
        return paymentModelList.size();
    }

    public class PaymentHolder extends RecyclerView.ViewHolder {
        private PaymentRowBinding binding;
        public PaymentHolder(@NonNull PaymentRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }






}
