package com.marcin.jasi.roadmemorizer.roadLoader;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;
import com.marcin.jasi.roadmemorizer.R;
import com.marcin.jasi.roadmemorizer.general.common.presentation.CommonFragment;

public class RoadLoaderFragment extends CommonFragment {

    public static final String TITLE = "Zapisana trasa";
    public static final String ROAD_ID_KEY = "roadIdBundleKey";

    private RoadLoaderViewModel mViewModel;

    private Marker startMarker;
    private Marker endMarker;
    private Polyline polyline;

    private SupportMapFragment supportMapFragment;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.road_loader_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RoadLoaderViewModel.class);
        setupMapFragment();
    }

    private void setupMapFragment() {
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_view);
    }

    @Override
    protected String getFragmentTitle() {
        return TITLE;
    }

}
