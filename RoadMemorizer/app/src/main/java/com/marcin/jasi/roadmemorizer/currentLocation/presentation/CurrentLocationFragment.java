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
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.marcin.jasi.roadmemorizer.Application;
import com.marcin.jasi.roadmemorizer.R;
import com.marcin.jasi.roadmemorizer.currentLocation.di.DaggerCurrentLocationComponent;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event.AlignClickIntent;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event.CurrentLocationIntent;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event.MoveCameraIntent;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event.SavingButtonClickIntent;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event.ScreenshotGeneratedIntent;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event.UnconnectReceiverIntent;
import com.marcin.jasi.roadmemorizer.currentLocation.presentation.entity.AlignMap;
import com.marcin.jasi.roadmemorizer.currentLocation.presentation.entity.CurrentLocationViewState;
import com.marcin.jasi.roadmemorizer.currentLocation.presentation.entity.GenerateScreenshotViewState;
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

import static com.marcin.jasi.roadmemorizer.general.Constants.CURRENT_LOCATION_FRAGMENT_TITLE;


// todo big refactor
// todo after recording screenshot

@PerFragment
public class CurrentLocationFragment extends CommonFragment {

    public static final String TITLE = CURRENT_LOCATION_FRAGMENT_TITLE;
    public static final float ZOOM = 15.0f;
    public static final int SCREENSHOT_ROUTE_PADDING = 100;

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


        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.current_location_fragment,
                container, false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initDependencies();

        viewModel = ViewModelProviders
                .of(this, viewModelProvider)
                .get(CurrentLocationViewModel.class);

        setupMapFragment();
        addButtonsCallbacks();
    }

    private void initDependencies() {
        DaggerCurrentLocationComponent
                .builder()
                .applicationComponent(((Application) getActivity().getApplication()).getApplicationComponent())
                .build()
                .inject(this);
    }

    private void setupMapFragment() {
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_view);
        supportMapFragment.getMapAsync(googleMap -> googleMap.setOnCameraMoveStartedListener(reason -> {
            if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
                viewModel.callEvent(new MoveCameraIntent());
            }
        }));
    }

    private void addButtonsCallbacks() {
        binding.alignButton.setOnClickListener(v -> viewModel.callEvent(new AlignClickIntent()));
        binding.saveButton.setOnClickListener(v -> viewModel.callEvent(new SavingButtonClickIntent()));
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.init();
        disposable.add(handleMapEvents());
    }

    @NonNull
    private Disposable handleMapEvents() {
        return viewModel.getMapEvents()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::render);
    }

    private void render(CurrentLocationViewState event) {

        if (event instanceof GenerateScreenshotViewState) {
            takeScreenshot((GenerateScreenshotViewState) event);
            return;
        }

        if (event instanceof UpdateRoadViewState) {
            updateRoadMapView(((UpdateRoadViewState) event));
        } else if (event instanceof UpdatePointViewState) {
            updatePointMapView((UpdatePointViewState) event);
        } else if (event instanceof AlignMap) {
            alignCameraToLocation(((AlignMap) event).getAlignPoint());
        }

        updateAlignButton(event);
        updateSavingButton(event);
    }

    private void takeScreenshot(GenerateScreenshotViewState event) {
        supportMapFragment.getMapAsync(googleMap -> {

            LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
            for (LatLng point : event.getRoad())
                boundsBuilder.include(point);

            LatLngBounds bounds = boundsBuilder.build();
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, SCREENSHOT_ROUTE_PADDING));

            googleMap.snapshot(bitmap -> {
                viewModel.callEvent(new ScreenshotGeneratedIntent(event.getScreenshotFileName(), bitmap));
            });
        });
    }

    private void updateRoadMapView(UpdateRoadViewState pointViewState) {
        supportMapFragment.getMapAsync((GoogleMap googleMap) -> {
            setEndPoint(googleMap, pointViewState.getEndPoint());
            setStartMarker(googleMap, pointViewState.getStartPoint());
            setPolyline(googleMap, pointViewState.getRoad());

            if (pointViewState.isAlign()) {
                alignCameraToLocation(pointViewState.getEndPoint());
            }
        });
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

    private void updateSavingButton(CurrentLocationViewState event) {
        if (event.isShowSaveButton()) {
            binding.saveButton.setVisibility(View.VISIBLE);
            binding.saveButton.setText(getString(R.string.save_button_text));
        } else if (event.isShowStopSavingButton()) {
            binding.saveButton.setVisibility(View.VISIBLE);
            binding.saveButton.setText(getString(R.string.start_saving_button_text));
        } else
            binding.saveButton.setVisibility(View.GONE);
    }

    private void alignCameraToLocation(LatLng location) {
        if (location == null)
            return;

        supportMapFragment.getMapAsync(googleMap -> {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, ZOOM));
        });
    }

    private void updateAlignButton(CurrentLocationViewState event) {
        if (event.isShowAligmButton()) {
            binding.alignButton.setVisibility(View.VISIBLE);
        } else {
            binding.alignButton.setVisibility(View.GONE);
        }
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
    public void onDestroy() {
        viewModel.dispose();
        disposable.dispose();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.callEvent(new CurrentLocationIntent());
    }

    @Override
    public void onPause() {
        viewModel.callEvent(new UnconnectReceiverIntent());
        super.onPause();
    }

    //        googleMap.snapshot(bitmap -> {     });
    // https://github.com/akexorcist/Android-GoogleDirectionLibrary
}
