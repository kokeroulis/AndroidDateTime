package gr.kokeroulis.androiddatetime.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import gr.kokeroulis.androiddatetime.DateSelection;
import gr.kokeroulis.androiddatetime.OnDateChangedListener;
import gr.kokeroulis.androiddatetime.OnTimeChangedListener;
import gr.kokeroulis.androiddatetime.TimeSelection;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.date_selection_picker) DateSelection dateSelection;
    @Bind(R.id.value) TextView valueText;
    @Bind(R.id.time_selection_picker) TimeSelection timeSelection;
    @Bind(R.id.valueTime) TextView valueTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Timber.plant(new Timber.DebugTree());

        dateSelection.setCurrentDate(18, 6, 2016);
        dateSelection.setOnDateChangedListener(new OnDateChangedListener() {
            @Override
            public void onDateChanged(int day, int month, int year) {
                valueText.setText(day + "/" + month + "/" + year);
            }
        });

        timeSelection.setCurrentDateTime(18, 6, 14,43);
        timeSelection.setOnTimeChangedListener(new OnTimeChangedListener() {
            @Override
            public void onTimeChanged(int day, int month, int year, int hour, int minute) {
                valueTime.setText(day + "/" + month + "/" + year + " hour " + hour + " minute " +minute);
            }
        });
    }
}
