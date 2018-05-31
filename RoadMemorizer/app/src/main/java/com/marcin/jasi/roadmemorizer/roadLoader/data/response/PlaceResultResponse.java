package com.marcin.jasi.roadmemorizer.roadLoader.data.response;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class PlaceResultResponse {

    @SerializedName("place_id")
    public abstract String placeId();

    @SerializedName("formatted_address")
    public abstract String address();


    public static TypeAdapter<PlaceResultResponse> typeAdapter(Gson gson) {
        return new AutoValue_PlaceResultResponse.GsonTypeAdapter(gson);
    }

}
