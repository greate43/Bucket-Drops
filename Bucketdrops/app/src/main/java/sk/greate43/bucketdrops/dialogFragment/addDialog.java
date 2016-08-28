package sk.greate43.bucketdrops.dialogFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import io.realm.Realm;
import sk.greate43.bucketdrops.R;
import sk.greate43.bucketdrops.model.Drop;


/**
 * Created by great on 8/22/2016.
 */

// adding data to the database
// and creating a dialog fragment
public class AddDialog extends DialogFragment {

    private ImageButton BtnClose;
    private EditText InputWhat;
    private DatePicker InputWhen;
    private Button BtnAdd;

    private View.OnClickListener mBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id=v.getId();
            switch (id){
                case R.id.btn_add_a_drop:
                    addAction();
                  break;

            }


            dismiss();
        }
    };

    private void addAction() {
        String what= InputWhat.getText().toString();
        long now= System.currentTimeMillis();




        Realm realm=Realm.getDefaultInstance();
        Drop drop=new Drop(what,now,0,false);
        realm.beginTransaction();
        realm.copyToRealm(drop);
        realm.commitTransaction();
        realm.close();

    }

    public AddDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_dialog,container,false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BtnClose = (ImageButton) view.findViewById(R.id.btn_close);
        InputWhat = (EditText) view.findViewById(R.id.et_drop);
        InputWhen = (DatePicker) view.findViewById(R.id.datePicker);
        BtnAdd = (Button) view.findViewById(R.id.btn_add_a_drop);

        BtnClose.setOnClickListener(mBtnClickListener);
        BtnAdd.setOnClickListener(mBtnClickListener);
    }

}
