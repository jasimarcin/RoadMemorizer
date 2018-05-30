package com.marcin.jasi.roadmemorizer.general.view.appToolbar;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.jakewharton.rxbinding2.view.RxView;
import com.marcin.jasi.roadmemorizer.R;
import com.marcin.jasi.roadmemorizer.databinding.AppToolbarBinding;
import com.marcin.jasi.roadmemorizer.general.common.presentation.VerticalRecyclerDivider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;


public class AppToolbar extends LinearLayout {

    public interface AppToolbarListener {
        void onItemClick(AppToolbarData item);
    }

    private AppToolbarBinding binding;
    private View popUpLayout;
    private PopupWindow popUpList;
    private RotateAnimation collapseAnimation;
    private RotateAnimation expandAnimation;
    private List<AppToolbarData> items = new ArrayList<>();
    private AppToolbarAdapter adapter;
    private RecyclerView recyclerView;
    private AppToolbarListener clickListener;
    private ObservableField<String> header = new ObservableField<>();
    private boolean popUpIsShow;

    private CompositeDisposable disposable = new CompositeDisposable();

    public AppToolbar(Context context) {
        this(context, null);
    }

    public AppToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public AppToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.app_toolbar, this, true);
        binding.setController(this);

        initPopUpList();
        setOnArrowClickListener();
        setupAnimations();
    }

    private void initPopUpList() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        popUpLayout = inflater.inflate(R.layout.pop_up_layout, findViewById(R.id.relative_layout));

        setupRecyclerView();

        popUpList = new PopupWindow(popUpLayout,
                ViewPager.LayoutParams.MATCH_PARENT,
                ViewPager.LayoutParams.WRAP_CONTENT,
                true);

        popUpList.setOnDismissListener(this::handleOnArrowClick);
        popUpList.setAnimationStyle(R.style.PopUpAnimationStyle);
    }

    private void setupRecyclerView() {
        recyclerView = popUpLayout.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new AppToolbarAdapter(items, item -> {

            popUpList.dismiss();

            if (clickListener != null)
                clickListener.onItemClick(item);
        });

        recyclerView.setAdapter(adapter);

        VerticalRecyclerDivider divider = new VerticalRecyclerDivider(1, false);
        recyclerView.addItemDecoration(divider);
    }

    private void setOnArrowClickListener() {
        disposable.add(
                RxView.clicks(binding.arrow)
                        .throttleFirst(500, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(view -> handleOnArrowClick(),
                                Timber::d));
    }

    private void setupAnimations() {
        collapseAnimation = getAnimation(180, 0);
        expandAnimation = getAnimation(0, 180);
    }

    private RotateAnimation getAnimation(int fromDegrees, int toDegrees) {
        RotateAnimation animation = new RotateAnimation(fromDegrees, toDegrees, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(500);
        animation.setFillAfter(true);
        animation.setFillEnabled(true);
        animation.setRepeatCount(0);

        return animation;
    }

    private void handleOnArrowClick() {
        if (popUpIsShow) {
            collapseList();
        } else {
            expandList();
        }
    }

    private void expandList() {
        popUpIsShow = true;
        binding.arrow.startAnimation(expandAnimation);
        showPopUpList();
    }

    private void collapseList() {
        popUpIsShow = false;
        binding.arrow.startAnimation(collapseAnimation);
        popUpList.dismiss();
    }

    private void showPopUpList() {
        int[] location = new int[2];
        binding.getRoot().getLocationInWindow(location);

        int height = binding.getRoot().getHeight() + location[1]
                - (int) getResources().getDimension(R.dimen.app_toolbar_padding);

        popUpList.showAtLocation(popUpLayout, Gravity.TOP, 0, height);
    }

    public void setItems(List<AppToolbarData> items) {
        this.items = items;

        adapter.setItems(items);
        adapter.notifyDataSetChanged();
    }

    public void setClickListener(AppToolbarListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setHeader(String header) {
        this.header.set(header);
    }

    public ObservableField<String> getHeader() {
        return header;
    }

    public void dispose() {
        disposable.dispose();
    }

}
