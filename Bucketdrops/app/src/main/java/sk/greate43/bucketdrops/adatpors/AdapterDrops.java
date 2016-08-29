package sk.greate43.bucketdrops.adatpors;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.Realm;
import io.realm.RealmResults;
import sk.greate43.bucketdrops.R;
import sk.greate43.bucketdrops.holder.DropHolder;
import sk.greate43.bucketdrops.holder.FooterHolder;
import sk.greate43.bucketdrops.interfaces.AddListener;
import sk.greate43.bucketdrops.interfaces.MarkListener;
import sk.greate43.bucketdrops.interfaces.SwipeListener;
import sk.greate43.bucketdrops.model.Drop;

/**
 * Created by great on 8/24/2016.
 */
public class AdapterDrops extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SwipeListener {

    public static final int ITEM=0;
    public static final int FOOTER=1;
    private AddListener addListener;
    private MarkListener markListener;
    private LayoutInflater inflater;
    private RealmResults<Drop> results;
    private Realm realm;
    public static final String TAG="Salman";



    public AdapterDrops(Context context, Realm r, RealmResults<Drop> results, AddListener listener, MarkListener m) {
        inflater = LayoutInflater.from(context);
        update(results);
        addListener =listener;
        realm=r;
        markListener=m;
    }

    public void update(RealmResults<Drop> results){
        this.results = results;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType==FOOTER){
            View view = inflater.inflate(R.layout.footer, parent, false);
            Log.d(TAG, "onCreateViewHolder: Footer ");
            return new FooterHolder(view, addListener);
        }else {
            View view = inflater.inflate(R.layout.row_drop, parent, false);
            Log.d(TAG, "onCreateViewHolder: Holder ");
            return new DropHolder(view,markListener);
        }



    }

    @Override
    public int getItemViewType(int position) {

        if(results==null||position<results.size()){
                return ITEM;
        }else {
            return FOOTER;
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof DropHolder){
            Drop drop= results.get(position);
            DropHolder dropHolder=(DropHolder)holder;
            dropHolder.UpdateUI(drop);
            Log.d(TAG, "onBindViewHolder: "+position);
        }

    }

    @Override
    public int getItemCount() {
        if (results==null||results.isEmpty()){
            return 0;
        }else {
            return results.size() + 1;
        }
    }


    @Override
    public void OnSwipe(int position) {
        if (position<results.size()){
        realm.beginTransaction();
        results.get(position).deleteFromRealm();
        realm.commitTransaction();
        notifyItemRemoved(position);
    }
    }

    public void markComplete(int position) {
        if (position<results.size()){

        realm.beginTransaction();
        results.get(position).setCompleted(true);
        realm.commitTransaction();
           notifyItemChanged(position);
        }
    }
}
