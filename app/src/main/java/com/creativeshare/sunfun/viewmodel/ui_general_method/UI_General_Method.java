package com.creativeshare.sunfun.viewmodel.ui_general_method;

import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.share.TimeAgo;
import com.creativeshare.sunfun.tags.Tags;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
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

    @BindingAdapter("rate")
    public static void displayRate(SimpleRatingBar simpleRatingBar,double rate)
    {

        simpleRatingBar.setRating((float) rate);
    }
    @BindingAdapter("date")
    public static void convertToDate(TextView textView,String date)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd/MM/yyy", Locale.ENGLISH);
        String d = dateFormat.format(new Date(Long.parseLong(date)*1000));
        textView.setText(d);

    }

    @BindingAdapter("notification_date")
    public static void convertToNotDate(TextView textView,String date)
    {
       long d = Long.parseLong(date)*1000;
       String n_date = TimeAgo.getTimeAgo(d,textView.getContext());
       textView.setText(n_date);

    }

    @BindingAdapter({"notification_action","status"})
    public static void checkOrderStatus(TextView textView,String notification_action,String status)
    {
        if (notification_action.equals("1"))
        {
            textView.setText(R.string.new_order);
        }else if (notification_action.equals("4"))
        {
            if (status.equals("1"))
            {
                textView.setText(R.string.order_accepted);
            }else if (status.equals("2"))
            {
                textView.setText(R.string.order_refused);

            }
        }

        else if (notification_action.equals("5"))
        {
            if (status.equals("1"))
            {
                textView.setText(R.string.company_accepted);
            }else if (status.equals("2"))
            {
                textView.setText(R.string.company_refused);

            }
        }

        else if (notification_action.equals("6"))
        {
            if (status.equals("1"))
            {
                textView.setText(R.string.create_event_accepted);
            }else if (status.equals("2"))
            {
                textView.setText(R.string.create_event_refused);

            }
        }

    }

    @BindingAdapter({"visibility_tv"})
    public static void checkVisibility(TextView textView,String notification_action)
    {
        if (notification_action.equals("1"))
        {
            textView.setVisibility(View.VISIBLE);
        }else
            {
                textView.setVisibility(View.GONE);

            }

    }

    @BindingAdapter({"qr_code_image"})
    public static void qrcode_image(ImageView imageView,String end_point)
    {
        Picasso.with(imageView.getContext()).load(Uri.parse(Tags.IMAGE_QRCODE+end_point)).fit().into(imageView);

    }
}
