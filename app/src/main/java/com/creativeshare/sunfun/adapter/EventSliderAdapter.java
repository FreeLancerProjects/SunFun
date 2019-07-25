package com.creativeshare.sunfun.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.databinding.EventSliderRowBinding;
import com.creativeshare.sunfun.tags.Tags;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EventSliderAdapter extends PagerAdapter {
    private List<String> imageList;
    private Context context;

    public EventSliderAdapter(List<String> imageList, Context context) {
        this.imageList = imageList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        EventSliderRowBinding rowBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.event_slider_row,container,false);
        Picasso.with(context).load(Uri.parse(Tags.IMAGE_EVENT_URL+imageList.get(position))).fit().into(rowBinding.image);
        container.addView(rowBinding.getRoot());
        return rowBinding.getRoot();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
