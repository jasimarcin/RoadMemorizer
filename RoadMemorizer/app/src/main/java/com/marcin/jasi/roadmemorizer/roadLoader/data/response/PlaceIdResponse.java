package com.marcin.jasi.roadmemorizer.roadLoader.data.response;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;


@AutoValue
public abstract class PlaceIdResponse {

    @SerializedName("status")
    public abstract String status();

    @Nullable
    @SerializedName("results")
    public abstract List<PlaceResultResponse> results();

    @Nullable
    @SerializedName("result")
    public abstract PlaceResultResponse result();

    public final List<PlaceResultResponse> resultsList() {
        return Collections.unmodifiableList(results());
    }

    public static TypeAdapter<PlaceIdResponse> typeAdapter(Gson gson) {
        return new AutoValue_PlaceIdResponse.GsonTypeAdapter(gson)
                .setDefaultResult(null)
                .setDefaultResults(null);
    }

}
