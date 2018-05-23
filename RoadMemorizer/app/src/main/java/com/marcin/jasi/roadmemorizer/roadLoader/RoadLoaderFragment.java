package com.marcin.jasi.roadmemorizer.roadLoader;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marcin.jasi.roadmemorizer.R;

public class RoadLoaderFragment extends Fragment {

    private RoadLoaderViewModel mViewModel;

    public static RoadLoaderFragment newInstance() {
        return new RoadLoaderFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.road_loader_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RoadLoaderViewModel.class);
    }

}
