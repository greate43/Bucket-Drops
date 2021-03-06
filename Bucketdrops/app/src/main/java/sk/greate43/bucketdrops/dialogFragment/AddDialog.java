package sk.greate43.bucketdrops.dialogFragment;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import io.realm.Realm;
import sk.greate43.bucketdrops.R;
import sk.greate43.bucketdrops.model.Drop;
import sk.greate43.bucketdrops.widgets.BucketPickerView;


/**
 * Created by great on 8/22/2016.
 */

// adding data to the database
// and creating a dialog fragment
public class AddDialog extends DialogFragment {

    private ImageButton BtnClose;
    private EditText InputWhat;
    private BucketPickerView InputWhen;
    private Button BtnAdd;

    private View.OnClickListener mBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.btn_add_a_drop:
                    addAction();
                    break;

            }


            dismiss();
        }
    };

    public AddDialog() {
    }

    private void addAction() {
        String what = InputWhat.getText().toString();
        long now = System.currentTimeMillis();


        Realm realm = Realm.getDefaultInstance();
        Drop drop = new Drop(what, now, InputWhen.getTime(), false);
        realm.beginTransaction();
        realm.copyToRealm(drop);
        realm.commitTransaction();
        realm.close();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogThemeCustom);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_dialog, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BtnClose = (ImageButton) view.findViewById(R.id.btn_close);
        InputWhat = (EditText) view.findViewById(R.id.et_drop);
        InputWhen = (BucketPickerView) view.findViewById(R.id.datePicker);
        BtnAdd = (Button) view.findViewById(R.id.btn_add_a_drop);

        BtnClose.setOnClickListener(mBtnClickListener);
        BtnAdd.setOnClickListener(mBtnClickListener);
    }

}
