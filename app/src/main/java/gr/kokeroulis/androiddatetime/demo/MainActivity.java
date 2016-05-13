package gr.kokeroulis.androiddatetime.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;
import gr.kokeroulis.androiddatetime.BaseAdapter;
import gr.kokeroulis.androiddatetime.CenterLockListener;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.demo_recyclerView)
    RecyclerView recyclerView;
    BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Timber.plant(new Timber.DebugTree());

        final LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new BaseAdapter();
        recyclerView.setAdapter(adapter);

        
        adapter.setItems(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22);
        recyclerView.addOnScrollListener(new CenterLockListener(0));
    }
}
