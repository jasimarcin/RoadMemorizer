package com.marcin.jasi.roadmemorizer.roadLoader.presentation.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.marcin.jasi.roadmemorizer.R;
import com.marcin.jasi.roadmemorizer.di.scope.PerFragment;
import com.marcin.jasi.roadmemorizer.general.Constants;
import com.marcin.jasi.roadmemorizer.general.common.presentation.CommonFragment;
import com.marcin.jasi.roadmemorizer.main.MainActivity;
import com.marcin.jasi.roadmemorizer.roadLoader.di.DaggerRoadLoaderComponent;
import com.marcin.jasi.roadmemorizer.roadLoader.presentation.entity.RoadPack;


import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

import static com.marcin.jasi.roadmemorizer.general.Constants.ROADS_LOADER_FRAGMENT_TITLE;
import static com.marcin.jasi.roadmemorizer.general.Constants.ROAD_ID_KEY;
import static com.marcin.jasi.roadmemorizer.general.Constants.SCREENSHOT_ROUTE_PADDING;

@PerFragment
public class RoadLoaderFragment extends CommonFragment {

    public static final String TITLE = ROADS_LOADER_FRAGMENT_TITLE;
    public static final long DEFAULT_ROAD_ID = -1;

    @Inject
    ViewModelProvider.Factory viewModelProvider;

    private CompositeDisposable disposable = new CompositeDisposable();
    private RoadLoaderViewModel viewModel;
    private SupportMapFragment supportMapFragment;

    private Long roadId;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.road_loader_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initDependencies();

        viewModel = ViewModelProviders
                .of(this, viewModelProvider)
                .get(RoadLoaderViewModel.class);

        roadId = DEFAULT_ROAD_ID;
        if (getArguments() != null)
            roadId = getArguments().getLong(ROAD_ID_KEY, DEFAULT_ROAD_ID);

        if (roadId == DEFAULT_ROAD_ID)
            getActivity().onBackPressed();

        setupMapFragment();
    }

    private void initDependencies() {
        DaggerRoadLoaderComponent
                .builder()
                .mainActivityComponent(((MainActivity) getActivity()).getComponent())
                .build()
                .inject(this);
    }

    private void setupMapFragment() {
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_view);
    }

    @Override
    public void onStart() {
        super.onStart();

        disposable.add(viewModel
                .getRoadPack(roadId)
                .subscribe(this::handleRoadPack, Timber::d));
    }

    private void handleRoadPack(RoadPack roadPack) {
        supportMapFragment.getMapAsync(googleMap -> {
            googleMap.clear();

            Polyline polyline = googleMap.addPolyline(new PolylineOptions()
                    .color(ContextCompat.getColor(getContext(), R.color.colorPrimary)));

            polyline.setPoints(roadPack.points());

            setupMarkers(roadPack, googleMap);
            animateToRoad(roadPack, googleMap);

        });
    }

    private void setupMarkers(RoadPack roadPack, GoogleMap googleMap) {
        LatLng startPoint = roadPack.points().get(0);
        LatLng endPoint = roadPack.points().get(roadPack.points().size() - 1);

        Marker startMarker = googleMap.addMarker(new MarkerOptions().position(startPoint));
        Marker endMarker = googleMap.addMarker(new MarkerOptions().position(endPoint));

        disposable.add(loadPointInfo(startPoint, startMarker));
//        disposable.add(loadPointInfo(endPoint, endMarker));
    }

    private Disposable loadPointInfo(LatLng startPoint, Marker startMarker) {
        return viewModel
                .getPlaceId(startPoint)
                .filter(id -> !id.equals(Constants.EMPTY_STRING))
                .flatMap(this::getPlaceFromId)
                .subscribe(place -> {
                    supportMapFragment.getMapAsync(googleMap -> {
                        startMarker.setTitle(place.getName().toString());
                    });
                }, Timber::d);
    }

    public Observable<Place> getPlaceFromId(String placeId) {
        return Observable.create(emitter -> {

            Places.getGeoDataClient(getActivity()).getPlaceById(placeId)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            PlaceBufferResponse places = task.getResult();
                            Place place = places.get(0);
                            places.release();

                            emitter.onNext(place);
                        } else {
                            emitter.onError(new Exception("Place not found"));
                        }
                    });

        });
    }

    private void animateToRoad(RoadPack roadPack, GoogleMap googleMap) {
        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();

        for (LatLng point : roadPack.points())
            boundsBuilder.include(point);

        LatLngBounds bounds = boundsBuilder.build();

        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, SCREENSHOT_ROUTE_PADDING));
    }

    @Override
    protected String getFragmentTitle() {
        return TITLE;
    }

    @Override
    public void onDestroyView() {
        disposable.dispose();
        super.onDestroyView();
    }
}
