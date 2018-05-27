package com.marcin.jasi.roadmemorizer.roadsArchive;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marcin.jasi.roadmemorizer.R;
import com.marcin.jasi.roadmemorizer.general.common.presentation.CommonFragment;

public class RoadsArchiveFragment extends CommonFragment {

    public static final String TITLE = "Zapisane trasy";

    private RoadsArchiveViewModel mViewModel;

    public static RoadsArchiveFragment newInstance() {
        return new RoadsArchiveFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.roads_archive_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RoadsArchiveViewModel.class);
    }

    @Override
    protected String getFragmentTitle() {
        return TITLE;
    }

}
