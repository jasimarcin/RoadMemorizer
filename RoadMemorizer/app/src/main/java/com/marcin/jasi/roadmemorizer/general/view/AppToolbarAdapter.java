package com.marcin.jasi.roadmemorizer.general.view;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.marcin.jasi.roadmemorizer.R;
import com.marcin.jasi.roadmemorizer.databinding.RowAppToolbarBinding;

import java.util.List;

public class AppToolbarAdapter extends RecyclerView.Adapter<AppToolbarViewHolder> {

    interface RowClickListener {
        void onItemClick(AppToolbarData item);
    }

    private List<AppToolbarData> items;
    private RowClickListener listener;

    public AppToolbarAdapter(List<AppToolbarData> items, RowClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AppToolbarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RowAppToolbarBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.row_app_toolbar, parent, false);

        return new AppToolbarViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AppToolbarViewHolder holder, int position) {
        holder.bind(new RowAppToolbarViewModel(items.get(position)));
    }

    @Override
    public int getItemCount() {
        if (items != null)
            return items.size();
        else
            return 0;
    }
}
