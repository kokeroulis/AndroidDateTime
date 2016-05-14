package gr.kokeroulis.androiddatetime;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class DateSelection extends LinearLayout {
    private RecyclerView monthRv;
    private RecyclerView dayRv;
    private RecyclerView yearRv;

    private BaseAdapter monthAdapter;
    private BaseAdapter dayAdapter;
    private BaseAdapter yearAdapter;
    private int currentDay = -1;
    private OnDateChangedListener listener;

    private final BaseAdapter.Callback dayCallback = new BaseAdapter.Callback() {
        @Override
        public void onDateChanged(final int value) {
            if (listener != null) {
                listener.onDateChanged(value, monthAdapter.getActivatedValue(), yearAdapter.getActivatedValue());
            }
        }
    };

    private final BaseAdapter.Callback monthCallback = new BaseAdapter.Callback() {
        @Override
        public void onDateChanged(int value) {
            int year = yearAdapter.getActivatedValue();
            updateDayAdapter(value, year);
            if (listener != null) {
                listener.onDateChanged(dayAdapter.getActivatedValue(), value, yearAdapter.getActivatedValue());
            }
        }
    };

    private final BaseAdapter.Callback yearCallback = new BaseAdapter.Callback() {
        @Override
        public void onDateChanged(int value) {
            int month = monthAdapter.getActivatedValue();
            updateDayAdapter(month, value);
            scrollDayAdapter();
            if (listener != null) {
                listener.onDateChanged(dayAdapter.getActivatedValue(), monthAdapter.getActivatedValue(), value);
            }
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

        final ImageView dateBackground = (ImageView) findViewById(R.id.date_background);

        final ViewTreeObserver observer = dateBackground.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
            final float dx = dateBackground.getX();
            final float dy = dateBackground.getY();
            dayRv.setOnTouchListener(new CenterViewHolderListener(dx, dy));
            monthRv.setOnTouchListener(new CenterViewHolderListener(dx, dy));
            yearRv.setOnTouchListener(new CenterViewHolderListener(dx, dy));

            if (observer.isAlive()) {
                observer.removeOnGlobalLayoutListener(this);
            }
            }
        });
        monthRv = (RecyclerView) findViewById(R.id.monthRecyclerView);
        monthAdapter = new BaseAdapter(monthCallback);
        monthRv.setLayoutManager(new LinearLayoutManager(getContext()));
        monthRv.setAdapter(monthAdapter);
        monthAdapter.setItems(DataDateProvider.getMonths());

        dayRv = (RecyclerView) findViewById(R.id.dayRecyclerView);
        dayAdapter = new BaseAdapter(dayCallback);
        dayRv.setLayoutManager(new LinearLayoutManager(getContext()));
        dayRv.setAdapter(dayAdapter);

        yearRv = (RecyclerView) findViewById(R.id.yearRecyclerView);
        yearAdapter = new BaseAdapter(yearCallback);
        yearRv.setLayoutManager(new LinearLayoutManager(getContext()));
        yearRv.setAdapter(yearAdapter);
        yearAdapter.setItems(DataDateProvider.getYears());

        setCurrentDate(13, 5, 2016);
    }

    private void updateDayAdapter(int month, int year) {
        dayAdapter = new BaseAdapter(dayCallback);
        dayRv.setAdapter(dayAdapter);
        dayAdapter.setItems(DataDateProvider.getDaysForMonthAndYear(month, year));
    }

    public void setCurrentDate(int day, int month, int year) {

        new AsyncTaskHelper(month, monthRv).execute(monthAdapter);
        new AsyncTaskHelper(year, yearRv).execute(yearAdapter);
        // small hack for waiting until our month adapter has been populated
        currentDay = day;
    }

    private void scrollDayAdapter() {
        // Small hack for starting the async task after our
        // month adapter has data.
        // This is being used for setCurrentDate
        if (currentDay != -1) {
            new AsyncTaskHelper(currentDay, dayRv).execute(dayAdapter);
            currentDay = -1;
        }
    }

    public void setOnDateChangedListener(@Nullable final OnDateChangedListener listener) {
        this.listener = listener;
    }
}
