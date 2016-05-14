package gr.kokeroulis.androiddatetime.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import gr.kokeroulis.androiddatetime.DateSelection;
import gr.kokeroulis.androiddatetime.OnDateChangedListener;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.date_selection_picker) DateSelection dateSelection;
    @Bind(R.id.value) TextView valueText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Timber.plant(new Timber.DebugTree());

        dateSelection.setOnDateChangedListener(new OnDateChangedListener() {
            @Override
            public void onDateChanged(int day, int month, int year) {
                valueText.setText(day + "/" + month + "/" + year);
            }
        });
    }
}
