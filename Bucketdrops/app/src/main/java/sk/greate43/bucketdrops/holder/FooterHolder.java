package sk.greate43.bucketdrops.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import sk.greate43.bucketdrops.R;
import sk.greate43.bucketdrops.interfaces.AddListener;

/**
 * Created by great on 8/28/2016.
 */

public class FooterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private Button footer;
    // using for the fragment dialog
    private AddListener addListener;

    public FooterHolder(View itemView, AddListener listener) {
        super(itemView);
        footer = (Button) itemView.findViewById(R.id.footerBtn);
        footer.setOnClickListener(this);
        addListener = listener;
    }

    @Override
    public void onClick(View v) {
        addListener.add();
    }
}
