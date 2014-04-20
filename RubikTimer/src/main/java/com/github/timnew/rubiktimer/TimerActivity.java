package com.github.timnew.rubiktimer;

import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.github.timnew.rubiktimer.typefaces.KozGoProTypeface;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.timer_activity)
public class TimerActivity extends SherlockActivity {

    @Bean
    protected KozGoProTypeface typeface;

    @ViewById(R.id.timer)
    protected TextView timer;

    @AfterViews
    void afterViews() {
        typeface.applyTo(timer);
        timer.setText("3:23.029");
    }
}
