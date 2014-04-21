package com.github.timnew.rubiktimer.timer;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.github.timnew.rubiktimer.R;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.ViewById;

import sun.plugin.dom.exception.InvalidStateException;

import static java.lang.String.format;

@EBean
public class TimerTrigger {

    @ViewById(R.id.finger_left_button)
    protected Button leftButton;

    @ViewById(R.id.finger_right_button)
    protected Button rightButton;

    private int touchCount;

    private OnTimerTriggeredListener triggeredListener;

    private TriggerState state = TriggerState.STATE_STOPPED;

    @AfterViews
    protected void afterViews() {
        touchCount = 0;
        OnTouchListener onTouchListener = new OnTouchListener();

        leftButton.setOnTouchListener(onTouchListener);
        rightButton.setOnTouchListener(onTouchListener);
    }

    public OnTimerTriggeredListener getTriggeredListener() {
        return triggeredListener;
    }

    public void setTriggeredListener(OnTimerTriggeredListener triggeredListener) {
        this.triggeredListener = triggeredListener;
    }

    private void checkStatus() {
        if (touchCount < 0 || touchCount > 2)
            throw new InvalidStateException(format("TouchCount value is invalid: %d", touchCount));

        state = state.checkStatus(touchCount, triggeredListener);
    }

    public boolean isTiming() {
        return state.isTiming();
    }

    public static enum TriggerState {
        STATE_STOPPED {
            @Override
            public TriggerState checkStatus(int touchCount, OnTimerTriggeredListener triggeredListener) {
                if (touchCount == 2)
                    return STATE_READY_TO_START;

                return this;
            }

            @Override
            public boolean isTiming() {
                return false;
            }
        },
        STATE_READY_TO_START {
            @Override
            public TriggerState checkStatus(int touchCount, OnTimerTriggeredListener triggeredListener) {
                if (touchCount == 0) {
                    triggeredListener.startTimer();
                    return STATE_TIMING;
                }

                return this;
            }

            @Override
            public boolean isTiming() {
                return false;
            }
        },
        STATE_TIMING {
            @Override
            public TriggerState checkStatus(int touchCount, OnTimerTriggeredListener triggeredListener) {
                if (touchCount == 2) {
                    triggeredListener.stopTimer();
                    return STATE_STOPPED;
                }

                return this;
            }

            @Override
            public boolean isTiming() {
                return true;
            }
        };

        public abstract TriggerState checkStatus(int touchCount, OnTimerTriggeredListener triggeredListener);

        public abstract boolean isTiming();
    }

    public static interface OnTimerTriggeredListener {
        void startTimer();

        void stopTimer();
    }

    private class OnTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touchCount++;
                    checkStatus();
                    return false;
                case MotionEvent.ACTION_UP:
                    touchCount--;
                    checkStatus();
                    return false;
            }
            return false;
        }
    }
}
