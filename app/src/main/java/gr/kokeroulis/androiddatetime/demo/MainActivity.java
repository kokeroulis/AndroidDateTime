package gr.kokeroulis.androiddatetime.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;
import gr.kokeroulis.androiddatetime.BaseAdapter;
import gr.kokeroulis.androiddatetime.CenterLockListener;
import gr.kokeroulis.androiddatetime.DataDateProvider;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Timber.plant(new Timber.DebugTree());

        /*final LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new BaseAdapter();
        recyclerView.setAdapter(adapter);


        adapter.setItems(DataDateProvider.getMonths());
        recyclerView.addOnScrollListener(new CenterLockListener(0));*/
    }
}
