package com.creativeshare.sunfun.viewmodel.ui_general_method;

import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.tags.Tags;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class UI_General_Method {

    @BindingAdapter("error")
    public static void setErrorUi(View view, String error)
    {
        if (view instanceof EditText)
        {
            EditText editText = (EditText) view;
            editText.setError(error);

        }else if (view instanceof TextView)
        {
            TextView textView = (TextView) view;
            textView.setError(error);

        }
    }

    @BindingAdapter("imageEventEndPoint")
    public static void displayImage(RoundedImageView imageView, String imageEndPoint)
    {

        Picasso.with(imageView.getContext()).load(Uri.parse(Tags.IMAGE_EVENT_URL+imageEndPoint)).placeholder(R.drawable.logo).fit().into(imageView);

    }

    @BindingAdapter("imageActivityEndPoint")
    public static void displayImage2(ImageView imageView, String imageEndPoint)
    {

        Picasso.with(imageView.getContext()).load(Uri.parse(Tags.IMAGE_ACTIVITY_URL+imageEndPoint)).placeholder(R.drawable.logo).fit().into(imageView);

    }

    @BindingAdapter("imageUserEndPoint")
    public static void displayImage3(CircleImageView imageView, String imageEndPoint)
    {

        Picasso.with(imageView.getContext()).load(Uri.parse(Tags.IMAGE_USERS_URL+imageEndPoint)).placeholder(R.drawable.logo).fit().into(imageView);

    }

    @BindingAdapter({"startDate","endDate"})
    public static void displayDate(TextView textView,String start_date,String end_date)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        String sDate = dateFormat.format(new Date(Long.parseLong(start_date)*1000));

        SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

        String eDate = dateFormat2.format(new Date(Long.parseLong(end_date)*1000));

        textView.setText(String.format("%s - %s",sDate,eDate));
    }

    @BindingAdapter({"startTime","endTime"})
    public static void displayTime(TextView textView,String start_time,String end_time)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
        String sTime = dateFormat.format(new Date(Long.parseLong(start_time)*1000));

        SimpleDateFormat dateFormat2 = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);

        String eTime = dateFormat2.format(new Date(Long.parseLong(end_time)*1000));

        textView.setText(String.format("%s - %s",sTime,eTime));
    }
}
