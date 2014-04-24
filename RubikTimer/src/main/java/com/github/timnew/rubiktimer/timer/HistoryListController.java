package com.github.timnew.rubiktimer.timer;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.timnew.rubiktimer.R;
import com.github.timnew.rubiktimer.database.TimeRecordRepository;
import com.github.timnew.rubiktimer.domain.TimeRecord;
import com.github.timnew.rubiktimer.history.HistoryActivity_;
import com.github.timnew.rubiktimer.history.TimeRecordItemView;
import com.github.timnew.rubiktimer.history.TimeRecordItemView_;
import com.j256.ormlite.dao.ForeignCollection;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.ViewById;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.google.common.collect.Iterables.addAll;
import static com.google.common.collect.Iterables.limit;

@EBean
public class HistoryListController extends BaseAdapter {

    public static final int HISTORY_LIST_SIZE = 3;

    protected List<TimeRecord> timeRecords = new ArrayList<TimeRecord>(HISTORY_LIST_SIZE);

    @RootContext
    protected Activity activity;

    @Bean
    protected TimeRecordRepository timeRecordRepository;

    @ViewById(R.id.history_list)
    protected ListView historyList; //TODO replace this with a raw layout view for better animation

    @ViewById(R.id.no_history_data_message)
    protected TextView noHistoryMessageView;

    @AfterViews
    protected void afterViews() {
        historyList.setAdapter(this);
        refreshData();
    }

    @Override
    public int getCount() {
        return timeRecords.size();
    }

    @Override
    public TimeRecord getItem(int position) {
        return timeRecords.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TimeRecordItemView view;

        if (convertView == null) {
            view = TimeRecordItemView_.build(activity);
        } else {
            view = (TimeRecordItemView) convertView;
        }

        TimeRecord timeRecord = getItem(position);

        view.updateView(timeRecord);

        return view;
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

    public void refreshData() {
        try {
            ForeignCollection<TimeRecord> collection = timeRecordRepository.currentUserTimeRecordByCreationTime();

            collection.refreshCollection();

            timeRecords.clear();

            addAll(timeRecords, limit(collection, HISTORY_LIST_SIZE));

            collection.closeLastIterator();

            notifyDataSetChanged();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
