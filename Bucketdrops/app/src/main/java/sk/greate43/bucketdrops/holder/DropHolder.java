package sk.greate43.bucketdrops.holder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import sk.greate43.bucketdrops.R;
import sk.greate43.bucketdrops.interfaces.MarkListener;
import sk.greate43.bucketdrops.model.Drop;

/**
 * Created by great on 8/25/2016.
 */
public class DropHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView TextWhat;
    private TextView TextWhen;
    private MarkListener markListener;
    private Context context;
    private View view;

    public DropHolder(View itemView, MarkListener m) {
        super(itemView);
        view = itemView;
        itemView.setOnClickListener(this);
        context = itemView.getContext();
        TextWhat = (TextView) itemView.findViewById(R.id.tv_what);
        TextWhen = (TextView) itemView.findViewById(R.id.tv_when);
        markListener = m;
    }

    public void UpdateUI(Drop drop) {
        TextWhat.setText(drop.getWhat());
        setBackgroundColor(drop.getCompletedTask());
        TextWhen.setText(DateUtils.getRelativeTimeSpanString(drop.getWhen(), System.currentTimeMillis(), DateUtils.DAY_IN_MILLIS, 0));
        //DateUtils.FORMAT_ABBREV_ALL
    }


    private void setBackgroundColor(boolean completedTask) {
        Drawable drawable;
        if (completedTask) {
            drawable = ContextCompat.getDrawable(context, R.color.bg_drop_complete);
        } else {
            drawable = ContextCompat.getDrawable(context, R.color.bg_drop_row_dark);
        }

        view.setBackground(drawable);

    }

    @Override
    public void onClick(View v) {
        markListener.OnMark(getAdapterPosition());
    }
}