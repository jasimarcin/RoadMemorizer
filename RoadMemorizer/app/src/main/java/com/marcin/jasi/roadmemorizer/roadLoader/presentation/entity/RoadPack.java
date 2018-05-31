package com.marcin.jasi.roadmemorizer.roadLoader.presentation.entity;

import com.google.android.gms.maps.model.LatLng;
import com.google.auto.value.AutoValue;
import com.marcin.jasi.roadmemorizer.roadsArchive.domain.entity.Road;

import java.util.List;

@AutoValue
public abstract class RoadPack {

    public abstract Road road();

    public abstract List<LatLng> points();

    public static Builder builder() {
        return new AutoValue_RoadPack.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder road(Road road);

        public abstract Builder points(List<LatLng> points);

        public abstract RoadPack build();
    }
}
