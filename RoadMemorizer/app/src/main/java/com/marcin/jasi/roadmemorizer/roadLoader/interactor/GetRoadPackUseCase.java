package com.marcin.jasi.roadmemorizer.roadLoader.interactor;

import com.marcin.jasi.roadmemorizer.database.LocationDatabaseRepository;
import com.marcin.jasi.roadmemorizer.general.common.interactor.CommonIOUseCase;
import com.marcin.jasi.roadmemorizer.general.common.schedulers.PostExecutionThread;
import com.marcin.jasi.roadmemorizer.general.common.schedulers.ThreadExecutor;
import com.marcin.jasi.roadmemorizer.roadLoader.presentation.entity.RoadPack;
import com.marcin.jasi.roadmemorizer.roadsArchive.domain.repository.RoadArchiveRepository;


import io.reactivex.Observable;

public class GetRoadPackUseCase extends CommonIOUseCase<RoadPack, Long> {

    private RoadArchiveRepository repository;
    private LocationDatabaseRepository pointsRepository;

    public GetRoadPackUseCase(ThreadExecutor threadExecutor,
                              PostExecutionThread postExecutionThread,
                              RoadArchiveRepository repository,
                              LocationDatabaseRepository pointsRepository) {

        super(threadExecutor, postExecutionThread);
        this.repository = repository;
        this.pointsRepository = pointsRepository;
    }

    @Override
    public Observable<RoadPack> buildObservable(Long roadId) {
        return repository.getRoad(roadId)
                .flatMap(road -> pointsRepository.getRoadPoints(roadId)
                        .map(points -> RoadPack.builder()
                                .points(points)
                                .road(road).build()));
    }
}
