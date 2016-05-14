package gr.kokeroulis.androiddatetime;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

public class CenterViewHolderListener implements View.OnTouchListener {
    private final float dx;
    private final float dy;

    public CenterViewHolderListener(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            RecyclerView recyclerView = (RecyclerView) v;
            View view = recyclerView.findChildViewUnder(dx, dy);
            if (view != null) {
                RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(view);
                if (holder != null) {
                    BaseAdapter adapter = getBaseDateTimeAdapter(recyclerView);
                    if (holder.getAdapterPosition() != RecyclerView.NO_POSITION) {
                        int pos = holder.getAdapterPosition();
                        final int scrollTo;
                        if (holder.itemView.getY() < dy /2) {
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
        return false;
    }

    @NonNull
    private BaseAdapter getBaseDateTimeAdapter(@NonNull final RecyclerView rv) {
        RecyclerView.Adapter adapter = rv.getAdapter();
        return (BaseAdapter) adapter;
    }
}
