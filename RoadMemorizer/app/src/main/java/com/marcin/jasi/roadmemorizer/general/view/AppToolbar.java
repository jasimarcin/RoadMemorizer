package com.marcin.jasi.roadmemorizer.general.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.marcin.jasi.roadmemorizer.R;
import com.marcin.jasi.roadmemorizer.databinding.AppToolbarBinding;

public class AppToolbar extends LinearLayout {

    private AppToolbarBinding binding;

    public AppToolbar(Context context) {
        this(context, null);
    }

    public AppToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public AppToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.app_toolbar, this, true);
        binding.arrow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


}
