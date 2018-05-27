package com.marcin.jasi.roadmemorizer.general.common.data;

import io.reactivex.subjects.PublishSubject;

public class GpsTrackerMediator {

    private PublishSubject<Boolean> gpsEnableChange = PublishSubject.create();

    public PublishSubject<Boolean> getGpsEnableChange() {
        return gpsEnableChange;
    }
}
