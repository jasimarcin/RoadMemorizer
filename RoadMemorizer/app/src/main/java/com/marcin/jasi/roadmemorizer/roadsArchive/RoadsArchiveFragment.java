package com.marcin.jasi.roadmemorizer.roadsArchive;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marcin.jasi.roadmemorizer.R;

public class RoadsArchiveFragment extends Fragment {

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
        // TODO: Use the ViewModel
    }

}
