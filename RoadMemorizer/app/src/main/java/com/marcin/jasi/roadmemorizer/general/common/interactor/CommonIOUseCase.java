package com.marcin.jasi.roadmemorizer.general.common.interactor;

import com.marcin.jasi.roadmemorizer.general.common.schedulers.PostExecutionThread;
import com.marcin.jasi.roadmemorizer.general.common.schedulers.ThreadExecutor;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;


public abstract class CommonIOUseCase<Output, Input> {

    private ThreadExecutor threadExecutor;
    private PostExecutionThread postExecutionThread;

    public CommonIOUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    public Observable<Output> getObservable(Input item) {
        return buildObservable(item)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
    }

    public abstract Observable<Output> buildObservable(Input item);
}
