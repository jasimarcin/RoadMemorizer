package com.marcin.jasi.roadmemorizer.general.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.jakewharton.rxbinding2.view.RxView;
import com.marcin.jasi.roadmemorizer.R;
import com.marcin.jasi.roadmemorizer.databinding.AppToolbarBinding;
import com.marcin.jasi.roadmemorizer.general.common.VerticalRecyclerDivider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

// todo AppToolbar
// implement mvvm
// add view model
// add opportunity to set list
// add callback to set list
public class AppToolbar extends LinearLayout {

    private AppToolbarBinding binding;
    private View popUpLayout;
    private PopupWindow popUpList;
    private RotateAnimation collapseAnimation;
    private RotateAnimation expandAnimation;
    private boolean popUpIsShow;

    private CompositeDisposable disposable;

    public AppToolbar(Context context) {
        this(context, null);
    }

    public AppToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public AppToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.app_toolbar, this, true);

        initPopUpList();
        setOnArrowClickListener();

        collapseAnimation = new RotateAnimation(180, 0, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        collapseAnimation.setDuration(500);
        collapseAnimation.setFillAfter(true);
        collapseAnimation.setFillEnabled(true);
        collapseAnimation.setRepeatCount(0);

        expandAnimation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        expandAnimation.setDuration(500);
        expandAnimation.setFillAfter(true);
        expandAnimation.setFillEnabled(true);
        expandAnimation.setRepeatCount(0);
    }

    private void initPopUpList() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        popUpLayout = inflater.inflate(R.layout.pop_up_layout, findViewById(R.id.relative_layout));

        RecyclerView recyclerView = popUpLayout.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<AppToolbarData> dataList = new ArrayList<>();
        dataList.add(new AppToolbarData(0, "number one"));
        dataList.add(new AppToolbarData(1, "number two"));
        dataList.add(new AppToolbarData(2, "number three"));

        AppToolbarAdapter adapter = new AppToolbarAdapter(dataList, null);

        recyclerView.setAdapter(adapter);

        VerticalRecyclerDivider divider = new VerticalRecyclerDivider(1);
        recyclerView.addItemDecoration(divider);

        popUpList = new PopupWindow(popUpLayout,
                ViewPager.LayoutParams.MATCH_PARENT,
                ViewPager.LayoutParams.WRAP_CONTENT,
                true);

        popUpList.setOnDismissListener(this::handleOnArrowClick);
        popUpList.setAnimationStyle(R.style.PopUpAnimationStyle);
    }

    private void setOnArrowClickListener() {
        disposable.add(
                RxView.clicks(binding.arrow)
                        .throttleFirst(500, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(view -> handleOnArrowClick(),
                                throwable -> throwable.printStackTrace()));
    }

    private void handleOnArrowClick() {
        if (popUpIsShow) {
            popUpIsShow = false;
            binding.arrow.startAnimation(collapseAnimation);
            popUpList.dismiss();
        } else {
            popUpIsShow = true;
            binding.arrow.startAnimation(expandAnimation);
            showPopUpList();
        }
    }

    private void showPopUpList() {
        int[] location = new int[2];
        binding.getRoot().getLocationInWindow(location);

        int height = binding.getRoot().getHeight() + location[1]
                - (int) getResources().getDimension(R.dimen.app_toolbar_padding);

        popUpList.showAtLocation(popUpLayout, Gravity.TOP, 0, height);
    }

    public void dipose() {
        disposable.dispose();
    }

}
