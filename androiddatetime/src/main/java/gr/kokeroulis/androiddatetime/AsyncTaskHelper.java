package gr.kokeroulis.androiddatetime;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.List;

import gr.kokeroulis.androiddatetime.models.DateModel;

public class AsyncTaskHelper extends AsyncTask<BaseAdapter, Integer, Integer> {
    private WeakReference<BaseAdapter> adapter;
    private final WeakReference<RecyclerView> rvAdapter;
    private final int candidateValue;

    public AsyncTaskHelper(int candidateValue, RecyclerView rv) {
        this.candidateValue = candidateValue;
        rvAdapter = new WeakReference<>(rv);
    }

    @Override
    protected Integer doInBackground(BaseAdapter... params) {
        adapter = new WeakReference<>(params[0]);
        List<DateModel> values = adapter.get().getItems();
        for (int i = 0; i < values.size(); i++) {
            if (values.get(i).value() == candidateValue) {
                return i;
            }
        }

        return 0;
    }

    @Override
    protected void onPostExecute(Integer activateItem) {
        if (adapter.get() != null && rvAdapter.get() != null) {
            adapter.get().setActivatedItem(activateItem);
            if (activateItem == adapter.get().getItemCount()) {
                rvAdapter.get().scrollToPosition(activateItem);
            } else {
                rvAdapter.get().scrollToPosition(activateItem + 1);
            }
        }
    }
}
