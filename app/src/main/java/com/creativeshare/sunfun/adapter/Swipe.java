package com.creativeshare.sunfun.adapter;

import android.graphics.Canvas;
import android.view.View;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.sunfun.R;

public class Swipe extends ItemTouchHelper.SimpleCallback {

    private SwipeListener swipeListener;
    public Swipe(int dragDirs, int swipeDirs,SwipeListener swipeListener) {
        super(dragDirs, swipeDirs);
        this.swipeListener = swipeListener;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (swipeListener!=null)
        {
            swipeListener.onSwipe(viewHolder.getAdapterPosition(),direction);

        }
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {

        if (viewHolder!=null)
        {
            if (viewHolder instanceof MyEventsAdapter.EventHolder)
            {
                MyEventsAdapter.EventHolder holder = (MyEventsAdapter.EventHolder) viewHolder;
                getDefaultUIUtil().onSelected(holder.binding.consForeground);

            }

        }



    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (viewHolder!=null)
        {
            if (viewHolder instanceof MyEventsAdapter.EventHolder)
            {
                MyEventsAdapter.EventHolder holder = (MyEventsAdapter.EventHolder) viewHolder;
                getDefaultUIUtil().clearView(holder.binding.consForeground);

            }

        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {


        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE)
        {

            if (viewHolder!=null)
            {
                if (viewHolder instanceof MyEventsAdapter.EventHolder)
                {
                    MyEventsAdapter.EventHolder holder = (MyEventsAdapter.EventHolder) viewHolder;

                    if (dX>0)
                    {
                        holder.binding.consBackground.setBackgroundResource(R.color.active);
                        holder.binding.img2.setVisibility(View.GONE);
                        holder.binding.tv2.setVisibility(View.GONE);
                        holder.binding.img1.setVisibility(View.VISIBLE);
                        holder.binding.tv1.setVisibility(View.VISIBLE);


                    }else
                    {
                        holder.binding.consBackground.setBackgroundResource(R.color.delete);
                        holder.binding.img2.setVisibility(View.VISIBLE);
                        holder.binding.tv2.setVisibility(View.VISIBLE);
                        holder.binding.img1.setVisibility(View.GONE);
                        holder.binding.tv1.setVisibility(View.GONE);
                    }


                    getDefaultUIUtil().onDraw(c,recyclerView,holder.binding.consForeground,dX,dY,actionState,isCurrentlyActive);

                }

            }


        }

    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (viewHolder!=null)
        {
            if (viewHolder instanceof MyEventsAdapter.EventHolder)
            {
                MyEventsAdapter.EventHolder holder = (MyEventsAdapter.EventHolder) viewHolder;


                getDefaultUIUtil().onDrawOver(c,recyclerView,holder.binding.consForeground,dX,dY,actionState,isCurrentlyActive);

            }

        }

    }


    public interface SwipeListener
    {
        void onSwipe(int position,int dir);
    }
}
