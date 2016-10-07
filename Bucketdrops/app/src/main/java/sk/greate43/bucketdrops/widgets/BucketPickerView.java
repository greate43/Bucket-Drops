package sk.greate43.bucketdrops.widgets;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import sk.greate43.bucketdrops.R;
import sk.greate43.bucketdrops.application.AppBucketDrops;


/**
 * Created by great on 8/31/2016.
 */

/**
 * Created by vivz on 10/01/16.
 */
public class BucketPickerView extends LinearLayout implements View.OnTouchListener {
    public static final int LEFT = 0;
    public static final int TOP = 1;
    public static final int RIGHT = 2;
    public static final int BOTTOM = 3;
    public static final int DELAY = 250;
    private Calendar calendar;
    private TextView TextDate;
    private TextView TextMonth;
    private TextView TextYear;
    private SimpleDateFormat Formatter;
    private String TAG = "Salman";
    private boolean Increment;
    private boolean Decrement;

    private Drawable UpNormal;
    private Drawable UpPressed;
    private Drawable DownNormal;
    private Drawable DownPressed;
    private int MESSAGE_WHAT = 123;
    private int ActiveId;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (Increment) {
                increment(ActiveId);
            }
            if (Decrement) {
                decrement(ActiveId);
            }
            if (Increment || Decrement) {
                handler.sendEmptyMessageDelayed(MESSAGE_WHAT, DELAY);
            }
            return true;
        }
    });

    public BucketPickerView(Context context) {
        super(context);
        init(context);
    }

    public BucketPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BucketPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.bucket_picker_view, this);
        calendar = Calendar.getInstance();
        Formatter = new SimpleDateFormat("MMM");
        UpNormal = ContextCompat.getDrawable(context, R.drawable.up_normal);
        UpPressed = ContextCompat.getDrawable(context, R.drawable.up_pressed);
        DownNormal = ContextCompat.getDrawable(context, R.drawable.down_normal);
        DownPressed = ContextCompat.getDrawable(context, R.drawable.down_pressed);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Log.d(TAG, "onSaveInstanceState: ");
        Bundle bundle = new Bundle();
        bundle.putParcelable("super", super.onSaveInstanceState());
        bundle.putInt("date", calendar.get(Calendar.DATE));
        bundle.putInt("month", calendar.get(Calendar.MONTH));
        bundle.putInt("year", calendar.get(Calendar.YEAR));
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Log.d(TAG, "onRestoreInstanceState: ");
        if (state instanceof Parcelable) {
            Bundle bundle = (Bundle) state;
            state = bundle.getParcelable("super");
            int date = bundle.getInt("date");
            int month = bundle.getInt("month");
            int year = bundle.getInt("year");
            update(date, month, year, 0, 0, 0);
        }
        super.onRestoreInstanceState(state);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        TextDate = (TextView) this.findViewById(R.id.tv_date);
        TextMonth = (TextView) this.findViewById(R.id.tv_month);
        TextYear = (TextView) this.findViewById(R.id.tv_year);
        AppBucketDrops.setRalewayRegular(getContext(), TextDate, TextMonth, TextYear);
        TextDate.setOnTouchListener(this);
        TextMonth.setOnTouchListener(this);
        TextYear.setOnTouchListener(this);
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        update(date, month, year, 0, 0, 0);
    }

    private void update(int date, int month, int year, int hour, int minute, int second) {
        calendar.set(Calendar.DATE, date);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        TextYear.setText(year + "");
        TextDate.setText(date + "");
        TextMonth.setText(Formatter.format(calendar.getTime()));
    }

    public long getTime() {
        return calendar.getTimeInMillis();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.tv_date:
                processEventsFor(TextDate, event);
                break;
            case R.id.tv_month:
                processEventsFor(TextMonth, event);
                break;
            case R.id.tv_year:
                processEventsFor(TextYear, event);
                break;
        }
        return true;
    }

    private void processEventsFor(TextView textView, MotionEvent event) {
        Drawable[] drawables = textView.getCompoundDrawables();
        if (hasDrawableTop(drawables) && hasDrawableBottom(drawables)) {
            Rect topBounds = drawables[TOP].getBounds();
            Rect bottomBounds = drawables[BOTTOM].getBounds();
            float x = event.getX();
            float y = event.getY();
            ActiveId = textView.getId();
            if (topDrawableHit(textView, topBounds.height(), x, y)) {
                if (isActionDown(event)) {
                    Increment = true;
                    increment(textView.getId());
                    handler.removeMessages(MESSAGE_WHAT);
                    handler.sendEmptyMessageDelayed(MESSAGE_WHAT, DELAY);
                    toggleDrawable(textView, true);
                }
                if (isActionUpOrCancel(event)) {
                    Increment = false;
                    toggleDrawable(textView, false);
                }

            } else if (bottomDrawableHit(textView, bottomBounds.height(), x, y)) {
                if (isActionDown(event)) {
                    Decrement = true;
                    decrement(textView.getId());
                    handler.removeMessages(MESSAGE_WHAT);
                    handler.sendEmptyMessageDelayed(MESSAGE_WHAT, DELAY);
                    toggleDrawable(textView, true);
                }
                if (isActionUpOrCancel(event)) {
                    Decrement = false;
                    toggleDrawable(textView, false);
                }
            } else {
                Increment = false;
                Decrement = false;
                toggleDrawable(textView, false);
            }
        }
    }

    private void increment(int id) {
        switch (id) {
            case R.id.tv_date:
                calendar.add(Calendar.DATE, 1);
                break;
            case R.id.tv_month:
                calendar.add(Calendar.MONTH, 1);
                break;
            case R.id.tv_year:
                calendar.add(Calendar.YEAR, 1);
                break;
        }
        set(calendar);
    }

    private void set(Calendar calendar) {
        int date = calendar.get(Calendar.DATE);
        int year = calendar.get(Calendar.YEAR);
        TextDate.setText(date + "");
        TextYear.setText(year + "");
        TextMonth.setText(Formatter.format(this.calendar.getTime()));
    }

    private void decrement(int id) {
        switch (id) {
            case R.id.tv_date:
                calendar.add(Calendar.DATE, -1);
                break;
            case R.id.tv_month:
                calendar.add(Calendar.MONTH, -1);
                break;
            case R.id.tv_year:
                calendar.add(Calendar.YEAR, -1);
                break;
        }
        set(calendar);
    }

    private boolean isActionDown(MotionEvent event) {
        return event.getAction() == MotionEvent.ACTION_DOWN;
    }

    private boolean isActionUpOrCancel(MotionEvent event) {
        return event.getAction() == MotionEvent.ACTION_UP
                || event.getAction() == MotionEvent.ACTION_CANCEL;
    }

    private boolean topDrawableHit(TextView textView, int drawableHeight, float x, float y) {
        int xmin = textView.getPaddingLeft();
        int xmax = textView.getWidth() - textView.getPaddingRight();
        int ymin = textView.getPaddingTop();
        int ymax = textView.getPaddingTop() + drawableHeight;
        return x > xmin && x < xmax && y > ymin && y < ymax;
    }

    private boolean bottomDrawableHit(TextView textView, int drawableHeight, float x, float y) {
        int xmin = textView.getPaddingLeft();
        int xmax = textView.getWidth() - textView.getPaddingRight();
        int ymax = textView.getHeight() - textView.getPaddingBottom();
        int ymin = ymax - drawableHeight;
        return x > xmin && x < xmax && y > ymin && y < ymax;
    }

    private boolean hasDrawableTop(Drawable[] drawables) {
        return drawables[TOP] != null;
    }

    private boolean hasDrawableBottom(Drawable[] drawables) {
        return drawables[BOTTOM] != null;
    }

    private void toggleDrawable(TextView textView, boolean pressed) {
        if (pressed) {
            if (Increment) {
                textView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.up_pressed, 0, R.drawable.down_normal);
            }
            if (Decrement) {
                textView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.up_normal, 0, R.drawable.down_pressed);
            }
        } else {
            textView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.up_normal, 0, R.drawable.down_normal);
        }
    }
}