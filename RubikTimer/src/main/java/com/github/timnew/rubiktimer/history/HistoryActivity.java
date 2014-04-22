package com.github.timnew.rubiktimer.history;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

@EActivity
public class HistoryActivity extends SherlockFragmentActivity {
    @Bean
    protected HistoryViewPagerManager historyViewPagerManager;

    @AfterViews
    protected void afterViews() {
    }
}

