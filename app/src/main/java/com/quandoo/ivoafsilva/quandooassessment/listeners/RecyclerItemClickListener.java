package com.quandoo.ivoafsilva.quandooassessment.listeners;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    /**
     * TAG to use when logging
     */
    private static final String TAG = RecyclerItemClickListener.class.getSimpleName();
    /**
     * The instance of {@link OnItemClickListener} that will be called when an item is clicked
     */
    private final OnItemClickListener mListener;
    /**
     * The {@link GestureDetector} that
     */
    private final GestureDetector mGestureDetector;

    public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        Log.w(TAG, "onRequestDisallowInterceptTouchEvent");
    }


    /**
     * Interface to be called when an item within the RecyclerView is clicked
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}