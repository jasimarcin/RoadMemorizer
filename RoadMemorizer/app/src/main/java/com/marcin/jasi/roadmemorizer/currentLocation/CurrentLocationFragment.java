package com.marcin.jasi.roadmemorizer.currentLocation;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.marcin.jasi.roadmemorizer.R;
import com.marcin.jasi.roadmemorizer.general.common.presentation.CommonFragment;

public class CurrentLocationFragment extends CommonFragment implements OnMapReadyCallback {

    public static final String TITLE = "Aktualna lokalizacja";

    private CurrentLocationViewModel viewModel;

    public static CurrentLocationFragment newInstance() {
        return new CurrentLocationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.current_location_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders
                .of(this)
                .get(CurrentLocationViewModel.class);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map_view);
        supportMapFragment.getMapAsync(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);

        LatLng myPlace = new LatLng(40.73, -73.99);

        googleMap.addPolyline(new PolylineOptions()
                .add(new LatLng(40.73, -73.99))
                .add(new LatLng(40.74, -73.98))
                .add(new LatLng(40.74, -73.97))
                .add(new LatLng(40.75, -73.97))
                .add(new LatLng(40.75, -73.96))
                .add(new LatLng(40.76, -73.96))
                .add(new LatLng(40.77, -73.92))

        );

        googleMap.addMarker(new MarkerOptions()
                .position(myPlace)
        );

        googleMap.animateCamera(CameraUpdateFactory
                .newLatLngZoom(myPlace, 17.0f));

    }

    @Override
    protected String getFragmentTitle() {
        return TITLE;
    }

    @Override
    public void onResume() {
//        start rx interval
        super.onResume();
    }

    @Override
    public void onPause() {
//        dispose
        super.onPause();
    }



//        googleMap.snapshot(bitmap -> {     });
    // https://github.com/akexorcist/Android-GoogleDirectionLibrary
}
