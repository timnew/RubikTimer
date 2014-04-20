package com.github.timnew.rubiktimer;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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

@EBean
public class GameTimer {
    private static final String TIMER_TAG = "Game Timer";
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

    private PowerManager.WakeLock wakeLock;
    private long totalTime = 0;
    private long startTime = 0;
    private boolean isTiming = false;

    private Handler uiCallback;

    @AfterViews
    protected void afterViews() {
        typeface.applyTo(timerView);

        wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, TIMER_TAG);

        uiCallback = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what != REFRESH_TIMER)
                    return false;

                if (isTiming) {
                    uiCallback.sendEmptyMessageDelayed(REFRESH_TIMER, UPDATE_INTERVAL);
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
//        wakeLock.acquire();

        uiCallback.sendEmptyMessageDelayed(REFRESH_TIMER, UPDATE_INTERVAL);

        updateTimer();
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

//        wakeLock.release();
    }

    public void restart() {
        totalTime = 0;
        refresh();
        start();
    }

    public static class AlarmReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
}
