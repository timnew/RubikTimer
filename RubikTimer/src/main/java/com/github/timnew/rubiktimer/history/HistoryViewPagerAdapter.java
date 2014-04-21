package com.github.timnew.rubiktimer.history;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.github.timnew.rubiktimer.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.ViewById;

import static android.support.v4.view.ViewPager.OnPageChangeListener;
import static com.actionbarsherlock.app.ActionBar.TabListener;

@EBean
public class HistoryViewPagerAdapter extends FragmentPagerAdapter
        implements OnPageChangeListener, TabListener {

    @ViewById(R.id.view_pager)
    protected ViewPager viewPager;

    @RootContext
    protected SherlockFragmentActivity activity;

    private ActionBar actionBar;

    public HistoryViewPagerAdapter(Context sherlockFragmentActivity) {
        super(((SherlockFragmentActivity) sherlockFragmentActivity).getSupportFragmentManager());
    }

    @AfterViews
    protected void afterViews() {
        viewPager.setAdapter(this);
        viewPager.setOnPageChangeListener(this);

        actionBar = activity.getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
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

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
