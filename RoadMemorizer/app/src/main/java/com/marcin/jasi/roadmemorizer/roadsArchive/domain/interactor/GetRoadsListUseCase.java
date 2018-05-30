package com.marcin.jasi.roadmemorizer.roadsArchive.domain.interactor;

import io.reactivex.Observable;

import com.marcin.jasi.roadmemorizer.roadsArchive.domain.entity.Road;
import com.marcin.jasi.roadmemorizer.roadsArchive.domain.repository.RoadArchiveRepository;

import java.util.List;

public class GetRoadsListUseCase {

    private RoadArchiveRepository repository;

    public GetRoadsListUseCase(RoadArchiveRepository repository) {
        this.repository = repository;
    }

    public Observable<List<Road>> getRoads() {
        return Observable.fromCallable(() -> repository.getRoads());
    }

}
