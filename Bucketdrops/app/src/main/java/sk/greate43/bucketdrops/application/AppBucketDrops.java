package sk.greate43.bucketdrops.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import sk.greate43.bucketdrops.interfaces.Filter;

/**
 * Created by great on 8/25/2016.
 */
public class AppBucketDrops extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);
    }
    public static void save(Context context, int filterOption) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("filter", filterOption);
        editor.apply();
    }

    public static int load(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int filterOption = sharedPreferences.getInt("filter", Filter.NONE);
        return filterOption;
    }

    public static void setRalewayRegular(Context context, TextView textView) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/raleway_thin.ttf");
        textView.setTypeface(typeface);
    }

    public static void setRalewayRegular(Context context, TextView... textViews) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/raleway_thin.ttf");
        for (TextView textView : textViews) {
            textView.setTypeface(typeface);
        }
    }


}