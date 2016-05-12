package gr.kokeroulis.androiddatetime;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.ViewHolder> {
    private final List<Integer> mValues = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.holder_datebase, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int item = mValues.get(position);
        holder.dateValue.setText(String.valueOf(item));
    }

    public void setItems(int... values) {
        mValues.clear();
        for (int v : values) {
            mValues.add(v);
        }

        notifyDataSetChanged();
    }

    public int getItemValue(int pos) {
        return mValues.get(pos);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView dateValue;
        public ViewHolder(View itemView) {
            super(itemView);
            dateValue = (TextView) itemView.findViewById(R.id.date_value);
        }
    }
}
