package com.marcin.jasi.roadmemorizer.currentLocation.presentation;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event.GetCurrentEvent;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event.UnconnectReceiverEvent;
import com.marcin.jasi.roadmemorizer.currentLocation.presentation.entity.AlignMap;
import com.marcin.jasi.roadmemorizer.currentLocation.presentation.entity.CurrentLocationViewState;
import com.marcin.jasi.roadmemorizer.currentLocation.presentation.entity.UpdatePointViewState;
import com.marcin.jasi.roadmemorizer.currentLocation.presentation.entity.UpdateRoadViewState;
import com.marcin.jasi.roadmemorizer.databinding.CurrentLocationFragmentBinding;
import com.marcin.jasi.roadmemorizer.di.scope.PerFragment;
import com.marcin.jasi.roadmemorizer.general.common.presentation.CommonFragment;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


// todo big refactor
// todo start / stop recording
// todo after recording screenshot

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
            if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE
                    && viewModel.gotLastLocation()) {
                viewModel.setCameraMoved(true);
            }
        }));
        binding.setViewModel(viewModel);
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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::render);
    }

    private void render(CurrentLocationViewState event) {

        if (event instanceof UpdateRoadViewState) {
            updateRoadMapView(((UpdateRoadViewState) event));
        } else if (event instanceof UpdatePointViewState) {
            updatePointMapView((UpdatePointViewState) event);
        } else if (event instanceof AlignMap) {
            alignCameraToLocation(((AlignMap) event).getAlignPoint());
        }
    }

    private void updateRoadMapView(UpdateRoadViewState pointViewState) {
        supportMapFragment.getMapAsync((GoogleMap googleMap) -> {
            setStartMarker(googleMap, pointViewState.getStartPoint());
            setEndPoint(googleMap, pointViewState.getEndPoint());
            setPolyline(googleMap, pointViewState.getRoad());

            if (pointViewState.isAlign()) {
                alignCameraToLocation(pointViewState.getEndPoint());
            }
        });
    }

    private void setPolyline(GoogleMap googleMap, List<LatLng> points) {
        if (polyline == null && points != null && points.size() > 0) {
            polyline = googleMap.addPolyline(new PolylineOptions()
                    .color(ContextCompat.getColor(getContext(), R.color.colorPrimary)));
            polyline.setPoints(points);
            polyline.setVisible(true);
        }
    }

    private void setEndPoint(GoogleMap googleMap, LatLng point) {
        if (endMarker == null && point != null) {
            endMarker = googleMap.addMarker(new MarkerOptions().position(point));
            endMarker.setVisible(true);
        } else if (endMarker != null && point != null) {
            endMarker.setPosition(point);
            endMarker.setVisible(true);
        }
    }

    private void setStartMarker(GoogleMap googleMap, LatLng point) {
        if (startMarker == null && point != null) {
            startMarker = googleMap.addMarker(new MarkerOptions().position(point));
            startMarker.setVisible(true);
        } else if (startMarker != null && point != null) {
            startMarker.setPosition(point);
            startMarker.setVisible(true);
        }
    }

    private void updatePointMapView(UpdatePointViewState pointViewState) {
        supportMapFragment.getMapAsync((GoogleMap googleMap) -> {

            setStartMarker(googleMap, pointViewState.getPoint());

            if (endMarker != null)
                endMarker.setVisible(false);

            if (polyline != null)
                polyline.setVisible(false);

            if (pointViewState.isAlign()) {
                alignCameraToLocation(pointViewState.getPoint());
            }
        });
    }

    private void alignCameraToLocation(LatLng location) {
        if (location == null)
            return;

        supportMapFragment.getMapAsync(googleMap -> {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15.0f));
        });
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        CurrentLocationViewState viewState = viewModel.getLastState();
        render(viewState);
    }

    @Override
    protected String getFragmentTitle() {
        return TITLE;
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.init();
        disposable.add(handleMapEvents());
    }

    @Override
    public void onStop() {
        viewModel.dispose();
        disposable.dispose();
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.callEvent(new GetCurrentEvent());
    }

    @Override
    public void onPause() {
        viewModel.callEvent(new UnconnectReceiverEvent());
        super.onPause();
    }

    //        googleMap.snapshot(bitmap -> {     });
    // https://github.com/akexorcist/Android-GoogleDirectionLibrary
}
