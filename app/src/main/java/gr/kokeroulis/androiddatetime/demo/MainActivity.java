package gr.kokeroulis.androiddatetime.demo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;

import butterknife.Bind;
import butterknife.ButterKnife;
import gr.kokeroulis.androiddatetime.BaseAdapter;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.demo_recyclerView)
    RecyclerView recyclerView;
    BaseAdapter adapter;
    private int overScroll;
    int activePos = -1;
    int currentPos = -2;
    int allPixel;
    int itemWidth;
    int found;

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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int pos = manager.findLastVisibleItemPosition() - manager.findFirstVisibleItemPosition();

                Timber.e("pos %s", pos);
            }
        }, 1000);

        /*recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE || newState == RecyclerView.SCROLL_STATE_SETTLING) {

                }
            }


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                overScroll += dy;
                allPixel += dx;
                View v = recyclerView.findChildViewUnder(dx, recyclerView.getMeasuredHeight() /2);
                int first =manager.findFirstCompletelyVisibleItemPosition();
                int last = manager.findLastCompletelyVisibleItemPosition();

                Timber.e("first %s last %s", first, last);
                /*if (v != null) {
                    RecyclerView.ViewHolder hol = recyclerView.findContainingViewHolder(v);
                    if (hol != null) {
                        currentPos = hol.getAdapterPosition();
                        if (itemWidth == 0) {
                            itemWidth = hol.itemView.getMeasuredWidth();
                        }
                        Timber.e("overs %s, vdx %s,  vDy %s xHei %s, height %s", overScroll, hol.itemView.getX(), hol.itemView.getY(), hol.itemView.getMeasuredHeight(), recyclerView.getMeasuredHeight());
                        float candMiddle = hol.itemView.getY() + (hol.itemView.getMeasuredHeight() / 2);
                        int pos = hol.getAdapterPosition();
                        if (recyclerView.getMeasuredHeight() /2 == (candMiddle + 50) && found != pos) {
                            found = pos;
                            Timber.e("found it %s", found);
                        }
                        //Timber.e("moiddle? %s", adapter.getItemValue(hol.getAdapterPosition()));

                    }
                }*/
                //Timber.e("x %s y %s", dx, dy);
            //}
        //});*/

        recyclerView.addOnScrollListener(new CenterLockListener(0));

        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int top = recyclerView.getTop();
                int bottom = recyclerView.getBottom();

                Timber.e("top %s, bottom %s", top, bottom);
            }
        });


    }

    public static class CenterLockListener extends RecyclerView.OnScrollListener {

        //To avoid recursive calls
        private boolean mAutoSet = true;

        //The pivot to be snapped to
        private int mCenterPivot;


        public CenterLockListener(int center){

            this.mCenterPivot = center;

        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();

            if( mCenterPivot == 0 ) {

                // Default pivot , Its a bit inaccurate .
                // Better pass the center pivot as your Center Indicator view's
                // calculated center on it OnGlobalLayoutListener event
                mCenterPivot = recyclerView.getTop() + recyclerView.getBottom();
            }
            if( !mAutoSet ) {
                if( newState == RecyclerView.SCROLL_STATE_IDLE ) {
                    //ScrollStoppped
                    View view = findCenterView(lm);//get the view nearest to center
                    int viewCenter = (view.getTop() + view.getBottom())/2;
                    //compute scroll from center
                    int scrollNeeded = viewCenter - mCenterPivot; // Add or subtract any offsets you need here
                    recyclerView.smoothScrollBy(0, (int) (scrollNeeded));
                    RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(view);
                    if (holder != null) {
                        Timber.e("pos %s", holder.getAdapterPosition());
                        Timber.e("aaaa %s", ((BaseAdapter) recyclerView.getAdapter()).getItemValue(holder.getAdapterPosition()));
                        Timber.e("first %s", lm.findFirstVisibleItemPosition());
                        Timber.e("second %s", lm.findLastVisibleItemPosition());
                        int first = lm.findFirstVisibleItemPosition();
                        int second = lm.findLastVisibleItemPosition();
                        int REAL_POSSSSS =  second - first;
                        mAutoSet =true;
                    }
                }
            }

            if( newState == RecyclerView.SCROLL_STATE_DRAGGING
                || newState == RecyclerView.SCROLL_STATE_SETTLING ){
                mAutoSet =false;
            }
        }

        private View findCenterView(LinearLayoutManager lm) {

            int minDistance = 0;
            View view = null;
            View returnView = null;
            boolean notFound = true;

            for(int i = lm.findFirstVisibleItemPosition(); i <= lm.findLastVisibleItemPosition() && notFound ; i++ ) {

                view=lm.findViewByPosition(i);

                int center = lm.getOrientation() == LinearLayoutManager.HORIZONTAL ? ( view.getLeft() + view.getRight() )/ 2 : ( view.getTop() + view.getBottom() )/ 2;
                int leastDifference = Math.abs(mCenterPivot - center);

                if( leastDifference <= minDistance || i == lm.findFirstVisibleItemPosition())
                {
                    minDistance = leastDifference;
                    returnView=view;
                }
                else
                {
                    notFound=false;

                }
            }
            return returnView;
        }
    }
}
