package sk.greate43.bucketdrops.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by great on 8/22/2016.
 */
public class Drop extends RealmObject {
    private String what;
    @PrimaryKey
    private long added;
    private long when;
    private boolean completed;

    public Drop() {
    }

    public Drop(String what, long added, long when, boolean completed) {
        this.what = what;
        this.added = added;
        this.when = when;
        this.completed = completed;
    }

    public String getWhat() {
        return what;
    }

    public long getAdded() {
        return added;
    }



    public long getWhen() {
        return when;
    }



    public boolean getCompletedTask() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
