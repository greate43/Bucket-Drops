package sk.greate43.bucketdrops.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import sk.greate43.bucketdrops.R;
import sk.greate43.bucketdrops.adatpors.AdapterDrops;
import sk.greate43.bucketdrops.dialogFragment.AddDialog;
import sk.greate43.bucketdrops.dialogFragment.DialogMark;
import sk.greate43.bucketdrops.interfaces.AddListener;
import sk.greate43.bucketdrops.interfaces.MarkListener;
import sk.greate43.bucketdrops.interfaces.TaskCompleteListener;
import sk.greate43.bucketdrops.model.Drop;
import sk.greate43.bucketdrops.recyclerCustomItem.Divider;
import sk.greate43.bucketdrops.recyclerCustomItem.SimpleTouchCallback;
import sk.greate43.bucketdrops.widgets.BucketRecyclerView;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    BucketRecyclerView recyclerView;
    Realm realm;
    RealmResults<Drop> results;

    private AddListener addListener = new AddListener() {
        @Override
        public void add() {
            showDialogAdd();

        }
    };

    private MarkListener markListener=new MarkListener() {
        @Override
        public void OnMark(int position) {
            showDialogMark(position);
        }
    };


   private TaskCompleteListener taskCompleteListener=new TaskCompleteListener() {
       @Override
       public void OnComplete(int position) {
                       adatperDrop.markComplete(position);
       }
   };

    AdapterDrops adatperDrop;
    View emptyView;

    private RealmChangeListener realmChangeListener = new RealmChangeListener() {
        @Override
        public void onChange(Object element) {
            Log.v("Tag", "on change was called");

            adatperDrop.update(results);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        emptyView = findViewById(R.id.empty_drops);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (BucketRecyclerView) findViewById(R.id.recycler);

        setSupportActionBar(toolbar);

        realm = Realm.getDefaultInstance();
        results = realm.where(Drop.class).findAllAsync();

        recyclerView.hideifempty(toolbar);
        recyclerView.showifempty(emptyView);
        adatperDrop = new AdapterDrops(this, realm, results, addListener,markListener);
        recyclerView.setAdapter(adatperDrop);
        recyclerView.addItemDecoration(new Divider(this, LinearLayoutManager.VERTICAL));

        SimpleTouchCallback simpleTouchCallback = new SimpleTouchCallback(adatperDrop);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        initImageView();

    }


    private void initImageView() {
        ImageView background = (ImageView) findViewById(R.id.background);
        Glide.with(this)
                .load(R.drawable.background)
                .centerCrop()
                .into(background);
    }

    public void addBtn(View view) {
        showDialogAdd();
    }

    private void showDialogAdd() {
        AddDialog dialog = new AddDialog();
        dialog.show(getSupportFragmentManager(), "Add");

    }

    private void showDialogMark(int position) {
        DialogMark dialog = new DialogMark();
        Bundle bundle =new Bundle();
        bundle.putInt("POSITION",position);
        dialog.setArguments(bundle);
        dialog.setCompleteListener(taskCompleteListener);
        dialog.show(getSupportFragmentManager(), "OnMark");


    }

    @Override
    protected void onStart() {
        super.onStart();
        results.addChangeListener(realmChangeListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        results.removeChangeListener(realmChangeListener);
    }
}
