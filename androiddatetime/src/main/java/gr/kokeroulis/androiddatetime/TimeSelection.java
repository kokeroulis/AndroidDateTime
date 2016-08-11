package gr.kokeroulis.androiddatetime;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import gr.kokeroulis.androiddatetime.models.MonthDayModel;

public class TimeSelection extends LinearLayout {
    private RecyclerView monthDayRv;
    private RecyclerView timeRv;
    private RecyclerView hourRv;

    private BaseAdapter monthDayAdapter;
    private BaseAdapter timeAdapter;
    private BaseAdapter hourAdapter;
    private OnTimeChangedListener listener;
    private int monthDay;
    private int time;
    private int hour;
    private int day;
    private int month;
    private final DataDateProvider dateProvider = getDateProvider();

    public TimeSelection(Context context) {
        super(context);
        init();
    }

    public TimeSelection(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TimeSelection(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private final BaseAdapter.Callback dateTimeCallback = new BaseAdapter.Callback() {
        @Override
        public void onDateChanged(int value) {
            int monthDay = monthDayAdapter.getActivatedValue();
            int hour = hourAdapter.getActivatedValue();
            int minute = timeAdapter.getActivatedValue();

            final MonthDayModel monthDayModel = dateProvider.getMonthDayFromDayOfYear(monthDay);

            if (listener != null && monthDayModel != null) {
                listener.onTimeChanged(
                    monthDayModel.getDay(),
                    monthDayModel.getMonth(),
                    monthDayModel.getYear(),
                    hour,
                    minute
                );
            }
        }
    };

    protected void init() {
        setOrientation(HORIZONTAL);
    }

    protected DataDateProvider getDateProvider() {
        return new DataDateProvider();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        LayoutInflater.from(getContext())
            .inflate(R.layout.layout_time_selection, this, true);

        final ImageView dateBackground = (ImageView) findViewById(R.id.date_background);

        final ViewTreeObserver observer = dateBackground.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final float dx = dateBackground.getX();
                final float dy = dateBackground.getY();
                timeRv.setOnTouchListener(new CenterViewHolderListener(dx, dy));
                monthDayRv.setOnTouchListener(new CenterViewHolderListener(dx, dy));
                hourRv.setOnTouchListener(new CenterViewHolderListener(dx, dy));

                if (observer.isAlive()) {
                    observer.removeOnGlobalLayoutListener(this);
                }
            }
        });
        monthDayRv = (RecyclerView) findViewById(R.id.monthRecyclerView);
        monthDayAdapter = new BaseAdapter(dateTimeCallback);
        monthDayRv.setLayoutManager(new LinearLayoutManager(getContext()));
        monthDayRv.setAdapter(monthDayAdapter);
        monthDayAdapter.setItems(dateProvider.getMonthDay());

        timeRv = (RecyclerView) findViewById(R.id.dayRecyclerView);
        timeAdapter = new BaseAdapter(dateTimeCallback);
        timeRv.setLayoutManager(new LinearLayoutManager(getContext()));
        timeAdapter.setItems(dateProvider.getMinutes());
        timeRv.setAdapter(timeAdapter);

        hourRv = (RecyclerView) findViewById(R.id.yearRecyclerView);
        hourAdapter = new BaseAdapter(dateTimeCallback);
        hourRv.setLayoutManager(new LinearLayoutManager(getContext()));
        hourRv.setAdapter(hourAdapter);
        hourAdapter.setItems(dateProvider.getHours());

        //if (timeAdapter.getItemCount() == 0) {
            setCurrentDateTime(day, month, time, hour);
        //}
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //The rv will break if a view has click access while we scroll
        getParent().requestDisallowInterceptTouchEvent(true);
        return false;
    }

    public void setCurrentDateTime(int day, int month, int hour, int minute) {
        if (monthDayRv == null) {
            MonthDayModel model = MonthDayModel.fromMonthDay(month, day, dateProvider);
            this.monthDay = model == null ? 1 : model.value();
            this.day = day;
            this.month = month;
            this.time = minute;
            this.hour = hour;
        } else {
            if (monthDay == 0) {
                MonthDayModel model = MonthDayModel.fromMonthDay(month, day, dateProvider);
                this.monthDay = model == null ? 1 : model.value();
            }
            new AsyncTaskHelper(monthDay, monthDayRv).execute(monthDayAdapter);
            new AsyncTaskHelper(this.hour, hourRv).execute(hourAdapter);
            new AsyncTaskHelper(time, timeRv).execute(timeAdapter);
        }
    }

    public void setOnTimeChangedListener(@Nullable final OnTimeChangedListener listener) {
        this.listener = listener;
    }
}
