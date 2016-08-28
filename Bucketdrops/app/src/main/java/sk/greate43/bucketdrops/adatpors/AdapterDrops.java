package sk.greate43.bucketdrops.adatpors;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.RealmResults;
import sk.greate43.bucketdrops.R;
import sk.greate43.bucketdrops.holder.DropHolder;
import sk.greate43.bucketdrops.holder.FooterHolder;
import sk.greate43.bucketdrops.interfaces.AddListener;
import sk.greate43.bucketdrops.model.Drop;

/**
 * Created by great on 8/24/2016.
 */
public class AdapterDrops extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM=0;
    public static final int FOOTER=1;
    private AddListener mAddListener;
    private LayoutInflater inflater;
    private RealmResults<Drop> results;
    public static final String TAG="Salman";



    public AdapterDrops(Context context, RealmResults<Drop> results,AddListener listener) {
        inflater = LayoutInflater.from(context);
        update(results);
        mAddListener=listener;
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
            return new FooterHolder(view,mAddListener);
        }else {
            View view = inflater.inflate(R.layout.row_drop, parent, false);
            Log.d(TAG, "onCreateViewHolder: Holder ");
            return new DropHolder(view);
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
        return results.size()+1;
    }


}
