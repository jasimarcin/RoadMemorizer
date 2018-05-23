package com.marcin.jasi.roadmemorizer.currentLocation;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marcin.jasi.roadmemorizer.R;

public class CurrentLocationFragment extends Fragment {

    private CurrentLocationViewModel viewModel;

    public static CurrentLocationFragment newInstance() {
        return new CurrentLocationFragment();
    }

    @Nullable
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
    }

}
