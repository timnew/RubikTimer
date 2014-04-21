package com.github.timnew.rubiktimer.history;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;

@EActivity
public class HistoryActivity extends SherlockFragmentActivity {
    @Bean
    protected HistoryViewPagerAdapter viewPagerAdapter;

    @AfterViews
    protected void afterViews() {
    }
}

