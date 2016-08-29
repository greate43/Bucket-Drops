package sk.greate43.bucketdrops.recyclerCustomItem;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import sk.greate43.bucketdrops.interfaces.SwipeListener;

/**
 * Created by great on 8/29/2016.
 */

public class SimpleTouchCallback extends ItemTouchHelper.Callback{
    private SwipeListener swipeListener;
    public SimpleTouchCallback(SwipeListener Listener) {
     swipeListener=Listener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0,ItemTouchHelper.END);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            swipeListener.OnSwipe(viewHolder.getAdapterPosition());
    }
}
