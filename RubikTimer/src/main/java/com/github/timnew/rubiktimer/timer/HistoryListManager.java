package com.github.timnew.rubiktimer.timer;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.timnew.rubiktimer.R;
import com.github.timnew.rubiktimer.history.HistoryActivity_;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.ItemClick;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.ViewById;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

@EBean
public class HistoryListManager extends BaseAdapter {

    @RootContext
    protected Activity activity;

    @ViewById(R.id.history_list)
    protected ListView historyList;

    @ViewById(R.id.no_history_data_message)
    protected TextView noHistoryMessageView;

    @AfterViews
    protected void afterViews() {
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        updateViewVisibility();
    }

    private void updateViewVisibility() {
        if (getCount() == 0) {
            historyList.setVisibility(GONE);
            noHistoryMessageView.setVisibility(VISIBLE);
        } else {
            historyList.setVisibility(VISIBLE);
            noHistoryMessageView.setVisibility(GONE);
        }
    }

    @ItemClick(R.id.history_list)
    @Click(R.id.no_history_data_message)
    protected void onProfileClicked() {
        HistoryActivity_.intent(activity).start();
    }
}
