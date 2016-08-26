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
import sk.greate43.bucketdrops.model.Drop;

/**
 * Created by great on 8/24/2016.
 */
public class AdapterDrops extends RecyclerView.Adapter<DropHolder> {
    private LayoutInflater inflater;
    private RealmResults<Drop> results;
    public static final String TAG="Salman";
    public AdapterDrops(Context context, RealmResults<Drop> results) {
        inflater = LayoutInflater.from(context);
        update(results);
    }

    public void update(RealmResults<Drop> results){
        this.results = results;
        notifyDataSetChanged();
    }

    @Override
    public DropHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_drop, parent, false);
        DropHolder holder = new DropHolder(view);
        Log.d(TAG, "onCreateViewHolder: ");
        return holder;
    }

    @Override
    public void onBindViewHolder(DropHolder holder, int position) {
        Drop drop= results.get(position);
       holder.UpdateUI(drop);
        Log.d(TAG, "onBindViewHolder: "+position);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }


}
