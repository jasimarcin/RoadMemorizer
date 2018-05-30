package com.marcin.jasi.roadmemorizer.roadsArchive.presentation.viewModel;

import com.marcin.jasi.roadmemorizer.roadsArchive.domain.entity.Road;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RoadRowViewModel {

    private Road model;

    public RoadRowViewModel(Road model) {
        this.model = model;
    }

    public String getImageFilePath() {
        return model.getFilePath();
    }

    public String getPiontsQuantityRow() {
        return String.format("Punktów: %s", model.getPointsQuantity());
    }

    public String getDateRow() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(model.getDate());

        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = parser.format(calendar.getTime());

        return String.format("Data: %s", date);
    }
}
