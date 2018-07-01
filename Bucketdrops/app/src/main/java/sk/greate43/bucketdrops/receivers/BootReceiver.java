package sk.greate43.bucketdrops.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import sk.greate43.bucketdrops.extras.Util;

public class BootReceiver extends BroadcastReceiver {
    private static final String TAG = "BootReceiver";
    public BootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: ");
        Util.ScheduleAlarm(context);
    }
}
