package com.marcin.jasi.roadmemorizer.general.common.presentation;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class LinearLayoutManagerWrapper extends LinearLayoutManager {

    private int mPendingScrollPosition = RecyclerView.NO_POSITION;
    private int mPendingScrollOffset;

    public LinearLayoutManagerWrapper(Context context) {
        super(context);
    }

    public LinearLayoutManagerWrapper(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public LinearLayoutManagerWrapper(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.getItemCount() == 0) {
            return;
        }
        final int anchorItemPosition = getAnchorItemPosition(state);

        Log.d("Anchor item pos", "Pos" + anchorItemPosition);
        if (anchorItemPosition >= state.getItemCount()) {
            return;
        }

        setPendingScrollPositionWithOffset(RecyclerView.NO_POSITION, 0);
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            Log.d("LinearLayoutManWrapper", "Inconsistency bug");
        }
    }

    protected int getAnchorItemPosition(RecyclerView.State state) {
        final int itemCount = state.getItemCount();

        int pendingPosition = getPendingScrollPosition();
        if (pendingPosition != RecyclerView.NO_POSITION) {
            if (pendingPosition < 0 || pendingPosition >= itemCount) {
                pendingPosition = RecyclerView.NO_POSITION;
            }
        }

        if (pendingPosition != RecyclerView.NO_POSITION) {
            return pendingPosition;
        } else if (getChildCount() > 0) {
            return findFirstValidChildPosition(itemCount);
        } else {
            return 0;
        }
    }

    private int findFirstValidChildPosition(int itemCount) {
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View view = getChildAt(i);
            final int position = getPosition(view);
            if (position >= 0 && position < itemCount) {
                return position;
            }
        }

        return 0;
    }

    protected int getPendingScrollPosition() {
        return mPendingScrollPosition;
    }

    protected void setPendingScrollPositionWithOffset(int position, int offset) {
        mPendingScrollPosition = position;
        mPendingScrollOffset = offset;
    }
}
