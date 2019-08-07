package sk.greate43.bucketdrops.adatpors;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.Realm;
import io.realm.RealmResults;
import sk.greate43.bucketdrops.R;
import sk.greate43.bucketdrops.application.AppBucketDrops;
import sk.greate43.bucketdrops.holder.DropHolder;
import sk.greate43.bucketdrops.holder.FooterHolder;
import sk.greate43.bucketdrops.holder.NoItemViewHolder;
import sk.greate43.bucketdrops.interfaces.AddListener;
import sk.greate43.bucketdrops.interfaces.Filter;
import sk.greate43.bucketdrops.interfaces.MarkListener;
import sk.greate43.bucketdrops.interfaces.ResetListener;
import sk.greate43.bucketdrops.interfaces.SwipeListener;
import sk.greate43.bucketdrops.model.Drop;

/**
 * Created by great on 8/24/2016.
 */
public class AdapterDrops extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SwipeListener {

    public static final int ITEM = 0;
    public static final int NO_ITEM = 1;
    public static final int FOOTER = 2;

    public static final int COUNT_FOOTER = 1;
    public static final int COUNT_NO_ITEM = 1;
    public static final String TAG = "Salman";
    private AddListener addListener;
    private MarkListener markListener;
    private LayoutInflater inflater;
    private RealmResults<Drop> results;
    private Realm realm;
    private int optionFilter;
    private ResetListener restListener;
    private Context context;


    public AdapterDrops(Context context, Realm r, RealmResults<Drop> results, AddListener listener, MarkListener m, ResetListener reset) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        update(results);
        addListener = listener;
        realm = r;
        markListener = m;
        restListener = reset;
    }

    public void update(RealmResults<Drop> results) {
        optionFilter = AppBucketDrops.load(context);
        this.results = results;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == FOOTER) {
            View view = inflater.inflate(R.layout.footer, parent, false);
            Log.d(TAG, "onCreateViewHolder: Footer ");
            return new FooterHolder(view, addListener);
        } else if (viewType == NO_ITEM) {
            View view = inflater.inflate(R.layout.no_item, parent, false);
            Log.d(TAG, "onCreateViewHolder: NO ITEM ");
            return new NoItemViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.row_drop, parent, false);
            Log.d(TAG, "onCreateViewHolder: DropHolder ");
            return new DropHolder(view, markListener);
        }


    }

    @Override
    public int getItemViewType(int position) {

        if (!results.isEmpty()) {
            if (position < results.size()) {
                return ITEM;
            } else {
                return FOOTER;
            }
        } else {
            if (optionFilter == Filter.COMPLETE ||
                    optionFilter == Filter.INCOMPLETE) {
                if (position == 0) {
                    return NO_ITEM;
                } else {
                    return FOOTER;
                }
            } else {
                return ITEM;
            }
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof DropHolder) {
            Drop drop = results.get(position);
            DropHolder dropHolder = (DropHolder) holder;
            dropHolder.UpdateUI(drop);
            Log.d(TAG, "onBindViewHolder: " + position);
        }

    }

    @Override
    public int getItemCount() {
        if (!results.isEmpty()) {
            return results.size() + COUNT_FOOTER;
        } else {
            if (optionFilter == Filter.LEAST_TIME_LEFT
                    || optionFilter == Filter.MOST_TIME_LEFT
                    || optionFilter == Filter.NONE) {
                return 0;
            } else {
                return COUNT_NO_ITEM + COUNT_FOOTER;
            }
        }

    }


    @Override
    public long getItemId(int position) {
        if (position < results.size()) {
            return results.get(position).getAdded();
        }
        return RecyclerView.NO_ID;
    }

    @Override
    public void OnSwipe(int position) {
        if (position < results.size()) {
            realm.beginTransaction();
            results.get(position).deleteFromRealm();
            realm.commitTransaction();
            notifyItemRemoved(position);
        }
        resetFilterIfEmpty();
    }

    private void resetFilterIfEmpty() {
        if (results.isEmpty() && (optionFilter == Filter.COMPLETE || optionFilter == Filter.INCOMPLETE)) {
            restListener.OnReset();
        }
    }

    public void markComplete(int position) {
        if (position < results.size()) {

            realm.beginTransaction();
            results.get(position).setCompleted(true);
            realm.commitTransaction();
            notifyItemChanged(position);
        }
    }
}
