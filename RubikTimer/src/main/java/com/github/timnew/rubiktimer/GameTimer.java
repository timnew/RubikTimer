package com.github.timnew.rubiktimer;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.widget.TextView;

import com.github.timnew.rubiktimer.typefaces.KozGoProTypeface;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.SystemService;
import com.googlecode.androidannotations.annotations.ViewById;

import static android.os.Handler.Callback;
import static android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;

@EBean
public class GameTimer {
    private static final int UPDATE_INTERVAL = 10;
    public static final int REFRESH_TIMER = 101;

    @RootContext
    protected Activity activity;

    @Bean
    protected KozGoProTypeface typeface;

    @SystemService
    protected PowerManager powerManager;

    @ViewById(R.id.timer)
    protected TextView timerView;

    private long totalTime = 0;
    private long startTime = 0;
    private boolean isTiming = false;

    private Handler uiUpdater;

    @AfterViews
    protected void afterViews() {
        typeface.applyTo(timerView);

        uiUpdater = new Handler(new Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what != REFRESH_TIMER)
                    return false;

                if (isTiming) {
                    uiUpdater.sendEmptyMessageDelayed(REFRESH_TIMER, UPDATE_INTERVAL);
                    updateTimer();
                }

                return true;
            }
        });

        refresh();
    }

    public void toggle() {
        if (isTiming)
            pause();
        else
            restart();
    }

    public void start() {
        if (isTiming)
            return;

        isTiming = true;
        startTime = System.currentTimeMillis();

        uiUpdater.sendEmptyMessageDelayed(REFRESH_TIMER, UPDATE_INTERVAL);

        updateTimer();

        activity.getWindow().addFlags(FLAG_KEEP_SCREEN_ON);
    }

    private void updateTimer() {
        long now = System.currentTimeMillis();
        totalTime += (now - startTime);
        startTime = now;

        refresh();
    }

    public void refresh() {
        long seconds = totalTime / 1000;
        long min = seconds / 60;
        seconds %= 60;
        long milli = totalTime % 1000;

        String timeText = String.format("%02d:%02d.%03d", min, seconds, milli);
        timerView.setText(timeText);
    }

    public void pause() {
        if (!isTiming)
            return;

        isTiming = false;

        updateTimer();

        activity.getWindow().clearFlags(FLAG_KEEP_SCREEN_ON);
    }

    public void restart() {
        totalTime = 0;
        refresh();
        start();
    }
}
