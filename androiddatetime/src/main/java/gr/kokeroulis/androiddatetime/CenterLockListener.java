package gr.kokeroulis.androiddatetime;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

public class CenterLockListener extends RecyclerView.OnScrollListener {

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
                    int second = lm.findLastVisibleItemPosition();
                    int pos =  second - 2;
                    final BaseAdapter adapter = getBaseDateTimeAdapter(recyclerView);
                    if (pos <= 0) {
                        Log.e("test", "smaller");
                    }

                    Log.e("test", "here");
                    if (adapter != null && adapter.getActivatedItem() != pos) {
                        if (pos == 1) {
                            pos = 2;
                        } else if (pos <= 0) {
                            pos = 2;
                        }
                        adapter.setActivatedItem(pos);
                        adapter.notifyDataSetChanged();
                    }
                    mAutoSet =true;
                }
            }
        }

        if( newState == RecyclerView.SCROLL_STATE_DRAGGING
            || newState == RecyclerView.SCROLL_STATE_SETTLING ){
            mAutoSet = false;
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

    @Nullable
    private BaseAdapter getBaseDateTimeAdapter(@NonNull final RecyclerView rv) {
        RecyclerView.Adapter adapter = rv.getAdapter();
        if (adapter instanceof BaseAdapter) {
            return (BaseAdapter) adapter;
        } else {
            return null;
        }
    }
}
