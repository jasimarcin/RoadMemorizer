package com.marcin.jasi.roadmemorizer.general.common.presentation;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class VerticalRecyclerDivider extends RecyclerView.ItemDecoration {

    private int offset;

    public VerticalRecyclerDivider(int offset) {
        this.offset = offset;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (parent.getChildAdapterPosition(view) < parent.getAdapter().getItemCount()-1)
            outRect.bottom = offset;
    }
}
