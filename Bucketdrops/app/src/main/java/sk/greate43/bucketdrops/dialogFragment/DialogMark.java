package sk.greate43.bucketdrops.dialogFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import sk.greate43.bucketdrops.R;
import sk.greate43.bucketdrops.interfaces.TaskCompleteListener;

/**
 * Created by great on 8/29/2016.
 */

public class DialogMark extends DialogFragment {
    private ImageButton close;
    private Button mark;
    private TaskCompleteListener taskCompleteListener;

    private View.OnClickListener BtnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mark_complete:
                    markASComplete();
                    break;
            }

            dismiss();
        }
    };


    private void markASComplete() {

        Bundle arguments = getArguments();
        if (taskCompleteListener != null && arguments != null) {
            int position = arguments.getInt("POSITION");
            taskCompleteListener.OnComplete(position);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogThemeCustom);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_mark, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        close = (ImageButton) view.findViewById(R.id.close);
        mark = (Button) view.findViewById(R.id.mark_complete);
        close.setOnClickListener(BtnClickListener);
        mark.setOnClickListener(BtnClickListener);


    }


    public void setCompleteListener(TaskCompleteListener listener) {
        taskCompleteListener = listener;
    }
}
