package com.github.timnew.rubiktimer.timer;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.timnew.rubiktimer.R;
import com.github.timnew.rubiktimer.domain.TimeRecord;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import static com.github.timnew.rubiktimer.domain.TimeRecord.formartTime;

@EViewGroup(R.layout.time_record_item_view)
public class TimeRecordItemView extends RelativeLayout {

    @ViewById(R.id.profile)
    protected TextView profileView;

    @ViewById(R.id.time)
    protected TextView timeView;

    public TimeRecordItemView(Context context) {
        super(context);
    }

    public TimeRecordItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeRecordItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void updateView(TimeRecord timeRecord) {
        profileView.setText(timeRecord.getProfile().getName());
        timeView.setText(formartTime(timeRecord.getTime()));
    }
}