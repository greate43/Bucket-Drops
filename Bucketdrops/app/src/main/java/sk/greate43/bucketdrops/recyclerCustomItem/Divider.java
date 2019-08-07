package sk.greate43.bucketdrops.recyclerCustomItem;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import sk.greate43.bucketdrops.R;
import sk.greate43.bucketdrops.adatpors.AdapterDrops;

/**
 * Created by great on 8/28/2016.
 */

public class Divider extends RecyclerView.ItemDecoration {
    public static final String TAG = "Salman";
    private Drawable drawable;
    private int Orientation;

    public Divider(Context context, int orientation) {
        drawable = ContextCompat.getDrawable(context, R.drawable.divider);
        if (orientation != LinearLayoutManager.VERTICAL) {
            throw new IllegalArgumentException("This Item Decoration can be used only with a RecyclerView that uses a LinearLayoutManager with vertical orientation");
        }
        Orientation = orientation;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (Orientation == LinearLayoutManager.VERTICAL) {
            drawHorizontalDivider(c, parent, state);
        }
    }

    private void drawHorizontalDivider(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left, top, right, bottom;
        left = parent.getPaddingLeft();
        right = parent.getWidth() - parent.getPaddingRight();
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            if (AdapterDrops.FOOTER != parent.getAdapter().getItemViewType(i)) {
                View current = parent.getChildAt(i);
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) current.getLayoutParams();
                top = current.getTop() - params.topMargin;
                bottom = top + drawable.getIntrinsicHeight();
                drawable.setBounds(left, top, right, bottom);
                drawable.draw(c);
                Log.d(TAG, "drawHorizontalDivider: l:" + left + " t: " + top + " r: " + right + " b: " + bottom);
            }
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (Orientation == LinearLayoutManager.VERTICAL) {
            outRect.set(0, 0, 0, drawable.getIntrinsicHeight());
        }
    }
}