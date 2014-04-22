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

@EViewGroup(R.layout.record_view)
public class RecordView extends RelativeLayout {

    @ViewById(R.id.user)
    protected TextView userView;

    @ViewById(R.id.time)
    protected TextView timeView;

    public RecordView(Context context) {
        super(context);
    }

    public RecordView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecordView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void updateView(TimeRecord timeRecord) {
        userView.setText(timeRecord.getUser().getName());
        timeView.setText(formartTime(timeRecord.getTime()));
    }
}
