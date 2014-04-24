package com.github.timnew.rubiktimer.timer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;

import com.github.timnew.rubiktimer.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.github.timnew.rubiktimer.timer.GameTimer.OnTimerStatusChangedListener;

@EActivity(R.layout.timer_activity)
public class TimerActivity extends FragmentActivity implements OnTimerStatusChangedListener {
    @Bean
    protected TimerTriggerController timerTrigger;

    @Bean
    protected GameTimer gameTimer;

    @Bean
    protected HistoryListController historyListController;

    @Bean
    protected ProfileController profileController;

    @ViewById(R.id.abort_button)
    protected Button abortButton;

    @AfterViews
    protected void afterViews() {
        timerTrigger.setTriggeredListener(new TimerTriggerController.OnTimerTriggeredListener() {
            @Override
            public void startTimer() {
                gameTimer.restart();
            }

            @Override
            public void stopTimer() {
                gameTimer.stop();

                profileController.currentActiveProfile().addRecord(gameTimer.currentTime()); // TODO currentActiveProfile concept looks overcomplicated
                historyListController.refresh();
            }
        });
        gameTimer.setStatusChangedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        gameTimer.refresh();
        historyListController.refresh();
        profileController.refresh();
    }

    @Click(R.id.abort_button)
    protected void abortButtonClicked() {
        gameTimer.stop();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.abort_dialog_title)
                .setMessage(R.string.abort_dialog_text)
                .setPositiveButton(R.string.abort_dialog_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gameTimer.stop();
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

    @Override
    public void onTimerStatusChanged(GameTimer timer, boolean status) {
        if (status)
            abortButton.setVisibility(VISIBLE);
        else
            abortButton.setVisibility(GONE);
    }

}
