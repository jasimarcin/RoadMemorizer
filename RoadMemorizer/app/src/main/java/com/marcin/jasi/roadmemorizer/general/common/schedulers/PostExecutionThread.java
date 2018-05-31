package com.marcin.jasi.roadmemorizer.general.common.schedulers;

import io.reactivex.Scheduler;

public interface PostExecutionThread {
    Scheduler getScheduler();
}
