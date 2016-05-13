package gr.kokeroulis.androiddatetime;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import timber.log.Timber;

public class CenterLockListener extends RecyclerView.OnScrollListener {

    //To avoid recursive calls
    private boolean mAutoSet = true;

    //The pivot to be snapped to
    private int mCenterPivot;

    private float dx;
    private float dy;
    private int multiplier;
    private int overalScroll;
    private int previousScrollPos;


    public CenterLockListener(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
    }



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
                //recyclerView.smoothScrollBy(0, (int) (scrollNeeded));
                //RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(view);
                /*if (holder != null) {
                    int second = lm.findLastVisibleItemPosition();
                    int pos =  second - 2;
                    Log.e("test", "aaaa" + holder.getAdapterPosition());
                    final BaseAdapter adapter = getBaseDateTimeAdapter(recyclerView);
                    if (pos <= 0) {
                        Log.e("test", "smaller");
                    }

                    Log.e("test", "here " + scrollNeeded);
                    if (adapter != null && adapter.getActivatedItem() != pos) {
                        if (pos == 1) {
                            pos = 2;
                        } else if (pos <= 0) {
                            pos = 2;
                        }
                        adapter.setActivatedItem(holder.getAdapterPosition() - 1);
                        adapter.notifyDataSetChanged();
                        if (pos == adapter.getItemCount()) {
                            //recyclerView.scrollToPosition(pos);
                        } else {
                            //recyclerView.scrollToPosition(pos + 1);
                        }
                    }
                    mAutoSet =true;
                }*/

                View v =recyclerView.findChildViewUnder(dx,  dy);
                if (v != null) {
                    RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(v);
                    if (holder == null) {
                        return;
                    }

                    final BaseAdapter adapter = getBaseDateTimeAdapter(recyclerView);
                    int pos = holder.getAdapterPosition();
                    if (pos == RecyclerView.NO_POSITION) return;
                    Log.e("aaa ", "aaaa " + adapter.getItems().get(holder.getAdapterPosition()).value() + " ssss " + adapter.getActivatedItem());

                    if (pos <= 0) {
                        Log.e("test", "smaller");
                    }

                    //if (adapter != null && adapter.getActivatedItem() != pos) {
                        /*if (pos == 1) {
                            pos = 2;
                        } else if (pos <= 0) {
                            pos = 2;
                        }*/
                    float candidateDy = dy > 0 ? -dy : dy;

                    if (pos == adapter.getActivatedItem()) {
                        mAutoSet =true;
                        return;
                    }
                    Timber.e("holder dy %s imageview dy %s value %s multiplier %s", holder.itemView.getY(), dy, holder.getAdapterPosition() -1, multiplier);

                    //if (multiplier == -1) {
                    candidateDy = dy - holder.itemView.getY();

                    if (dy /2 >= holder.itemView.getY()) {
                            pos++;
                            //candidateDy =- (dy - holder.itemView.getY()) ;
                            candidateDy = (candidateDy * (1)) - holder.itemView.getY();
                            Timber.e("candidate 111 1 %s", candidateDy);
                        } else {
                            //candidateDy = -holder.itemView.getY() - (dy - holder.itemView.getY());
                            Timber.e("candidate %s", candidateDy);
                        candidateDy = (candidateDy * (1)) - holder.itemView.getY();

                        }

                    //candidateDy = dy - holder.itemView.getY();
                    //}

                    if (pos != adapter.getActivatedItem()) {
                        recyclerView.scrollBy(0, (int) -candidateDy);
                        adapter.setActivatedItem(pos);
                        adapter.notifyDataSetChanged();
                        Timber.e("this is the pos %s", pos -1);
                        if (pos == adapter.getItemCount()) {
                            //recyclerView.scrollToPosition(pos);
                        } else {
                            //recyclerView.scrollToPosition(pos + 1);
                        }
                        //}
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

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        multiplier = dy > 0 ? 1 : -1;
        overalScroll += dy;
        View v = recyclerView.findChildViewUnder(dx, overalScroll);
        if (v != null) {
            RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(v);
            if (holder != null) {
                if (previousScrollPos != holder.getAdapterPosition()) {
                    overalScroll = 0;
                } else {
                    previousScrollPos = holder.getAdapterPosition();
                }
            }
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
