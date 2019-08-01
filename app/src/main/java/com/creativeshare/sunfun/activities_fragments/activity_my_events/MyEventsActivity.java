package com.creativeshare.sunfun.activities_fragments.activity_my_events;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.adapter.MyEventsAdapter;
import com.creativeshare.sunfun.adapter.Swipe;
import com.creativeshare.sunfun.databinding.ActivityMyEventsBinding;
import com.creativeshare.sunfun.databinding.DialogDeleteBinding;
import com.creativeshare.sunfun.language.Language;
import com.creativeshare.sunfun.models.EventDataModel;
import com.creativeshare.sunfun.models.UserModel;
import com.creativeshare.sunfun.preferences.Preferences;
import com.creativeshare.sunfun.singleton.Singleton;
import com.creativeshare.sunfun.viewmodel.delete_event_view_model.DeleteEventViewModel;
import com.creativeshare.sunfun.viewmodel.my_event_view_model.MyEventViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class MyEventsActivity extends AppCompatActivity implements Swipe.SwipeListener {
    private ActivityMyEventsBinding binding;
    private String lang;
    private Preferences preferences;
    private UserModel userModel;
    private LinearLayoutManager manager;
    private List<EventDataModel.EventModel> eventModelList;
    private MyEventsAdapter adapter;
    private MyEventViewModel eventViewModel;
    private boolean isLoading = false;
    private boolean isFirstTime = true;
    private Singleton singleton;
    private DeleteEventViewModel deleteEventViewModel;
    private int deleted_pos = -1;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", Locale.getDefault().getLanguage())));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_events);
        eventViewModel = ViewModelProviders.of(this).get(MyEventViewModel.class);
        deleteEventViewModel = ViewModelProviders.of(this).get(DeleteEventViewModel.class);
        initView();
    }

    private void initView() {
        singleton = Singleton.newInstance();
        eventModelList = new ArrayList<>();
        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        binding.setLang(lang);
        binding.setUserModel(userModel);
        binding.setViewModel(eventViewModel);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        manager = new LinearLayoutManager(this);
        binding.recView.setLayoutManager(manager);


        adapter = new MyEventsAdapter(eventModelList, this);
        binding.recView.setAdapter(adapter);
        ItemTouchHelper.SimpleCallback simpleCallback = new Swipe(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.recView);
        eventViewModel.setContext(this);
        eventViewModel.data.observe(this, eventModelList -> {
            binding.swipeRefresh.setRefreshing(false);
            binding.progBar.setVisibility(View.GONE);
            if (eventModelList.size() > 0) {
                binding.tvNoEvents.setVisibility(View.GONE);
            } else {
                binding.tvNoEvents.setVisibility(View.VISIBLE);

            }
            MyEventsActivity.this.eventModelList.clear();
            MyEventsActivity.this.eventModelList.addAll(eventModelList);
            adapter.notifyDataSetChanged();
        });
        eventViewModel.dataLoadMore.observe(this, eventModelList -> {
            isLoading = false;
            MyEventsActivity.this.eventModelList.remove(MyEventsActivity.this.eventModelList.size() - 1);
            adapter.notifyItemRemoved(MyEventsActivity.this.eventModelList.size() - 1);
            MyEventsActivity.this.eventModelList.addAll(eventModelList);
            adapter.notifyDataSetChanged();
        });

        eventViewModel.error.observe(this, aBoolean ->
                binding.swipeRefresh.setRefreshing(false));

        eventViewModel.errorLoadMore.observe(this, aBoolean -> {
            isLoading = false;
            if (eventModelList.get(eventModelList.size() - 1) == null) {
                eventModelList.remove(eventModelList.size() - 1);
                adapter.notifyItemRemoved(eventModelList.size() - 1);
            }


        });

        binding.swipeRefresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary), ContextCompat.getColor(this, R.color.black), ContextCompat.getColor(this, R.color.delete), ContextCompat.getColor(this, R.color.golden));
        binding.swipeRefresh.setOnRefreshListener(() ->
                {
                    eventViewModel.current_page = 1;
                    eventViewModel.getMyEvents(String.valueOf(userModel.getUser().getId()));

                }

        );

        binding.recView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int totalItems = adapter.getItemCount();
                    int lastVisiblePos = manager.findLastCompletelyVisibleItemPosition();
                    if (totalItems > 10 && (totalItems - lastVisiblePos) == 3 && !isLoading) {
                        isLoading = true;
                        eventModelList.add(null);
                        adapter.notifyItemInserted(eventModelList.size() - 1);
                        if (userModel != null) {
                            eventViewModel.loadMore(String.valueOf(userModel.getUser().getId()));

                        } else {
                            eventViewModel.loadMore("0");

                        }
                    }
                }
            }
        });

        deleteEventViewModel.success.observe(this, aBoolean -> {
                    if (deleted_pos != 1) {
                        eventModelList.remove(deleted_pos);
                        adapter.notifyItemRemoved(this.deleted_pos);
                        this.deleted_pos = -1;

                    }
                }

        );

        binding.llBack.setOnClickListener(view -> finish());


    }


    public void setItemData(EventDataModel.EventModel eventModel, int action, int pos) {
        //delete
        if (action == 2) {
            CreateDeleteDialog(pos, eventModel.getId());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFirstTime) {
            isFirstTime = false;
            eventViewModel.getMyEvents(String.valueOf(userModel.getUser().getId()));

        } else {
            if (singleton.isEventAdded()) {
                eventViewModel.getMyEvents(String.valueOf(userModel.getUser().getId()));

            }
        }
    }

    @Override
    public void onSwipe(int position, int dir) {
        if (dir == ItemTouchHelper.LEFT) {
            CreateDeleteDialog(position, eventModelList.get(position).getId());
            //delete
        } else {
            //edit
        }
    }


    private void CreateDeleteDialog(int pos, int event_id) {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .create();

        DialogDeleteBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_delete, null, false);
        binding.btnDelete.setOnClickListener((view ->
        {
            this.deleted_pos = pos;
            adapter.notifyDataSetChanged();
            deleteEventViewModel.deleteEvents(event_id);
            dialog.dismiss();
        }));
        binding.btnCancel.setOnClickListener((v) ->
                {
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }


        );
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_congratulation_animation;
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_window_bg);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(binding.getRoot());
        dialog.show();
    }
}
