package com.github.timnew.rubiktimer.history;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.github.timnew.rubiktimer.R;
import com.google.common.base.Predicate;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.view.ViewPager.OnPageChangeListener;
import static com.actionbarsherlock.app.ActionBar.TabListener;
import static com.google.common.collect.Iterables.addAll;
import static com.google.common.collect.Iterables.filter;
import static java.util.Arrays.asList;

@EBean
public class HistoryViewPagerController extends FragmentPagerAdapter
        implements OnPageChangeListener, TabListener {

    @ViewById(R.id.view_pager)
    protected ViewPager viewPager;

    @RootContext
    protected SherlockFragmentActivity activity;

    private ActionBar actionBar;
    private List<HistoryTabs> tabs = new ArrayList<HistoryTabs>();

    public HistoryViewPagerController(Context sherlockFragmentActivity) {
        super(((SherlockFragmentActivity) sherlockFragmentActivity).getSupportFragmentManager());
    }

    @AfterViews
    protected void afterViews() {
        viewPager.setAdapter(this);
        viewPager.setOnPageChangeListener(this);

        actionBar = activity.getSupportActionBar();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        ActionBar.Tab currentTab = actionBar.getTabAt(position);
        actionBar.selectTab(currentTab);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        int position = tab.getPosition();
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    public void updateTabs() {
        tabs.clear();

        Iterable<HistoryTabs> visibleTabs = filter(asList(HistoryTabs.values()), new Predicate<HistoryTabs>() {
            @Override
            public boolean apply(HistoryTabs input) {
                return input.shouldShow();
            }
        });

        addAll(tabs, visibleTabs);

        notifyDataSetChanged();

        buildTabs();
    }

    private void buildTabs() {
        actionBar.removeAllTabs();

        for (HistoryTabs historyTab : tabs) {
            ActionBar.Tab tab = actionBar.newTab();
            tab.setText(historyTab.getTitle());
            tab.setTabListener(this);
            actionBar.addTab(tab);
        }
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    @Override
    public Fragment getItem(int position) {
        HistoryTabs tab = tabs.get(position);

        return tab.buildFragment();
    }
}

