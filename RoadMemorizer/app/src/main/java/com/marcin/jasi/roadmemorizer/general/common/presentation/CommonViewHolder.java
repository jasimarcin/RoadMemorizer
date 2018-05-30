package com.marcin.jasi.roadmemorizer.general.common.presentation;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

public abstract class CommonViewHolder<B extends ViewDataBinding, VM> extends RecyclerView.ViewHolder {

    protected B binding;

    public CommonViewHolder(B binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public abstract void bind(VM viewModel);
}