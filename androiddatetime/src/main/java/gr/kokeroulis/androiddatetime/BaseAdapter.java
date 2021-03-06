package gr.kokeroulis.androiddatetime;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gr.kokeroulis.androiddatetime.models.DateModel;

public class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.ViewHolder> {
    private final List<DateModel> mValues = new ArrayList<>();
    private int activatedItem = 2;
    private final Callback callback;

    public BaseAdapter(Callback callback) {
        this.callback = callback;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.holder_datebase, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final DateModel item = mValues.get(position);
        if (!item.title().equals("fake")) {
            holder.dateValue.setText(item.title());
        }
    }

    public void setItems(List<DateModel> models) {
        mValues.clear();
        mValues.add(DateModel.fakeModelForPadding);
        mValues.add(DateModel.fakeModelForPadding);
        mValues.addAll(models);
        mValues.add(DateModel.fakeModelForPadding);
        mValues.add(DateModel.fakeModelForPadding);
        notifyDataSetChanged();
    }

    public List<DateModel> getItems() {
        return mValues;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public int getActivatedItem() {
        return activatedItem;
    }

    public void setActivatedItem(int activatedItem) {
        this.activatedItem = activatedItem;
        callback.onDateChanged(getActivatedValue());
    }

    public DateModel getFirstRealModel() {
        for (DateModel value : mValues) {
            if (value.value() != -1) {
                return value;
            }
        }

        return null;
    }

    public DateModel getLastRealModel() {
        if (mValues.size() == 0) return null;

        for (int size = mValues.size(); size > 0; size--) {
            final DateModel model = mValues.get(size - 1);
            if (model.value() != -1) {
                return model;
            }
        }

        return null;
    }

    public int getActivatedValue() {
        DateModel item = mValues.get(activatedItem);
        return item.value();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView dateValue;
        public final LinearLayout parentView;
        public ViewHolder(View itemView) {
            super(itemView);
            dateValue = (TextView) itemView.findViewById(R.id.date_value);
            parentView = (LinearLayout) itemView.findViewById(R.id.date_value_parent);
        }
    }

    public static abstract class Callback {
        public abstract void onDateChanged(int value);
    }
}
