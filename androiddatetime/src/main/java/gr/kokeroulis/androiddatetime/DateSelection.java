package gr.kokeroulis.androiddatetime;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class DateSelection extends LinearLayout {
    private RecyclerView monthRv;
    private RecyclerView dayRv;
    private RecyclerView yearRv;

    private BaseAdapter monthAdapter;
    private BaseAdapter dayAdapter;
    private BaseAdapter yearAdapter;

    private final BaseAdapter.Callback dayCallback = new BaseAdapter.Callback() {
        @Override
        public void onDateChanged(int value) {

        }
    };

    private final BaseAdapter.Callback monthCallback = new BaseAdapter.Callback() {
        @Override
        public void onDateChanged(int value) {
            int year = yearAdapter.getActivatedValue();
            updateDayAdapter(value, year);
        }
    };

    private final BaseAdapter.Callback yearCallback = new BaseAdapter.Callback() {
        @Override
        public void onDateChanged(int value) {
            int month = monthAdapter.getActivatedValue();
            updateDayAdapter(month, value);
        }
    };

    public DateSelection(Context context) {
        super(context);
        init();
    }

    public DateSelection(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DateSelection(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DateSelection(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        LayoutInflater.from(getContext())
            .inflate(R.layout.layout_selection_date, this, true);

        monthRv = (RecyclerView) findViewById(R.id.monthRecyclerView);
        monthAdapter = new BaseAdapter(monthCallback);
        monthRv.setLayoutManager(new LinearLayoutManager(getContext()));
        monthRv.addOnScrollListener(new CenterLockListener(0));
        monthRv.setAdapter(monthAdapter);
        monthAdapter.setItems(DataDateProvider.getMonths());

        dayRv = (RecyclerView) findViewById(R.id.dayRecyclerView);
        dayAdapter = new BaseAdapter(dayCallback);
        dayRv.setLayoutManager(new LinearLayoutManager(getContext()));
        dayRv.addOnScrollListener(new CenterLockListener(0));
        dayRv.setAdapter(dayAdapter);
        dayAdapter.setItems(DataDateProvider.getDaysForMonthAndYear(5, 2016));

        yearRv = (RecyclerView) findViewById(R.id.yearRecyclerView);
        yearAdapter = new BaseAdapter(yearCallback);
        yearRv.setLayoutManager(new LinearLayoutManager(getContext()));
        yearRv.addOnScrollListener(new CenterLockListener(0));
        yearRv.setAdapter(yearAdapter);
        yearAdapter.setItems(DataDateProvider.getYears());
    }

    private void updateDayAdapter(int month, int year) {
        dayAdapter = new BaseAdapter(dayCallback);
        dayRv.setAdapter(dayAdapter);
        dayAdapter.setItems(DataDateProvider.getDaysForMonthAndYear(month, year));
    }


}
