package com.github.timnew.rubiktimer.history;

import android.support.v4.app.ListFragment;

import com.github.timnew.rubiktimer.R;
import com.github.timnew.rubiktimer.database.DatabaseHelper;
import com.github.timnew.rubiktimer.domain.TimeRecord;
import com.j256.ormlite.dao.Dao;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OrmLiteDao;

@EFragment(R.layout.history_list_fragment)
public class HistoryListFragment extends ListFragment {

    @OrmLiteDao(helper = DatabaseHelper.class, model = TimeRecord.class)
    protected Dao<TimeRecord, Integer> scoreDao;
}