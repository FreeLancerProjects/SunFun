package com.creativeshare.sunfun.activities_fragments.activity_home.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.activities_fragments.activity_book_event.BookEventActivity;
import com.creativeshare.sunfun.activities_fragments.activity_home.activity.HomeActivity;
import com.creativeshare.sunfun.adapter.CategoryAdapter;
import com.creativeshare.sunfun.adapter.EventsAdapter;
import com.creativeshare.sunfun.adapter.SubCategoryAdapter;
import com.creativeshare.sunfun.databinding.DialogCustomBinding;
import com.creativeshare.sunfun.databinding.FragmentMainBinding;
import com.creativeshare.sunfun.models.CategoryDataModel;
import com.creativeshare.sunfun.models.EventDataModel;
import com.creativeshare.sunfun.models.UserModel;
import com.creativeshare.sunfun.preferences.Preferences;
import com.creativeshare.sunfun.singleton.Singleton;
import com.creativeshare.sunfun.viewmodel.category_view_model.CategoryViewModel;
import com.creativeshare.sunfun.viewmodel.events_view_model.EventViewModel;
import com.creativeshare.sunfun.viewmodel.search_category_view_model.CategorySearchViewModel;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class Fragment_Main extends Fragment {
    private HomeActivity activity;
    private FragmentMainBinding binding;
    private CategoryViewModel viewModel;
    private CategoryAdapter categoryAdapter;
    private List<CategoryDataModel.Category> categoryList;
    private List<CategoryDataModel.SubCategory> subCategoryList;
    private SubCategoryAdapter subCategoryAdapter;
    private EventsAdapter adapter;
    private LinearLayoutManager manager;
    private List<EventDataModel.EventModel> eventModelList;
    private EventViewModel eventViewModel;
    private String category_id = "", subcategory_id = "";
    private boolean isFirstTime = true;
    private Preferences preferences;
    private UserModel userModel;
    private CategorySearchViewModel categorySearchViewModel;
    private boolean isLoading = false;
    private Singleton singleton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        viewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        categorySearchViewModel = ViewModelProviders.of(this).get(CategorySearchViewModel.class);

        initView();
        return binding.getRoot();
    }

    private void initView() {
        singleton = Singleton.newInstance();
        eventModelList = new ArrayList<>();
        categoryList = new ArrayList<>();
        subCategoryList = new ArrayList<>();
        activity = (HomeActivity) getActivity();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        Paper.init(activity);

        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        viewModel.setContext(activity);
        viewModel.data.observe(this, categories -> {
            categoryList.clear();
            categoryList.addAll(categories);
            categoryAdapter = new CategoryAdapter(categories, activity);
            binding.spinnerCategory.setAdapter(categoryAdapter);
        });

        manager = new LinearLayoutManager(activity);
        binding.recView.setLayoutManager(manager);
        adapter = new EventsAdapter(eventModelList, activity, this);
        binding.recView.setAdapter(adapter);
        eventViewModel.setContext(activity);
        eventViewModel.data.observe(this, eventModelList -> {
            binding.swipeRefresh.setRefreshing(false);
            binding.progBar.setVisibility(View.GONE);
            Fragment_Main.this.eventModelList.clear();
            Fragment_Main.this.eventModelList.addAll(eventModelList);
            adapter.notifyDataSetChanged();
        });
        eventViewModel.dataLoadMore.observe(this, eventModelList -> {
            isLoading = false;
            Fragment_Main.this.eventModelList.remove(Fragment_Main.this.eventModelList.size()-1);
            adapter.notifyItemRemoved(Fragment_Main.this.eventModelList.size()-1);
            Fragment_Main.this.eventModelList.addAll(eventModelList);
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

        categorySearchViewModel.setContext(activity);
        binding.spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    subCategoryList.clear();
                    CategoryDataModel.SubCategory subCategory = new CategoryDataModel.SubCategory();
                    subCategory.setAr_title("التصنيف");
                    subCategory.setEn_title("Category");
                    subCategoryList.add(subCategory);
                    updateSubCategoryAdapter(subCategoryList);


                } else {
                    subCategoryList.clear();
                    CategoryDataModel.SubCategory subCategory = new CategoryDataModel.SubCategory();
                    subCategory.setAr_title("التصنيف");
                    subCategory.setEn_title("Category");
                    subCategoryList.add(subCategory);
                    subCategoryList.addAll(categoryList.get(i).getSub_categories());
                    updateSubCategoryAdapter(subCategoryList);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spinnerSubCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    category_id = subCategoryList.get(i).getCat_id();
                    subcategory_id = String.valueOf(subCategoryList.get(i).getId());
                    if (userModel!=null)
                    {
                        categorySearchViewModel.getEvents(category_id,subcategory_id,String.valueOf(userModel.getUser().getId()));

                    }else
                        {
                            categorySearchViewModel.getEvents(category_id,subcategory_id,"0");

                        }

                }else
                    {
                        subcategory_id="";
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        categorySearchViewModel.data.observe(this, eventModelList -> {
            Log.e("dddd",eventModelList.size()+"__");
            binding.swipeRefresh.setRefreshing(false);
            Fragment_Main.this.eventModelList.clear();
            Fragment_Main.this.eventModelList.addAll(eventModelList);
            adapter.notifyDataSetChanged();
        });
        categorySearchViewModel.error.observe(this, aBoolean -> binding.swipeRefresh.setRefreshing(false));
        binding.swipeRefresh.setColorSchemeColors(ContextCompat.getColor(activity, R.color.colorPrimary), ContextCompat.getColor(activity, R.color.black), ContextCompat.getColor(activity, R.color.delete), ContextCompat.getColor(activity, R.color.golden));
        binding.swipeRefresh.setOnRefreshListener(() -> {

            if (userModel != null) {

                eventViewModel.getEvents(String.valueOf(userModel.getUser().getId()));

            } else {
                eventViewModel.getEvents("0");

            }
            binding.spinnerCategory.setSelection(0);
            binding.spinnerSubCategory.setSelection(0);
            category_id = "";
            subcategory_id ="";


                }
        );

        binding.recView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy>0)
                {
                    int totalItems = adapter.getItemCount();
                    int lastVisiblePos = manager.findLastCompletelyVisibleItemPosition();
                    if (subcategory_id.isEmpty())
                    {
                        if (totalItems>10&&(totalItems-lastVisiblePos)==3&&!isLoading)
                        {
                            isLoading = true;
                            eventModelList.add(null);
                            adapter.notifyItemInserted(eventModelList.size()-1);
                            if (userModel!=null)
                            {
                                eventViewModel.loadMore(String.valueOf(userModel.getUser().getId()));

                            }else
                                {
                                    eventViewModel.loadMore("0");

                                }
                        }

                    }
                }
            }
        });





    }

    private void updateSubCategoryAdapter(List<CategoryDataModel.SubCategory> subCategoryList) {
        subCategoryAdapter = new SubCategoryAdapter(subCategoryList, activity);
        binding.spinnerSubCategory.setAdapter(subCategoryAdapter);
    }


    public static Fragment_Main newInstance() {
        return new Fragment_Main();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstTime) {
            viewModel.getCategories();

            if (userModel != null) {

                eventViewModel.getEvents(String.valueOf(userModel.getUser().getId()));

            } else {
                eventViewModel.getEvents("0");

            }
            isFirstTime = false;
        }else
            {
                if (userModel != null&&singleton.isEventAdded()) {

                    eventViewModel.getEvents(String.valueOf(userModel.getUser().getId()));
                    singleton.setEventAdded(false);
                }
            }
    }

    public void setItemData(EventDataModel.EventModel eventModel) {
        activity.DisplayFragmentEventDetails(eventModel);
    }

    public void book(EventDataModel.EventModel eventModel) {
        if (userModel!=null)
        {
            if (userModel.getUser().getId()!=Integer.parseInt(eventModel.getCompany_id()))
            {
                Intent intent = new Intent(activity, BookEventActivity.class);
                intent.putExtra("data",eventModel);
                startActivity(intent);
            }else
                {
                    Toast.makeText(activity, R.string.cnt_book, Toast.LENGTH_LONG).show();
                }

        }else
            {
                CreateNoSignAlertDialog();
            }

    }

    private void CreateNoSignAlertDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(activity)
                .create();

        DialogCustomBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_custom, null, false);

        binding.btnSignUp.setOnClickListener((v) -> {
            dialog.dismiss();
            activity.finish();



        });
        binding.btnSignIn.setOnClickListener((v) -> {
            dialog.dismiss();
            activity.finish();


        });

        binding.btnCancel.setOnClickListener((v) ->
                dialog.dismiss()

        );
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_congratulation_animation;
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_window_bg);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(binding.getRoot());
        dialog.show();
    }

}
