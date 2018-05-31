package com.marcin.jasi.roadmemorizer.roadsArchive.domain.interactor;

import io.reactivex.Observable;

import com.marcin.jasi.roadmemorizer.general.common.interactor.CommonOUseCase;
import com.marcin.jasi.roadmemorizer.general.common.schedulers.PostExecutionThread;
import com.marcin.jasi.roadmemorizer.general.common.schedulers.ThreadExecutor;
import com.marcin.jasi.roadmemorizer.roadsArchive.domain.entity.Road;
import com.marcin.jasi.roadmemorizer.roadsArchive.domain.repository.RoadArchiveRepository;

import java.util.List;

public class GetRoadsListUseCase extends CommonOUseCase<List<Road>> {

    private RoadArchiveRepository repository;

    public GetRoadsListUseCase(ThreadExecutor threadExecutor,
                               PostExecutionThread postExecutionThread,
                               RoadArchiveRepository repository) {

        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }


    @Override
    public Observable<List<Road>> buildObservable() {
        return repository.getRoads();
    }
}
