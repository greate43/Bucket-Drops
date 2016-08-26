package sk.greate43.bucketdrops.application;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by great on 8/25/2016.
 */
public class AppBucketDrops extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration config=new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(config);
    }
}
