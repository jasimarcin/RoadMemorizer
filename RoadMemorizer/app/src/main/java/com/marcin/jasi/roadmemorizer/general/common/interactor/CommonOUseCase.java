package com.marcin.jasi.roadmemorizer.general.common.interactor;


import com.marcin.jasi.roadmemorizer.general.common.schedulers.PostExecutionThread;
import com.marcin.jasi.roadmemorizer.general.common.schedulers.ThreadExecutor;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public abstract class CommonOUseCase<Output> {

    private ThreadExecutor threadExecutor;
    private PostExecutionThread postExecutionThread;

    public CommonOUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    public Observable<Output> getObservable() {
        return buildObservable()
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
    }

    public abstract Observable<Output> buildObservable();
}

