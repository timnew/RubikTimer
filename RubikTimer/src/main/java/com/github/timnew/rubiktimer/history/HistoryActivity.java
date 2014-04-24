package com.github.timnew.rubiktimer.history;

import android.content.Intent;
import android.support.v4.app.NavUtils;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.github.timnew.rubiktimer.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;

@EActivity(R.layout.history_activity)
public class HistoryActivity extends SherlockFragmentActivity {

    @Bean
    protected HistoryViewPagerController historyViewPagerController;

    @AfterViews
    protected void afterView() {
        ActionBar actionBar = getSupportActionBar();

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        historyViewPagerController.updateTabs();
    }

    @OptionsItem(android.R.id.home)
    protected void homeUp() {
        Intent parentActivityIntent = NavUtils.getParentActivityIntent(this);
        NavUtils.navigateUpTo(this, parentActivityIntent);
    }


}

