package com.marcin.jasi.roadmemorizer.general.view;

import android.support.v7.widget.RecyclerView;

import com.marcin.jasi.roadmemorizer.databinding.RowAppToolbarBinding;

public class AppToolbarViewHolder extends RecyclerView.ViewHolder {

    private RowAppToolbarBinding binding;

    public AppToolbarViewHolder(RowAppToolbarBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(RowAppToolbarViewModel viewModel) {
        binding.setViewModel(viewModel);
    }
}
