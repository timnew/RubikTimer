package com.github.timnew.rubiktimer.timer;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.github.timnew.rubiktimer.R;
import com.github.timnew.rubiktimer.typefaces.KozGoProTypeface;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.ViewById;

import static android.os.Handler.Callback;
import static android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;

@
        EBean
public class GameTimer {

    private static final int REFRESH_TIMER = 101;
    private static final int UPDATE_INTERVAL = 10;

    @RootContext
    protected Activity activity;

    @Bean
    protected KozGoProTypeface typeface;

    @ViewById(R.id.timer)
    protected TextView timerView;

    private long totalTime = 0;
    private long startTime = 0;
    private boolean isTiming = false;

    private Handler uiUpdater;

    private OnTimerStatusChangedListener statusChangedListener;

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

    public void restart() {
        totalTime = 0;
        refresh();
        start();
    }

    public void start() {
        if (isTiming)
            return;

        isTiming = true;
        startTime = System.currentTimeMillis();

        uiUpdater.sendEmptyMessageDelayed(REFRESH_TIMER, UPDATE_INTERVAL);

        updateTimer();

        activity.getWindow().addFlags(FLAG_KEEP_SCREEN_ON);

        onStatusChanged();
    }

    public void stop() {
        if (!isTiming)
            return;

        isTiming = false;

        updateTimer();

        activity.getWindow().clearFlags(FLAG_KEEP_SCREEN_ON);

        onStatusChanged();
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

    private void onStatusChanged() {
        if (statusChangedListener == null)
            return;

        statusChangedListener.onTimerStatusChanged(this, isTiming);
    }

    public OnTimerStatusChangedListener getStatusChangedListener() {
        return statusChangedListener;
    }

    public void setStatusChangedListener(OnTimerStatusChangedListener statusChangedListener) {
        this.statusChangedListener = statusChangedListener;
    }

    public boolean isTiming() {
        return isTiming;
    }

    public static interface OnTimerStatusChangedListener {
        void onTimerStatusChanged(GameTimer timer, boolean status);
    }
}
