package sk.greate43.bucketdrops.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import sk.greate43.bucketdrops.R;
import sk.greate43.bucketdrops.adatpors.AdapterDrops;
import sk.greate43.bucketdrops.customDrawable.Divider;
import sk.greate43.bucketdrops.dialogFragment.AddDialog;
import sk.greate43.bucketdrops.interfaces.AddListener;
import sk.greate43.bucketdrops.model.Drop;
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
    AdapterDrops adatperDrop;
    View emptyView;
    private RealmChangeListener realmChangeListener=new RealmChangeListener() {
        @Override
        public void onChange(Object element) {
            Log.v("Tag","on change was called");
            adatperDrop.update(results);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        realm = Realm.getDefaultInstance();
        results=realm.where(Drop.class).findAllAsync();

        emptyView= findViewById(R.id.empty_drops);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (BucketRecyclerView) findViewById(R.id.recycler);
        recyclerView.hideifempty(toolbar);
        recyclerView.showifempty(emptyView);
        adatperDrop=new AdapterDrops(this,results, addListener);

        recyclerView.setAdapter(adatperDrop);



        recyclerView.addItemDecoration(new Divider(this, LinearLayoutManager.VERTICAL));
        setSupportActionBar(toolbar);
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
