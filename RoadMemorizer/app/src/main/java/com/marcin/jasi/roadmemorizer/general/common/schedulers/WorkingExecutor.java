package com.marcin.jasi.roadmemorizer.general.common.schedulers;

import android.support.annotation.NonNull;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WorkingExecutor implements ThreadExecutor {

    public static final int MAXIMUM_POOL_SIZE = 5;
    public static final int THREAD_ALIVE_TIME = 10;
    private final ThreadPoolExecutor threadPoolExecutor;
    public static final int CORE_POOLS_SIZE = 3;

    // background jobs
    public WorkingExecutor() {
        this.threadPoolExecutor = new ThreadPoolExecutor(CORE_POOLS_SIZE, MAXIMUM_POOL_SIZE,
                THREAD_ALIVE_TIME, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), new JobThreadFactory());
    }

    @Override
    public void execute(@NonNull Runnable runnable) {
        this.threadPoolExecutor.execute(runnable);
    }

    private static class JobThreadFactory implements ThreadFactory {
        private int counter = 0;

        @Override
        public Thread newThread(@NonNull Runnable runnable) {
            return new Thread(runnable, "android_" + counter++);
        }
    }

}
