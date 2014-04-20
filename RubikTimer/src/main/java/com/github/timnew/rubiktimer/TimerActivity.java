package com.github.timnew.rubiktimer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

import static com.github.timnew.rubiktimer.TimerTriggerManager.OnTimerTriggeredListener;

@EActivity(R.layout.timer_activity)
public class TimerActivity extends SherlockActivity {
    @Bean
    protected TimerTriggerManager timerTriggerManager;

    @Bean
    protected GameTimer gameTimer;

    @ViewById(R.id.abort_button)
    protected Button abortButton;

    @ViewById(R.id.profile_icon)
    protected ImageView profileIcon;

    @ViewById(R.id.profile_name)
    protected TextView profileName;

    @AfterViews
    protected void afterViews() {
        timerTriggerManager.setTriggeredListener(new OnTimerTriggeredListener() {
            @Override
            public void onTimerTriggered() {
                gameTimer.toggle();
            }
        });
    }

    @Click(R.id.abort_button)
    protected void abortButtonClicked() {
        gameTimer.pause();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.abort_dialog_title)
                .setMessage(R.string.abort_dialog_text)
                .setPositiveButton(R.string.abort_dialog_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gameTimer.pause();
                    }
                })
                .setNegativeButton(R.string.abort_dialog_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gameTimer.start();
                    }
                })
                .show();
    }
}
