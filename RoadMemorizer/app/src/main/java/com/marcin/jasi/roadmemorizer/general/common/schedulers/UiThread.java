package com.marcin.jasi.roadmemorizer.general.common.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class UiThread implements PostExecutionThread {
    @Override
    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
