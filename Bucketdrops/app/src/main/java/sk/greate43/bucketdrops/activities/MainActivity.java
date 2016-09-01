package sk.greate43.bucketdrops.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;
import sk.greate43.bucketdrops.R;
import sk.greate43.bucketdrops.adatpors.AdapterDrops;
import sk.greate43.bucketdrops.application.AppBucketDrops;
import sk.greate43.bucketdrops.dialogFragment.AddDialog;
import sk.greate43.bucketdrops.dialogFragment.DialogMark;
import sk.greate43.bucketdrops.extras.Util;
import sk.greate43.bucketdrops.interfaces.AddListener;
import sk.greate43.bucketdrops.interfaces.Filter;
import sk.greate43.bucketdrops.interfaces.MarkListener;
import sk.greate43.bucketdrops.interfaces.ResetListener;
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

    private MarkListener markListener = new MarkListener() {
        @Override
        public void OnMark(int position) {
            showDialogMark(position);
        }
    };

    private ResetListener resetListener=new ResetListener() {
        @Override
        public void OnReset() {
          AppBucketDrops.save(MainActivity.this,Filter.NONE);
            loadResults(Filter.NONE);
        }
    };


    private TaskCompleteListener taskCompleteListener = new TaskCompleteListener() {
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

        int filterOption=AppBucketDrops.load(this);
        loadResults(filterOption);

        recyclerView.hideifempty(toolbar);
        recyclerView.showifempty(emptyView);
        adatperDrop = new AdapterDrops(this, realm, results, addListener, markListener,resetListener);
        adatperDrop.setHasStableIds(true);
        recyclerView.setAdapter(adatperDrop);
        recyclerView.addItemDecoration(new Divider(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        SimpleTouchCallback simpleTouchCallback = new SimpleTouchCallback(adatperDrop);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);



        Util.ScheduleAlarm(this);

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
        Bundle bundle = new Bundle();
        bundle.putInt("POSITION", position);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        int filterOption=Filter.NONE;
        boolean handled=true;
        switch (id){

            case Filter.NONE:
               filterOption=Filter.NONE;
                break;
            case R.id.action_add:
                showDialogAdd();
                break;
            case R.id.action_sort_assending_date:
                filterOption=Filter.LEAST_TIME_LEFT;


                break;
            case R.id.action_show_descending_date:
                filterOption=Filter.MOST_TIME_LEFT;


                break;
            case R.id.action_show_complete:
                filterOption=Filter.COMPLETE;


                break;
            case R.id.action_show_incomplete:
                filterOption=Filter.INCOMPLETE;


                break;
            default:
                 handled=false;
              break;
        }
        AppBucketDrops.save(this,filterOption);
        loadResults(filterOption);
        return handled;
    }



    private void loadResults(int filterOption){
        switch (filterOption){
            case Filter.NONE:

                results = realm.where(Drop.class).findAllAsync();
                break;
            case Filter.LEAST_TIME_LEFT:
                results=realm.where(Drop.class).findAllSortedAsync("when",Sort.ASCENDING);

                break;
            case Filter.MOST_TIME_LEFT:
                results=realm.where(Drop.class).findAllSortedAsync("when",Sort.DESCENDING);

                break;
            case Filter.COMPLETE:
                results=realm.where(Drop.class).equalTo("completed",true).findAllAsync();

                break;
            case Filter.INCOMPLETE:
                results=realm.where(Drop.class).equalTo("completed",false).findAllAsync();

                break;
        }
        results.addChangeListener(realmChangeListener);

    }

}
