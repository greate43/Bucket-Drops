package sk.greate43.bucketdrops.extras;

import android.view.View;

import java.util.List;

/**
 * Created by great on 8/25/2016.
 */
public class Util {
    public static void showViews(List<View> views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void hideViews(List<View> views) {
        for (View view : views) {
            view.setVisibility(View.GONE);
        }
    }
    }