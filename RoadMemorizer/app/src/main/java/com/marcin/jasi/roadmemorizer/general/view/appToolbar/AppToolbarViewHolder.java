package com.marcin.jasi.roadmemorizer.general.view.appToolbar;


import com.marcin.jasi.roadmemorizer.databinding.RowAppToolbarBinding;
import com.marcin.jasi.roadmemorizer.general.common.presentation.CommonViewHolder;

public class AppToolbarViewHolder extends CommonViewHolder<RowAppToolbarBinding, RowAppToolbarViewModel> {

    public AppToolbarViewHolder(RowAppToolbarBinding binding) {
        super(binding);
    }

    public void bind(RowAppToolbarViewModel viewModel) {
        binding.setViewModel(viewModel);
    }
}
