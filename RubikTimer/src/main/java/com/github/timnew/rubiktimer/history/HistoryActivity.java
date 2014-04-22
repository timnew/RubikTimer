package com.github.timnew.rubiktimer.history;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.github.timnew.rubiktimer.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.history_activity)
public class HistoryActivity extends SherlockFragmentActivity {

    @Bean
    protected HistoryViewPagerManager historyViewPagerManager;

    @AfterViews
    protected void afterView() {
        ActionBar actionBar = getSupportActionBar();

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
    }

}

