package com.github.timnew.rubiktimer.history;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

@EActivity
public class HistoryActivity extends SherlockFragmentActivity {
    @Bean
    protected HistoryViewPagerAdapter viewPagerAdapter;

    @AfterViews
    protected void afterViews() {
    }
}

