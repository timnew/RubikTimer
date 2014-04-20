package com.github.timnew.rubiktimer;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.ViewById;

@EBean
public class TimerTriggerManager {

    @ViewById(R.id.finger_left_button)
    protected Button leftButton;

    @ViewById(R.id.finger_right_button)
    protected Button rightButton;

    private int touchCount;

    private OnTimerTriggeredListener triggeredListener;

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
        if (touchCount >= 2 && triggeredListener != null) {
            triggeredListener.onTimerTriggered();
        }
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
                    return false;
            }
            return false;
        }
    }

    public static interface OnTimerTriggeredListener {
        void onTimerTriggered();
    }
}
