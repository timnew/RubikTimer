//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package com.github.timnew.rubiktimer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.timnew.rubiktimer.R.id;
import com.github.timnew.rubiktimer.R.layout;

public final class TimerActivity_
    extends TimerActivity
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.timer_activity);
    }

    private void init_(Bundle savedInstanceState) {
        gameTimer = GameTimer_.getInstance_(this);
        timerTriggerManager = TimerTriggerManager_.getInstance_(this);
    }

    private void afterSetContentView_() {
        profileName = ((TextView) findViewById(id.profile_name));
        abortButton = ((Button) findViewById(id.abort_button));
        profileIcon = ((ImageView) findViewById(id.profile_icon));
        {
            View view = findViewById(id.abort_button);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        TimerActivity_.this.abortButtonClicked();
                    }

                }
                );
            }
        }
        ((GameTimer_) gameTimer).afterSetContentView_();
        ((TimerTriggerManager_) timerTriggerManager).afterSetContentView_();
        afterViews();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        afterSetContentView_();
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        afterSetContentView_();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        afterSetContentView_();
    }

    public static TimerActivity_.IntentBuilder_ intent(Context context) {
        return new TimerActivity_.IntentBuilder_(context);
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, TimerActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public TimerActivity_.IntentBuilder_ flags(int flags) {
            intent_.setFlags(flags);
            return this;
        }

        public void start() {
            context_.startActivity(intent_);
        }

        public void startForResult(int requestCode) {
            if (context_ instanceof Activity) {
                ((Activity) context_).startActivityForResult(intent_, requestCode);
            } else {
                context_.startActivity(intent_);
            }
        }

    }

}
