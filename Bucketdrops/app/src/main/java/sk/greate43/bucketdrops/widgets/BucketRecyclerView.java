package sk.greate43.bucketdrops.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import sk.greate43.bucketdrops.extras.Util;

/**
 * Created by great on 8/25/2016.
 */
public class BucketRecyclerView extends RecyclerView {

    private List<View> NonEmptyViews = Collections.emptyList();
    private List<View> EmptyViews = Collections.emptyList();
    private AdapterDataObserver dataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            toggleViews();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            toggleViews();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            toggleViews();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            toggleViews();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            toggleViews();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            toggleViews();
        }
    };

    public BucketRecyclerView(Context context) {
        super(context);
    }

    public BucketRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BucketRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void toggleViews() {
        if (getAdapter() != null && !EmptyViews.isEmpty() && !NonEmptyViews.isEmpty()) {
            if (getAdapter().getItemCount() == 0) {

                //show all the empty views
                Util.showViews(EmptyViews);
                //hide the RecyclerView
                setVisibility(View.GONE);

                //hide all the views which are meant to be hidden
                Util.hideViews(NonEmptyViews);
            } else {
                //hide all the empty views
                Util.showViews(NonEmptyViews);

                //show the RecyclerView
                setVisibility(View.VISIBLE);

                //hide all the views which are meant to be hidden
                Util.hideViews(EmptyViews);
            }
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        if (adapter != null) {
            adapter.registerAdapterDataObserver(dataObserver);
            dataObserver.onChanged();
        }
    }

    public void hideifempty(View... views) {
        NonEmptyViews = Arrays.asList(views);
    }


    public void showifempty(View... emptyViews) {
        EmptyViews = Arrays.asList(emptyViews);
    }
}
