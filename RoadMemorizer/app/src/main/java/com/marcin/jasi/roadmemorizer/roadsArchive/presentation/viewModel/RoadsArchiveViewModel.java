package com.marcin.jasi.roadmemorizer.roadsArchive.presentation.viewModel;

import android.arch.lifecycle.ViewModel;

import com.marcin.jasi.roadmemorizer.roadsArchive.domain.interactor.GetRoadsListUseCase;
import com.marcin.jasi.roadmemorizer.roadsArchive.domain.entity.Road;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


public class RoadsArchiveViewModel extends ViewModel {

    @Inject
    GetRoadsListUseCase getRoadsListUseCase;


    @Inject
    public RoadsArchiveViewModel() {
    }

    public Observable<List<Road>> getRoads() {
        return getRoadsListUseCase.getRoads();
    }

}
