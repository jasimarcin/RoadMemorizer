package com.marcin.jasi.roadmemorizer.roadsArchive.presentation;

import com.marcin.jasi.roadmemorizer.databinding.RoadRowBinding;
import com.marcin.jasi.roadmemorizer.general.common.presentation.CommonViewHolder;
import com.marcin.jasi.roadmemorizer.roadsArchive.presentation.viewModel.RoadRowViewModel;

public class RoadsArchiveViewHolder extends CommonViewHolder<RoadRowBinding, RoadRowViewModel> {

    public RoadsArchiveViewHolder(RoadRowBinding binding) {
        super(binding);
    }

    @Override
    public void bind(RoadRowViewModel viewModel) {
        binding.setViewModel(viewModel);
    }
}
