package com.marcin.jasi.roadmemorizer.currentLocation;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.marcin.jasi.roadmemorizer.Application;
import com.marcin.jasi.roadmemorizer.R;
import com.marcin.jasi.roadmemorizer.currentLocation.di.DaggerCurrentLocationComponent;
import com.marcin.jasi.roadmemorizer.currentLocation.presentation.AlignMap;
import com.marcin.jasi.roadmemorizer.currentLocation.presentation.CurrentLocationViewModel;
import com.marcin.jasi.roadmemorizer.currentLocation.presentation.UpdateLocation;
import com.marcin.jasi.roadmemorizer.databinding.CurrentLocationFragmentBinding;
import com.marcin.jasi.roadmemorizer.di.scope.PerFragment;
import com.marcin.jasi.roadmemorizer.general.common.presentation.CommonFragment;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


// todo big refactor
// todo start / stop recording
// todo after recording screenshot
// todo database repo / DS
@PerFragment
public class CurrentLocationFragment extends CommonFragment {

    public static final String TITLE = "Aktualna lokalizacja";

    @Inject
    ViewModelProvider.Factory viewModelProvider;
    private CompositeDisposable disposable = new CompositeDisposable();

    private CurrentLocationViewModel viewModel;
    private CurrentLocationFragmentBinding binding;

    private Marker startMarker;
    private Marker endMarker;
    private Polyline polyline;

    private SupportMapFragment supportMapFragment;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.current_location_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initDependencies();

        viewModel = ViewModelProviders
                .of(this, viewModelProvider)
                .get(CurrentLocationViewModel.class);

        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_view);
        supportMapFragment.getMapAsync(googleMap -> googleMap.setOnCameraMoveStartedListener(reason -> {
            if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
                viewModel.setCameraMoved(true);
            }
        }));
        binding.setViewModel(viewModel);
        viewModel.connect();
    }

    private void initDependencies() {
        DaggerCurrentLocationComponent
                .builder()
                .applicationComponent(((Application) getActivity().getApplication()).getApplicationComponent())
                .build()
                .inject(this);
    }

    @NonNull
    private Disposable handleMapEvents() {
        return viewModel.getMapEvents()
                .subscribe(event -> {
                    if (event instanceof UpdateLocation)
                        updateMapView(((UpdateLocation) event).getLocation());
                    else if (event instanceof AlignMap)
                        supportMapFragment.getMapAsync(googleMap -> {
                            alignCameraToCurrentLocation(viewModel.getLastPoint(), googleMap);
                        });
                });
    }

    private void updateMapView(Pair<LatLng, List<LatLng>> newLocation) {
        supportMapFragment.getMapAsync((GoogleMap googleMap) -> {
            if (startMarker == null) {
                startMarker = googleMap.addMarker(new MarkerOptions().position(newLocation.first));
            } else
                startMarker.setPosition(newLocation.first);

            if (polyline == null)
                polyline = googleMap.addPolyline(new PolylineOptions()
                        .color(ContextCompat.getColor(getContext(), R.color.colorPrimary)));

            polyline.setPoints(newLocation.second);

            if (!viewModel.isCameraMoved().get()) {
                alignCameraToCurrentLocation(newLocation.first, googleMap);
            }
        });
    }

    private void alignCameraToCurrentLocation(LatLng location, GoogleMap googleMap) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15.0f));
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (viewModel.getLastPoint() != null) {
            updateMapView(new Pair<>(viewModel.getLastPoint(), viewModel.getRoadPoints()));
        }
    }

    @Override
    protected String getFragmentTitle() {
        return TITLE;
    }

    @Override
    public void onStart() {
        disposable.add(handleMapEvents());
        super.onStart();
    }

    @Override
    public void onStop() {
        disposable.dispose();
        super.onStop();
    }

    //        googleMap.snapshot(bitmap -> {     });
    // https://github.com/akexorcist/Android-GoogleDirectionLibrary
}
