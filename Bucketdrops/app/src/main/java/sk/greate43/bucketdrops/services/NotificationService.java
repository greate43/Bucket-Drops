package sk.greate43.bucketdrops.services;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.util.Log;

import br.com.goncalves.pugnotification.notification.PugNotification;
import io.realm.Realm;
import io.realm.RealmResults;
import sk.greate43.bucketdrops.R;
import sk.greate43.bucketdrops.activities.MainActivity;
import sk.greate43.bucketdrops.model.Drop;



public class NotificationService extends IntentService {
public static final String TAG="Salman";

    public NotificationService() {
        super("NotificationService");
        Log.d(TAG,"NotificationService: ");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG,"onHandleIntent: ");
        Realm realm=null;
        try {
            realm=Realm.getDefaultInstance();
           RealmResults<Drop> results=realm.where(Drop.class).equalTo("completed",false).findAll();

            for (Drop current:results){
if (isNotificationNeeded(current.getAdded(),current.getWhen())){
    FireNotification();
}
            }
        }finally {
            if (realm!=null){
                realm.close();
            }
        }
    }

    private void FireNotification() {
        PugNotification.with(this)
                .load()
                .title("Achievement")
                .message("You nearing your goal")
                .bigTextStyle("Congratulation, You are on the verge of accomplishing your goal ")
                .smallIcon(R.drawable.ic_drop)
                .largeIcon(R.drawable.ic_drop)
                .flags(Notification.DEFAULT_ALL)
                .autoCancel(true)
                .click(MainActivity.class)
                .simple()
                .build();
    }

    private boolean isNotificationNeeded(long added, long when) {
        long now= System.currentTimeMillis();
        if (now>when){
return false;
        }else {
            long difference90= (long) (0.9*(when-added));
            return (now>(added+difference90))?true:false;
        }

    }

}
