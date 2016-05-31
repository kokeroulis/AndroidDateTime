package gr.kokeroulis.androiddatetime;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import gr.kokeroulis.androiddatetime.models.DateModel;

public class CenterViewHolderListener implements View.OnTouchListener {
    private final float dx;
    private final float dy;
    private Handler handler;

    public CenterViewHolderListener(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            RecyclerView recyclerView = (RecyclerView) v;
            checkRv(recyclerView);
            calculateRv(recyclerView);
        }
        return false;
    }

    private void calculateRv(final RecyclerView recyclerView) {
        View view = recyclerView.findChildViewUnder(dx, dy);
        if (view != null) {
            RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(view);
            if (holder != null) {
                BaseAdapter adapter = getBaseDateTimeAdapter(recyclerView);
                if (holder.getAdapterPosition() != RecyclerView.NO_POSITION) {
                    int pos = holder.getAdapterPosition();
                    final DateModel model = adapter.getItems().get(pos);
                    final int scrollTo;
                    if (model.value() == -1) {
                        if (pos > adapter.getItemCount() / 2) {
                            final DateModel correctModel = adapter.getLastRealModel();
                            adapter.setActivatedItem(adapter.getItems().indexOf(correctModel));
                            scrollTo = (int) (dy);
                        } else {
                            final DateModel correctModel = adapter.getFirstRealModel();
                            adapter.setActivatedItem(adapter.getItems().indexOf(correctModel));
                            scrollTo = (int) -(dy);
                        }
                    } else if (holder.itemView.getY() < dy /2) {
                        pos ++;
                        RecyclerView.ViewHolder current = recyclerView.findViewHolderForAdapterPosition(pos);
                        adapter.setActivatedItem(pos);
                        adapter.notifyDataSetChanged();
                        scrollTo = (int) (dy -current.itemView.getY());
                    } else {
                        adapter.setActivatedItem(pos);
                        adapter.notifyDataSetChanged();
                        scrollTo = (int) (dy -holder.itemView.getY());
                    }
                    recyclerView.scrollBy(0, -scrollTo);
                }
            }
        }
    }

    private void checkRv(final RecyclerView rv) {
        if (handler == null) {
            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (rv.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
                        calculateRv(rv);
                        handler.removeCallbacks(this);
                    } else {
                        handler.post(this);
                    }
                }
            }, 1000);
        }
    }

    @NonNull
    private BaseAdapter getBaseDateTimeAdapter(@NonNull final RecyclerView rv) {
        RecyclerView.Adapter adapter = rv.getAdapter();
        return (BaseAdapter) adapter;
    }
}
