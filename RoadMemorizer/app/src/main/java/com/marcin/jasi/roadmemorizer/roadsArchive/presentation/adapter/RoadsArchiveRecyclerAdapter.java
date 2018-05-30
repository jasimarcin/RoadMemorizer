package com.marcin.jasi.roadmemorizer.roadsArchive.presentation.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.marcin.jasi.roadmemorizer.R;
import com.marcin.jasi.roadmemorizer.databinding.RoadRowBinding;
import com.marcin.jasi.roadmemorizer.general.common.data.DataMapper;
import com.marcin.jasi.roadmemorizer.roadsArchive.domain.entity.Road;
import com.marcin.jasi.roadmemorizer.roadsArchive.presentation.RoadsArchiveViewHolder;
import com.marcin.jasi.roadmemorizer.roadsArchive.presentation.viewModel.RoadRowViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RoadsArchiveRecyclerAdapter extends RecyclerView.Adapter<RoadsArchiveViewHolder> {

    private DataMapper<Road, RoadRowViewModel> entityMapper;
    private List<Road> items = new ArrayList<>();


    public RoadsArchiveRecyclerAdapter(DataMapper<Road, RoadRowViewModel> entityMapper) {
        this.entityMapper = entityMapper;
    }

    @NonNull
    @Override
    public RoadsArchiveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RoadRowBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.road_row, parent, false);

        return new RoadsArchiveViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RoadsArchiveViewHolder holder, int position) {
        holder.bind(entityMapper.transform(items.get(position)));
//        holder.itemView.setOnClickListener();
    }

    @Override
    public int getItemCount() {
        if (items != null)
            return items.size();
        else
            return 0;
    }

    @NonNull
    public DiffUtil.DiffResult getDiffResult(final List<Road> newItems) {
        return DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return items.size();
            }

            @Override
            public int getNewListSize() {
                return newItems.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return items.get(oldItemPosition).getId() == newItems.get(newItemPosition).getId();
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return items.get(oldItemPosition).getEndPointLatitude() == newItems.get(newItemPosition).getEndPointLatitude() &&
                        items.get(oldItemPosition).getEndPointLongitude() == newItems.get(newItemPosition).getEndPointLongitude() &&
                        items.get(oldItemPosition).getStartPointLatitude() == newItems.get(newItemPosition).getStartPointLatitude() &&
                        items.get(oldItemPosition).getStartPointLongitude() == newItems.get(newItemPosition).getStartPointLongitude() &&
                        items.get(oldItemPosition).getFilePath().equals(newItems.get(newItemPosition).getFilePath());
            }
        });
    }

    public Disposable updateList(List<Road> data) {
        return Observable.just(data)
                .observeOn(Schedulers.computation())
                .map(list -> new Pair<>(list, getDiffResult(list)))
                .map(resultPair -> {
                    items = resultPair.first;
                    return resultPair.second;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> result.dispatchUpdatesTo(this));
    }
}
