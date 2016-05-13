package gr.kokeroulis.androiddatetime;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import butterknife.Bind;

/**
 * Created by kokeroulis on 13/05/16.
 */
public class DateSelection extends LinearLayout {
    private RecyclerView monthRv;
    private RecyclerView yearRv;
    private BaseAdapter monthAdapter;
    private BaseAdapter yearAdapter;


    public DateSelection(Context context) {
        super(context);
        init();
        ;
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
        monthAdapter = new BaseAdapter();
        monthRv.setLayoutManager(new LinearLayoutManager(getContext()));
        monthRv.addOnScrollListener(new CenterLockListener(0));
        monthRv.setAdapter(monthAdapter);
        monthAdapter.setItems(DataDateProvider.getMonths());

        yearRv = (RecyclerView) findViewById(R.id.yearRecyclerView);
        yearAdapter = new BaseAdapter();
        yearRv.setLayoutManager(new LinearLayoutManager(getContext()));
        yearRv.addOnScrollListener(new CenterLockListener(0));
        yearRv.setAdapter(yearAdapter);
        yearAdapter.setItems(DataDateProvider.getYears());




    }
}
