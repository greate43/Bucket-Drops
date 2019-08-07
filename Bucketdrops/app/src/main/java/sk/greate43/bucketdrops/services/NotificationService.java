package sk.greate43.bucketdrops.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmResults;
import sk.greate43.bucketdrops.R;
import sk.greate43.bucketdrops.activities.MainActivity;
import sk.greate43.bucketdrops.model.Drop;


public class NotificationService extends IntentService {
    public static final String TAG = "Salman";

    public NotificationService() {
        super("NotificationService");
        Log.d(TAG, "NotificationService: ");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent: ");
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            RealmResults<Drop> results = realm.where(Drop.class).equalTo("completed", false).findAll();

            for (Drop current : results) {
                if (isNotificationNeeded(current.getAdded(), current.getWhen())) {
                    FireNotification();
                }
            }
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    private void FireNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0 /* request code */, intent,PendingIntent.FLAG_UPDATE_CURRENT);

        long[] pattern = {500, 500, 500, 500, 500};

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "M_CH_ID")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Achievement")
                .setContentText("You nearing your goal")
                .setSmallIcon(R.drawable.ic_drop)
                .setAutoCancel(true)
                .setVibrate(pattern)
                .setLights(Color.BLUE, 1, 1)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                ;

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        }
    }

    private boolean isNotificationNeeded(long added, long when) {
        long now = System.currentTimeMillis();
        if (now > when) {
            return false;
        } else {
            long difference90 = (long) (0.9 * (when - added));
            return (now > (added + difference90)) ? true : false;
        }

    }

}
