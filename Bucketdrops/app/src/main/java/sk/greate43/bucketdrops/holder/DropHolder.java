package sk.greate43.bucketdrops.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import sk.greate43.bucketdrops.R;
import sk.greate43.bucketdrops.model.Drop;

/**
 * Created by great on 8/25/2016.
 */
public  class DropHolder extends RecyclerView.ViewHolder {

    TextView mTextWhat;
    public DropHolder(View itemView) {
        super(itemView);
        mTextWhat = (TextView) itemView.findViewById(R.id.tv_what);

    }

    public void UpdateUI(Drop drop){
        mTextWhat.setText(drop.getWhat());
    }
}