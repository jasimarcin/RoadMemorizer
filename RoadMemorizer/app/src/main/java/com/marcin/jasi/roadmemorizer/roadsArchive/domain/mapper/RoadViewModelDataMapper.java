package com.marcin.jasi.roadmemorizer.roadsArchive.domain.mapper;

import com.marcin.jasi.roadmemorizer.general.common.data.DataMapper;
import com.marcin.jasi.roadmemorizer.roadsArchive.domain.entity.Road;
import com.marcin.jasi.roadmemorizer.roadsArchive.presentation.viewModel.RoadRowViewModel;

import java.util.ArrayList;
import java.util.List;

public class RoadViewModelDataMapper implements DataMapper<Road, RoadRowViewModel> {

    @Override
    public RoadRowViewModel transform(Road road) {
        return new RoadRowViewModel(road);
    }

    @Override
    public List<RoadRowViewModel> transform(List<Road> from) {
        List<RoadRowViewModel> list = new ArrayList<>();

        for (Road road : from)
            list.add(transform(road));

        return list;
    }

}
